package com.sorcererxw.feedman.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorcererxw.feedman.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/20
 */

public class BaseTextAdapter extends RecyclerView.Adapter<BaseTextAdapter.TextViewHolder> {

    private Context mContext;

    public BaseTextAdapter(Context context) {
        mContext = context;
    }

    private List<String> mData = new ArrayList<>();

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    private int mTextSize = 20;

    public void setTextSize(int textSize) {
        mTextSize = textSize;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String text);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override

    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_base_text, parent, false));
    }

    @Override
    public void onBindViewHolder(final TextViewHolder holder, int position) {
        holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        holder.text.setText(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition(),
                            mData.get(holder.getAdapterPosition()));
                }
            }
        });
        holder.text.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextPrimary));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_item_base_text)
        TextView text;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
