package com.sorcererxw.feedman.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/19
 */

@SuppressWarnings("ALL")
public class Readability {

    private static final String CONTENT_SCORE = "readabilityContentScore";

    private final Document mDocument;
    private String mBodyCache;

    public Readability(String html) {
        super();
        mDocument = Jsoup.parse(html);
    }

    public Readability(String html, String baseUri) {
        super();
        mDocument = Jsoup.parse(html, baseUri);
    }

    public Readability(File in, String charsetName, String baseUri) throws IOException {
        super();
        mDocument = Jsoup.parse(in, charsetName, baseUri);
    }

    public Readability(URL url, int timeoutMillis) throws IOException {
        super();
        mDocument = Jsoup.parse(url, timeoutMillis);
    }

    public Readability(Document doc) {
        super();
        mDocument = doc;
    }

    /**
     * 工作流程:
     * 1. 预处理--移除文档中的脚本标签和css
     * 2. 建立DOM树
     * 3. 从当前的DOM树中提取数据重新组成文章
     * 4. 把当前的DOM树更新为重新组成文章的DOM树
     * 5. 完成
     *
     * @param preserveUnlikelyCandidates 是否保留可能无用的数据, 默认为不保留
     */
    private void init(boolean preserveUnlikelyCandidates) {

        /* 创建副本 */
        if (mDocument.body() != null && mBodyCache == null) {
            mBodyCache = mDocument.body().html();
        }

        prepDocument();

        /* 建立DOM树 */
        Element overlay = mDocument.createElement("div");
        Element innerDiv = mDocument.createElement("div");
        Element articleTitle = getArticleTitle();
        Element articleContent = grabArticle(preserveUnlikelyCandidates);

        /**
         * 检测是否处理过度, 当把所有可能不是文章的元素剔除了, 没有其他内容了
         * 如果是这样就使用保守的方法进行处理, 保留可能的内容
         * 如果保守处理还是没有内容, 说明当前算法无法处理这个文本
         */
        if (isEmpty(getInnerText(articleContent, false))) {
            if (!preserveUnlikelyCandidates) {
                mDocument.body().html(mBodyCache);
                init(true);
                return;
            } else {
                articleContent.html(mBodyCache);
                return;
            }
        }

        /* 将数据重新组合起来 */
        innerDiv.appendChild(articleTitle);
        innerDiv.appendChild(articleContent);
        overlay.appendChild(innerDiv);

        /* 移除旧的数据, 插入新的数据 */
        mDocument.body().html("");
        mDocument.body().prependChild(overlay);

    }

    public final void init() {
        init(false);
    }

    /**
     * @return inner html
     */
    public final String html() {
        return mDocument.html();
    }

    /**
     * @return outer html
     */
    public final String outerHtml() {
        return mDocument.outerHtml();
    }

    /**
     * @return 根据h1标签提取文章的标题
     */
    protected Element getArticleTitle() {
        Element articleTitle = mDocument.createElement("h1");
        articleTitle.html(mDocument.title());
        return articleTitle;
    }

    /**
     * 预处理文档内容, 剔除其中的非必要元素 (js, css和标签)
     */
    protected void prepDocument() {
        /* 防止文档内容为空 */
        if (mDocument.body() == null) {
            mDocument.appendElement("body");
        }

        /* 移除js */
        Elements elementsToRemove = mDocument.getElementsByTag("script");
        for (Element script : elementsToRemove) {
            script.remove();
        }

        /* 移除css */
        elementsToRemove = getElementsByTag(mDocument.head(), "link");
        for (Element styleSheet : elementsToRemove) {
            if ("stylesheet".equalsIgnoreCase(styleSheet.attr("rel"))) {
                styleSheet.remove();
            }
        }

        /* 移除标签 */
        elementsToRemove = mDocument.getElementsByTag("style");
        for (Element styleTag : elementsToRemove) {
            styleTag.remove();
        }

        /* 转换重复换行(br) 为普通段落(p) */
        mDocument.body().html(
                mDocument.body().html()
                        .replaceAll(Patterns.REGEX_REPLACE_BRS, "</p><p>")
                        .replaceAll(Patterns.REGEX_REPLACE_FONTS, "<$1span>"));
    }

    /**
     * Prepare the article node for display. Clean out any inline styles,
     * iframes, forms, strip extraneous &lt;p&gt; tags, etc.
     *
     * @param articleContent
     */
    private void prepArticle(Element articleContent) {
        cleanStyles(articleContent);
        killBreaks(articleContent);

        /* Clean out junk from the article content */
        clean(articleContent, "form");
        clean(articleContent, "object");
        clean(articleContent, "h1");
        /**
         * If there is only one h2, they are probably using it as a header and
         * not a subheader, so remove it since we already have a header.
         */
        if (getElementsByTag(articleContent, "h2").size() == 1) {
            clean(articleContent, "h2");
        }
        clean(articleContent, "iframe");

        cleanHeaders(articleContent);

        /*
         * Do these last as the previous stuff may have removed junk that will
         * affect these
         */
        cleanConditionally(articleContent, "table");
        cleanConditionally(articleContent, "ul");
        cleanConditionally(articleContent, "div");

        /* Remove extra paragraphs */
        Elements articleParagraphs = getElementsByTag(articleContent, "p");
        for (Element articleParagraph : articleParagraphs) {
            int imgCount = getElementsByTag(articleParagraph, "img").size();
            int embedCount = getElementsByTag(articleParagraph, "embed").size();
            int objectCount = getElementsByTag(articleParagraph, "object")
                    .size();

            if (imgCount == 0 && embedCount == 0 && objectCount == 0
                    && isEmpty(getInnerText(articleParagraph, false))) {
                articleParagraph.remove();
            }
        }

        try {
            articleContent.html(articleContent.html().replaceAll(
                    "(?i)<br[^>]*>\\s*<p", "<p"));
        } catch (Exception e) {
            log("Cleaning innerHTML of breaks failed. This is an IE strict-block-elements bug. Ignoring.",
                    e);
        }
    }

    /**
     * Initialize a node with the readability object. Also checks the
     * className/id for special names to add to its score.
     *
     * @param node
     */
    private static void initializeNode(Element node) {
        node.attr(CONTENT_SCORE, Integer.toString(0));

        String tagName = node.tagName();
        if ("div".equalsIgnoreCase(tagName)) {
            incrementContentScore(node, 5);
        } else if ("pre".equalsIgnoreCase(tagName)
                || "td".equalsIgnoreCase(tagName)
                || "blockquote".equalsIgnoreCase(tagName)) {
            incrementContentScore(node, 3);
        } else if ("address".equalsIgnoreCase(tagName)
                || "ol".equalsIgnoreCase(tagName)
                || "ul".equalsIgnoreCase(tagName)
                || "dl".equalsIgnoreCase(tagName)
                || "dd".equalsIgnoreCase(tagName)
                || "dt".equalsIgnoreCase(tagName)
                || "li".equalsIgnoreCase(tagName)
                || "form".equalsIgnoreCase(tagName)) {
            incrementContentScore(node, -3);
        } else if ("h1".equalsIgnoreCase(tagName)
                || "h2".equalsIgnoreCase(tagName)
                || "h3".equalsIgnoreCase(tagName)
                || "h4".equalsIgnoreCase(tagName)
                || "h5".equalsIgnoreCase(tagName)
                || "h6".equalsIgnoreCase(tagName)
                || "th".equalsIgnoreCase(tagName)) {
            incrementContentScore(node, -5);
        }

        incrementContentScore(node, getClassWeight(node));
    }

    /**
     * Using a variety of metrics (content score, classname, element types),
     * find the content that ismost likely to be the stuff a user wants to read.
     * Then return it wrapped up in a div.
     * <p>
     * 通过多种方法(内容权重, 类名, 节点类型) 来寻找最关键的数据
     * 通过div包裹返回
     *
     * @param preserveUnlikelyCandidates
     * @return
     */
    protected Element grabArticle(boolean preserveUnlikelyCandidates) {
        /**
         * First, node prepping. Trash nodes that look cruddy (like ones with
         * the class name "comment", etc), and turn divs into P tags where they
         * have been used inappropriately (as in, where they contain no other
         * block level elements.)
         *
         * Note: Assignment from index for performance. See
         * http://www.peachpit.com/articles/article.aspx?p=31567&seqNum=5 TODO:
         * Shouldn't this be a reverse traversal?
         **/
        for (Element node : mDocument.getAllElements()) {
            /* 移除可能无用的元素 */
            if (!preserveUnlikelyCandidates) {
                String unlikelyMatchString = node.className() + node.id();
                /* 一些常见的控件元素 */
                Matcher unlikelyCandidatesMatcher = Patterns
                        .get(Patterns.RegEx.UNLIKELY_CANDIDATES)
                        .matcher(
                                unlikelyMatchString);
                /* 一些常见的文章元素 */
                Matcher maybeCandidateMatcher = Patterns.get(
                        Patterns.RegEx.OK_MAYBE_ITS_A_CANDIDATE).matcher(
                        unlikelyMatchString);
                if (unlikelyCandidatesMatcher.find()
                        && maybeCandidateMatcher.find()
                        && !"body".equalsIgnoreCase(node.tagName())) {
                    node.remove();
                    log("Removing unlikely candidate - " + unlikelyMatchString);
                    continue;
                }
            }

            /* 将空div转换为p */
            if ("div".equalsIgnoreCase(node.tagName())) {
                Matcher matcher =
                        Patterns.get(Patterns.RegEx.DIV_TO_P_ELEMENTS).matcher(node.html());
                if (!matcher.find()) {
                    log("Alternating div to p: " + node);
                    try {
                        node.tagName("p");
                    } catch (Exception e) {
                        log("Could not alter div to p, probably an IE restriction, reverting back to div.",
                                e);
                    }
                }
            }
        }

        /**
         * Loop through all paragraphs, and assign a score to them based on how
         * content-y they look. Then add their score to their parent node.
         *
         * A score is determined by things like number of commas, class names,
         * etc. Maybe eventually link density.
         **/
        Elements allParagraphs = mDocument.getElementsByTag("p");
        ArrayList<Element> candidates = new ArrayList<>();

        for (Element node : allParagraphs) {
            Element parentNode = node.parent();
            Element grandParentNode = parentNode.parent();
            String innerText = getInnerText(node, true);

            /*
             * If this paragraph is less than 25 characters, don't even count it.
             */
            if (innerText.length() < 25) {
                continue;
            }

            /* Initialize readability data for the parent. */
            if (!parentNode.hasAttr("readabilityContentScore")) {
                initializeNode(parentNode);
                candidates.add(parentNode);
            }

            /* Initialize readability data for the grandparent. */
            if (!grandParentNode.hasAttr("readabilityContentScore")) {
                initializeNode(grandParentNode);
                candidates.add(grandParentNode);
            }

            int contentScore = 0;

            /* Add a point for the paragraph itself as a base. */
            contentScore++;

            /* Add points for any commas within this paragraph */
            contentScore += innerText.split(",").length;

            /*
             * For every 100 characters in this paragraph, add another point. Up
             * to 3 points.
             */
            contentScore += Math.min(Math.floor(innerText.length() / 100), 3);

            /* Add the score to the parent. The grandparent gets half. */
            incrementContentScore(parentNode, contentScore);
            incrementContentScore(grandParentNode, contentScore / 2);
        }

        /**
         * After we've calculated scores, loop through all of the possible
         * candidate nodes we found and find the one with the highest score.
         */
        Element topCandidate = null;
        for (Element candidate : candidates) {
            /**
             * Scale the final candidates score based on link density. Good
             * content should have a relatively small link density (5% or less)
             * and be mostly unaffected by this operation.
             */
            scaleContentScore(candidate, 1 - getLinkDensity(candidate));

            log("Candidate: (" + candidate.className() + ":" + candidate.id()
                    + ") with score " + getContentScore(candidate));

            if (topCandidate == null
                    || getContentScore(candidate) > getContentScore(topCandidate)) {
                topCandidate = candidate;
            }
        }

        /**
         * If we still have no top candidate, just use the body as a last
         * resort. We also have to copy the body node so it is something we can
         * modify.
         */
        if (topCandidate == null || "body".equalsIgnoreCase(topCandidate.tagName())) {
            topCandidate = mDocument.createElement("div");
            topCandidate.html(mDocument.body().html());
            mDocument.body().html("");
            mDocument.body().appendChild(topCandidate);
            initializeNode(topCandidate);
        }

        /**
         * Now that we have the top candidate, look through its siblings for
         * content that might also be related. Things like preambles, content
         * split by ads that we removed, etc.
         */
        Element articleContent = mDocument.createElement("div");
        articleContent.attr("id", "readability-content");
        int siblingScoreThreshold = Math.max(10,
                (int) (getContentScore(topCandidate) * 0.2f));
        Elements siblingNodes = topCandidate.parent().children();
        for (Element siblingNode : siblingNodes) {
            boolean append = false;

            log("Looking at sibling node: (" + siblingNode.className() + ":"
                    + siblingNode.id() + ")" + " with score "
                    + getContentScore(siblingNode));

            if (siblingNode == topCandidate) {
                append = true;
            }

            if (getContentScore(siblingNode) >= siblingScoreThreshold) {
                append = true;
            }

            if ("p".equalsIgnoreCase(siblingNode.tagName())) {
                float linkDensity = getLinkDensity(siblingNode);
                String nodeContent = getInnerText(siblingNode, true);
                int nodeLength = nodeContent.length();

                if (nodeLength > 80 && linkDensity < 0.25f) {
                    append = true;
                } else if (nodeLength < 80 && linkDensity == 0.0f
                        && nodeContent.matches(".*\\.( |$).*")) {
                    append = true;
                }
            }

            if (append) {
                log("Appending node: " + siblingNode);

                /*
                 * Append sibling and subtract from our list because it removes
                 * the node when you append to another node
                 */
                articleContent.appendChild(siblingNode);
                continue;
            }
        }

        /**
         * So we have all of the content that we need. Now we clean it up for
         * presentation.
         */
        prepArticle(articleContent);

        return articleContent;
    }

    /**
     * 提取节点里面的内容
     *
     * @param e               节点
     * @param normalizeSpaces 是否移除重复空格
     * @return 节点内容
     */
    private static String getInnerText(Element e, boolean normalizeSpaces) {
        String textContent = e.text().trim();

        if (normalizeSpaces) {
            textContent = textContent.replaceAll(Patterns.REGEX_NORMALIZE, "");
        }

        return textContent;
    }

    /**
     * Get the number of times a string s appears in the node e.
     *
     * @param e
     * @param s
     * @return
     */
    private static int getCharCount(Element e, String s) {
        if (s == null || s.length() == 0) {
            s = ",";
        }
        return getInnerText(e, true).split(s).length;
    }

    /**
     * Remove the style attribute on every e and under.
     *
     * @param e
     */
    private static void cleanStyles(Element e) {
        if (e == null) {
            return;
        }

        Element cur = e.children().first();

        // Remove any root styles, if we're able.
        if (!"readability-styled".equals(e.className())) {
            e.removeAttr("style");
        }

        // Go until there are no more child nodes
        while (cur != null) {
            // Remove style attributes
            if (!"readability-styled".equals(cur.className())) {
                cur.removeAttr("style");
            }
            cleanStyles(cur);
            cur = cur.nextElementSibling();
        }
    }

    /**
     * Get the density of links as a percentage of the content. This is the
     * amount of text that is inside a link divided by the total text in the
     * node.
     *
     * @param e
     * @return
     */
    private static float getLinkDensity(Element e) {
        Elements links = getElementsByTag(e, "a");
        int textLength = getInnerText(e, true).length();
        float linkLength = 0.0F;
        for (Element link : links) {
            linkLength += getInnerText(link, true).length();
        }
        return linkLength / textLength;
    }

    /**
     * Get an elements class/id weight. Uses regular expressions to tell if this
     * element looks good or bad.
     *
     * @param e
     * @return
     */
    private static int getClassWeight(Element e) {
        int weight = 0;

        /* Look for a special classname */
        String className = e.className();
        if (!isEmpty(className)) {
            Matcher negativeMatcher = Patterns.get(Patterns.RegEx.NEGATIVE)
                    .matcher(className);
            Matcher positiveMatcher = Patterns.get(Patterns.RegEx.POSITIVE)
                    .matcher(className);
            if (negativeMatcher.find()) {
                weight -= 25;
            }
            if (positiveMatcher.find()) {
                weight += 25;
            }
        }

        /* Look for a special ID */
        String id = e.id();
        if (!isEmpty(id)) {
            Matcher negativeMatcher = Patterns.get(Patterns.RegEx.NEGATIVE)
                    .matcher(id);
            Matcher positiveMatcher = Patterns.get(Patterns.RegEx.POSITIVE)
                    .matcher(id);
            if (negativeMatcher.find()) {
                weight -= 25;
            }
            if (positiveMatcher.find()) {
                weight += 25;
            }
        }

        return weight;
    }

    /**
     * Remove extraneous break tags from a node.
     *
     * @param e
     */
    private static void killBreaks(Element e) {
        e.html(e.html().replaceAll(Patterns.REGEX_KILL_BREAKS, "<br />"));
    }

    /**
     * Clean a node of all elements of type "tag". (Unless it's a youtube/vimeo
     * video. People love movies.)
     *
     * @param e
     * @param tag
     */
    private static void clean(Element e, String tag) {
        Elements targetList = getElementsByTag(e, tag);
        boolean isEmbed = "object".equalsIgnoreCase(tag)
                || "embed".equalsIgnoreCase(tag)
                || "iframe".equalsIgnoreCase(tag);

        for (Element target : targetList) {
            Matcher matcher = Patterns.get(Patterns.RegEx.VIDEO).matcher(
                    target.outerHtml());
            if (isEmbed && matcher.find()) {
                continue;
            }
            target.remove();
        }
    }

    /**
     * Clean an element of all tags of type "tag" if they look fishy. "Fishy" is
     * an algorithm based on content length, classnames, link density, number of
     * images & embeds, etc.
     *
     * @param e
     * @param tag
     */
    private void cleanConditionally(Element e, String tag) {
        Elements tagsList = getElementsByTag(e, tag);

        /**
         * Gather counts for other typical elements embedded within. Traverse
         * backwards so we can remove nodes at the same time without effecting
         * the traversal.
         *
         * TODO: Consider taking into account original contentScore here.
         */
        for (Element node : tagsList) {
            int weight = getClassWeight(node);

            log("Cleaning Conditionally (" + node.className() + ":" + node.id()
                    + ")" + getContentScore(node));

            if (weight < 0) {
                node.remove();
            } else if (getCharCount(node, ",") < 10) {
                /**
                 * If there are not very many commas, and the number of
                 * non-paragraph elements is more than paragraphs or other
                 * ominous signs, remove the element.
                 */
                int p = getElementsByTag(node, "p").size();
                int img = getElementsByTag(node, "img").size();
                int li = getElementsByTag(node, "li").size() - 100;
                int input = getElementsByTag(node, "input").size();

                int embedCount = 0;
                Elements embeds = getElementsByTag(node, "embed");
                for (Element embed : embeds) {
                    if (!Patterns.get(Patterns.RegEx.VIDEO)
                            .matcher(embed.absUrl("src")).find()) {
                        embedCount++;
                    }
                }

                float linkDensity = getLinkDensity(node);
                int contentLength = getInnerText(node, true).length();
                boolean toRemove = false;

                if (img > p) {
                    toRemove = true;
                } else if (li > p && !"ul".equalsIgnoreCase(tag)
                        && !"ol".equalsIgnoreCase(tag)) {
                    toRemove = true;
                } else if (input > Math.floor(p / 3)) {
                    toRemove = true;
                } else if (contentLength < 25 && (img == 0 || img > 2)) {
                    toRemove = true;
                } else if (weight < 25 && linkDensity > 0.2f) {
                    toRemove = true;
                } else if (weight > 25 && linkDensity > 0.5f) {
                    toRemove = true;
                } else if ((embedCount == 1 && contentLength < 75)
                        || embedCount > 1) {
                    toRemove = true;
                }

                if (toRemove) {
                    node.remove();
                }
            }
        }
    }

    /**
     * Clean out spurious headers from an Element. Checks things like classnames
     * and link density.
     *
     * @param e
     */
    private static void cleanHeaders(Element e) {
        for (int headerIndex = 1; headerIndex < 7; headerIndex++) {
            Elements headers = getElementsByTag(e, "h" + headerIndex);
            for (Element header : headers) {
                if (getClassWeight(header) < 0
                        || getLinkDensity(header) > 0.33f) {
                    header.remove();
                }
            }
        }
    }

    protected void log(String msg) {
        log(msg, null);
    }

    protected void log(String msg, Throwable t) {
        String logText = msg;
        if (t != null) {
            logText += "\n" + t.getMessage();
            logText += "\n" + t.getStackTrace();
        }
        Timber.d(logText);
    }

    private static class Patterns {
        private static Pattern sUnlikelyCandidatesRe;
        private static Pattern sOkMaybeItsACandidateRe;
        private static Pattern sPositiveRe;
        private static Pattern sNegativeRe;
        private static Pattern sDivToPElementsRe;
        private static Pattern sVideoRe;
        private static final String REGEX_REPLACE_BRS = "(?i)(<br[^>]*>[ \n\r\t]*){2,}";
        private static final String REGEX_REPLACE_FONTS = "(?i)<(\\/?)font[^>]*>";
        /* Java has String.trim() */
        // private static final String REGEX_TRIM = "^\\s+|\\s+$";
        private static final String REGEX_NORMALIZE = "\\s{2,}";
        private static final String REGEX_KILL_BREAKS = "(<br\\s*\\/?>(\\s|&nbsp;?)*){1,}";

        public enum RegEx {
            UNLIKELY_CANDIDATES,
            OK_MAYBE_ITS_A_CANDIDATE,
            POSITIVE,
            NEGATIVE,
            DIV_TO_P_ELEMENTS,
            VIDEO
        }

        public static Pattern get(RegEx regex) {
            switch (regex) {
                case UNLIKELY_CANDIDATES: {
                    if (sUnlikelyCandidatesRe == null) {
                        sUnlikelyCandidatesRe = Pattern.compile(
                                "combx|comment|disqus|foot|header|menu|meta|nav|rss|shoutbox|sidebar|sponsor",
                                Pattern.CASE_INSENSITIVE);
                    }
                    return sUnlikelyCandidatesRe;
                }
                case OK_MAYBE_ITS_A_CANDIDATE: {
                    if (sOkMaybeItsACandidateRe == null) {
                        sOkMaybeItsACandidateRe = Pattern.compile(
                                "and|article|body|column|main",
                                Pattern.CASE_INSENSITIVE);
                    }
                    return sOkMaybeItsACandidateRe;
                }
                case POSITIVE: {
                    if (sPositiveRe == null) {
                        sPositiveRe = Pattern
                                .compile(
                                        "article|body|content|entry|hentry|page|pagination|post|text",
                                        Pattern.CASE_INSENSITIVE);
                    }
                    return sPositiveRe;
                }
                case NEGATIVE: {
                    if (sNegativeRe == null) {
                        sNegativeRe = Pattern
                                .compile(
                                        "combx|comment|contact|foot|footer|footnote|link|media|meta|promo|related|scroll|shoutbox|sponsor|tags|widget",
                                        Pattern.CASE_INSENSITIVE);
                    }
                    return sNegativeRe;
                }
                case DIV_TO_P_ELEMENTS: {
                    if (sDivToPElementsRe == null) {
                        sDivToPElementsRe = Pattern.compile(
                                "<(a|blockquote|dl|div|img|ol|p|pre|table|ul)",
                                Pattern.CASE_INSENSITIVE);
                    }
                    return sDivToPElementsRe;
                }
                case VIDEO: {
                    if (sVideoRe == null) {
                        sVideoRe = Pattern.compile(
                                "http:\\/\\/(www\\.)?(youtube|vimeo)\\.com",
                                Pattern.CASE_INSENSITIVE);
                    }
                    return sVideoRe;
                }
            }
            return null;
        }
    }

    /**
     * Reads the content score.
     *
     * @param node
     * @return
     */
    private static int getContentScore(Element node) {
        try {
            return Integer.parseInt(node.attr(CONTENT_SCORE));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Increase or decrease the content score for an Element by an
     * increment/decrement.
     *
     * @param node
     * @param increment
     * @return
     */
    private static Element incrementContentScore(Element node, int increment) {
        int contentScore = getContentScore(node);
        contentScore += increment;
        node.attr(CONTENT_SCORE, Integer.toString(contentScore));
        return node;
    }

    /**
     * Scales the content score for an Element with a factor of scale.
     *
     * @param node
     * @param scale
     * @return
     */
    private static Element scaleContentScore(Element node, float scale) {
        int contentScore = getContentScore(node);
        contentScore *= scale;
        node.attr(CONTENT_SCORE, Integer.toString(contentScore));
        return node;
    }

    /**
     * Jsoup's Element.getElementsByTag(Element e) includes e itself, which is
     * different from W3C standards. This utility function is exclusive of the
     * Element e.
     *
     * @param e
     * @param tag
     * @return
     */
    private static Elements getElementsByTag(Element e, String tag) {
        Elements es = e.getElementsByTag(tag);
        es.remove(e);
        return es;
    }

    /**
     * Helper utility to determine whether a given String is empty.
     *
     * @param s
     * @return
     */
    private static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}

