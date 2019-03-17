package com.aquarius.common.view.recylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aquarius.common.R;

import java.util.List;

/**
 * 1.点击同一个item不处理
 * @param <T>
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "CommonAdapter";
    private Context mContext;
    private List<T> mDatas;
    private int mItemLayoutId;
    public CommonAdapter(Context mContext, List<T> mDatas, int mItemLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemLayoutId = mItemLayoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        position = position%mDatas.size();
        holder.itemView.setTag(R.id.tag_first,position);
        holder.itemView.setTag(R.id.tag_second,mDatas.get(position));
        convert(holder,mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas!=null&&mDatas.size()>0){
            return mDatas.size();
        }
        return 0;
    }

    public abstract void convert(ViewHolder helper, T item);
    //刷新列表
    public void refreshList(List<T> goodsList) {
        this.mDatas = goodsList;
        notifyDataSetChanged();
    }
    //添加单个刷新
    public void addrefreshList(T item) {
        this.mDatas.add(item);
        int position = mDatas.size()-1;
        notifyItemInserted(position);
    }
    //删除单个刷新
    public void deleteRefreshList(int position,T item){
        this.mDatas.remove(item);
        notifyItemRemoved(position);
    }
    /**
     * 下拉刷新 上拉加载
     * @param goodsList
     * @param type 0 代表下拉刷新 1 代表上拉加载
     */
    public void addrefreshList(List<T> goodsList,int type) {
        int position;
        if (type==0){
            position = 0;
        }
        else {
            position = this.mDatas.size();
        }
        this.mDatas.addAll(position,goodsList);
        notifyItemRangeChanged(position,goodsList.size());
    }

    //刷新item
    public void refreshItem(int position){
        notifyItemChanged(position);
    }
}
