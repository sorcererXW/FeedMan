package com.sorcererxw.feedman.util;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/20
 */

public class Queries {
    public static final class EntryQueries {
        public static final String all =
                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.account_id = ? ORDER BY published #{sort} LIMIT ? OFFSET ?";
        public static final String all_count = " SELECT COUNT (id) FROM entry WHERE account_id = ?";
        public static final String category_all =
                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? ORDER BY published #{sort} LIMIT ? OFFSET ?";
        public static final String category_all_count =
                " SELECT COUNT (e.id) FROM entry e WHERE account_id = ? AND subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? )";
        public static final String category_newer_unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? AND e.published > ? AND e.unread = 1";
        public static final String category_older_unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? AND e.published < ? AND e.unread = 1";
        public static final String category_starred =
                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.starred = 1 AND e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? ORDER BY published #{sort} LIMIT ? OFFSET ?";
        public static final String category_starred_count =
                " SELECT COUNT (e.id) FROM entry e WHERE account_id = ? AND subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND starred = 1";
        public static final String category_unread =
                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? AND (e.unread = 1 OR e.read_timestamp  > ?) ORDER BY published #{sort} LIMIT ? OFFSET ?";
        public static final String category_unread_count =
                " SELECT COUNT (e.id) FROM entry e WHERE account_id = ? AND subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND unread = 1";
        public static final String category_unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? AND e.unread = 1";
        public static final String newer_unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.account_id = ? AND e.published > ? AND e.unread = 1";
        public static final String older_unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.account_id = ? AND e.published < ? AND e.unread = 1";
        public static final String starred =
                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.account_id = ? AND e.starred = 1 ORDER BY published #{sort} LIMIT ? OFFSET ?";
        public static final String starred_count =
                " SELECT COUNT (id) FROM entry WHERE account_id = ? AND starred = 1";
        public static final String subscription_all =
                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.subscription_id = ? AND e.account_id = ? ORDER BY published #{sort} LIMIT ? OFFSET ?";
        public static final String subscription_newer_unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.subscription_id = ? AND e.account_id = ? AND e.unread = 1 AND e.published > ?";
        public static final String subscription_older_unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.subscription_id = ? AND e.account_id = ? AND e.unread = 1 AND e.published < ?";
        public static final String subscription_unread =
                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.subscription_id = ? AND e.account_id = ? AND (e.unread = 1 OR e.read_timestamp  > ?) ORDER BY published #{sort} LIMIT ? OFFSET ?";
        public static final String subscription_unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.subscription_id = ? AND e.account_id = ? AND e.unread = 1";
        public static final String uncached_unread =
                " SELECT * FROM entry WHERE unread = 1 AND (cached != 1 OR cached is NULL) ORDER BY published DESC LIMIT ? OFFSET ?";
        public static final String uncached_unread_count =
                " SELECT COUNT(id) FROM entry WHERE unread = 1 AND (cached != 1 OR cached is NULL)";
        public static final String unread =
                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.account_id = ? AND (e.unread = 1 OR e.read_timestamp > ?) ORDER BY published #{sort} LIMIT ? OFFSET ?";
        public static final String unread_count =
                " SELECT COUNT (id) FROM entry WHERE account_id = ? AND unread = 1";
        public static final String unread_ids =
                " SELECT e.id FROM entry AS e WHERE e.account_id = ? AND e.unread = 1";
    }

    public static final class SubscriptionQueries {
        public static final String all =
                " SELECT *, CASE WHEN unread > 0 THEN 1 ELSE 0 END AS has_unread FROM ( SELECT *, ( SELECT COUNT(id) FROM entry e WHERE e.subscription_id = s.id AND e.account_id = s.account_id AND e.unread = 1 ) AS unread FROM subscription s WHERE account_id = ? ) AS subs ORDER BY has_unread DESC, title COLLATE NOCASE";
        public static final String category_all =
                " SELECT *, CASE WHEN unread > 0 THEN 1 ELSE 0 END AS has_unread FROM ( SELECT *, ( SELECT COUNT(id) FROM entry e WHERE e.subscription_id = s.id AND e.account_id = s.account_id AND e.unread = 1 ) AS unread FROM subscription s WHERE account_id = ? AND id IN ( SELECT subscription_id FROM subscription_categories WHERE account_id = ? AND category_id = ? ) ) AS subs ORDER BY has_unread DESC, title COLLATE NOCASE";
        public static final String category_unread =
                " SELECT * FROM ( SELECT *, ( SELECT COUNT(id) FROM entry e WHERE e.subscription_id = s.id AND e.account_id = s.account_id AND e.unread = 1 ) AS unread FROM subscription s WHERE account_id = ? AND id IN ( SELECT subscription_id FROM subscription_categories WHERE account_id = ? AND category_id = ? ) ) AS subs WHERE unread > 0 ORDER BY title COLLATE NOCASE";
        public static final String unread =
                " SELECT * FROM ( SELECT *, ( SELECT COUNT(id) FROM entry e WHERE e.subscription_id = s.id AND e.account_id = s.account_id AND e.unread = 1 ) AS unread FROM subscription s WHERE account_id = ? ) AS subs WHERE unread > 0 ORDER BY title COLLATE NOCASE";
    }
}
