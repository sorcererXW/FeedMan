package com.sorcererxw.feedman.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
 * @date: 2016/9/5
 */
public abstract class BaseTextAdapter<E>
        extends RecyclerView.Adapter<BaseTextAdapter.BaseTextViewHolder> {

    static class BaseTextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_base_text_content)
        TextView content;

        public BaseTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<E> mList;

    private Context mContext;

    private OnItemClickListener<E> mOnItemClickListener;

    public BaseTextAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setData(List<E> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addData(E bean) {
        mList.add(bean);
        notifyDataSetChanged();
    }

    public void addData(List<E> been) {
        mList.addAll(been);
        notifyDataSetChanged();
    }

    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<E> getData() {
        return mList;
    }

    @Override
    public BaseTextViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        return new BaseTextViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_base_text, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseTextViewHolder holder, int position) {
        holder.content.setText(convert(mList.get(position)));
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(mList.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected abstract String convert(E bean);

    public interface OnItemClickListener<E> {
        void onItemClick(E e);
    }

    public void setOnItemClickListener(OnItemClickListener<E> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        notifyDataSetChanged();
    }
}
