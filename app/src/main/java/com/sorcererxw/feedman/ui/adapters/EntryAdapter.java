package com.sorcererxw.feedman.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.data.FeedEntry;
import com.sorcererxw.feedman.ui.activities.ArticleActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private List<FeedEntry> mEntryList = new ArrayList<>();
    private List<FeedEntry> mUnreadList = new ArrayList<>();
    private Context mContext;

    public EntryAdapter(Context context) {
        mContext = context;
    }

    public void setEntries(List<FeedEntry> list) {
        mEntryList = list;

        rx.Observable.from(list).filter(new Func1<FeedEntry, Boolean>() {
            @Override
            public Boolean call(FeedEntry feedlyEntry) {
                return feedlyEntry.unread();
            }
        }).toList().subscribe(new Action1<List<FeedEntry>>() {
            @Override
            public void call(List<FeedEntry> entries) {
                mUnreadList = entries;
                notifyDataSetChanged();
            }
        });
    }

    private boolean mOnlyUnread = false;

    public void setOnlyUnread(boolean onlyUnread) {
        mOnlyUnread = onlyUnread;
        notifyDataSetChanged();
    }

    public boolean isOnlyUnread() {
        return mOnlyUnread;
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EntryViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(final EntryViewHolder holder, int position) {
        final FeedEntry feedEntry =
                mOnlyUnread ? mUnreadList.get(position) : mEntryList.get(position);

        if (feedEntry == null) {
            holder.title.setText("");
            holder.summary.setText("");
            holder.from.setText("\n\n");
            holder.itemView.setOnClickListener(null);
            return;
        }
        String from = new SimpleDateFormat("MM/dd/yyyy").format(new Date(feedEntry.published()))
                + " ● " + feedEntry.subscriptionTitle();
        holder.from.setText(from);

        holder.title.setText(feedEntry.title());

        holder.summary.setText(feedEntry.summary());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArticleActivity.class);
                intent.putExtra("entry", feedEntry);
                String s = feedEntry.toString();
                int maxLogSize = 500;
                for (int i = 0; i <= s.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > s.length() ? s.length() : end;
                    Timber.d(s.substring(start, end));
                }
                mContext.startActivity(intent);
                ((Activity) mContext)
                        .overridePendingTransition(R.anim.slide_right_in, android.R.anim.fade_out);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(feedEntry);
                    return true;
                }
                return false;
            }
        });
        if (feedEntry.unread()) {
            holder.itemView.setAlpha(1);
        } else {
            holder.itemView.setAlpha(0.5f);
        }
    }

    @Override
    public int getItemCount() {
        if (mOnlyUnread) {
            return mUnreadList.size();
        }
        return mEntryList.size();
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        mOnItemLongClickListener = longClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(FeedEntry entry);
    }


    static class EntryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_item_entry_title)
        TextView title;

        @BindView(R.id.textView_item_entry_summary)
        TextView summary;

        @BindView(R.id.textView_item_entry_from)
        TextView from;

        public EntryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
