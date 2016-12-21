package com.sorcererxw.feedman.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.sorcererxw.feedman.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mContext;

    private RequestManager mRequestManager;

    public CategoryAdapter(Context context) {
        mContext = context;
        mRequestManager = Glide.with(context);
    }

    private List<CategoryItem> mCategoryItemList = new ArrayList<>();

    public void setCategoryItemList(List<CategoryItem> categoryItemList) {
        mCategoryItemList = categoryItemList;
        notifyDataSetChanged();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LABEL) {
            return new CategoryLabelViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_category_label, parent, false));
        }
        return new CategoryGeneralViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_category_general, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_LABEL) {
            CategoryLabelViewHolder holder = (CategoryLabelViewHolder) viewHolder;
            holder.label.setText(mCategoryItemList.get(position).getTitle());
        } else {
            CategoryGeneralViewHolder holder = (CategoryGeneralViewHolder) viewHolder;
            holder.title.setText(mCategoryItemList.get(position).getTitle());
            if (mCategoryItemList.get(position).getIconDrawable() != null) {
                holder.icon.setImageDrawable(mCategoryItemList.get(position).getIconDrawable());
            } else {
                mRequestManager
                        .load(mCategoryItemList.get(position).getIconUrl())
                        .into(holder.icon);
            }
            holder.itemView
                    .setOnClickListener(mCategoryItemList.get(position).getOnClickListener());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mCategoryItemList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mCategoryItemList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class CategoryLabelViewHolder extends CategoryViewHolder {
        @BindView(R.id.textView_item_category_label)
        TextView label;

        public CategoryLabelViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class CategoryGeneralViewHolder extends CategoryViewHolder {
        @BindView(R.id.textView_item_category_general_title)
        TextView title;

        @BindView(R.id.imageView_item_category_general_icon)
        ImageView icon;

        public CategoryGeneralViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static final int TYPE_LABEL = 0x0;
    public static final int TYPE_SIMPLE = 0x1;
    public static final int TYPE_COLLECTION = 0x2;
    public static final int TYPE_SUBSCRIPTION = 0x3;

    public static class CategoryItem {

        public CategoryItem(String title, Drawable iconDrawable, String iconUrl, int type,
                            View.OnClickListener onClickListener) {
            mTitle = title;
            mIconDrawable = iconDrawable;
            mIconUrl = iconUrl;
            mType = type;
            mOnClickListener = onClickListener;
        }

        private View.OnClickListener mOnClickListener;

        private String mTitle;

        private Drawable mIconDrawable;

        private String mIconUrl;

        private int mType;

        public Drawable getIconDrawable() {
            return mIconDrawable;
        }

        public void setIconDrawable(Drawable iconDrawable) {
            mIconDrawable = iconDrawable;
        }

        public String getIconUrl() {
            return mIconUrl;
        }

        public void setIconUrl(String iconUrl) {
            mIconUrl = iconUrl;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public int getType() {
            return mType;
        }

        public void setType(int type) {
            mType = type;
        }

        public View.OnClickListener getOnClickListener() {
            return mOnClickListener;
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }
    }
}
