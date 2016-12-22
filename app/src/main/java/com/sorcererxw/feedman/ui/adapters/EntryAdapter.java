package com.sorcererxw.feedman.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
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
import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private List<FeedEntry> mEntryList = new ArrayList<>();
    private Context mContext;

    public EntryAdapter(Context context) {
        mContext = context;
    }

    public void setEntries(List<FeedEntry> list) {
        boolean init = mEntryList.isEmpty();
        List<FeedEntry> oldList = mEntryList;
        mEntryList = new ArrayList<>(list);
        if (init) {
            notifyDataSetChanged();
        }else {
            DiffUtil.DiffResult result =
                    DiffUtil.calculateDiff(new EntryDiffUtilCallback(oldList, mEntryList), true);
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EntryViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(final EntryViewHolder holder, int position) {
        final FeedEntry feedEntry = mEntryList.get(position);

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
    public void onBindViewHolder(EntryViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads == null || payloads.isEmpty()) {
            return;
        }
        Bundle bundle = (Bundle) payloads.get(0);
        boolean unread = bundle.getBoolean(EntryDiffUtilCallback.KEY_UNREAD);
        if (unread) {
            holder.itemView.setAlpha(1f);
        } else {
            holder.itemView.setAlpha(0.5f);
        }
    }

    @Override
    public int getItemCount() {
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

        EntryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static class EntryDiffUtilCallback extends DiffUtil.Callback {
        private List<FeedEntry> mOldList;
        private List<FeedEntry> mNewList;

        EntryDiffUtilCallback(List<FeedEntry> oldList, List<FeedEntry> newList) {
            mOldList = oldList;
            mNewList = newList;
        }

        @Override
        public int getOldListSize() {
            return mOldList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldList.get(oldItemPosition).equals(mNewList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldList.get(oldItemPosition).unread() == mNewList.get(newItemPosition).unread();
        }

        static final String KEY_UNREAD = "key_unread";

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            FeedEntry oldEntry = mOldList.get(oldItemPosition);
            FeedEntry newEntry = mNewList.get(newItemPosition);
            Bundle diffBundle = new Bundle();
            if (oldEntry.unread() != newEntry.unread()) {
                diffBundle.putBoolean(KEY_UNREAD, newEntry.unread());
            }
            if (diffBundle.size() == 0) {
                return null;
            }
            return diffBundle;
        }
    }
}
