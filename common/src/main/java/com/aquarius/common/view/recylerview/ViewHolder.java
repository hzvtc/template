package com.aquarius.common.view.recylerview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.aquarius.common.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


public class ViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    private List<RecyclerView> list;
    public ViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        list = new ArrayList<>();
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view==null){
            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    // 查找item下的所有item
    private List<RecyclerView> findNestedRecyclerView(@NonNull View view) {
        List<RecyclerView> list = new ArrayList<>();
        if (view instanceof RecyclerView) {
            list.add((RecyclerView) view);
        }
        if (!(view instanceof ViewGroup)) {
            return list;
        }
        final ViewGroup parent = (ViewGroup) view;
        final int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = parent.getChildAt(i);
            list.addAll(findNestedRecyclerView(child));
        }
        return list;
    }
    // item下存在recylerview 服用RecycledViewPool 提高加载速度
    public void setRecyclerViewPool(RecyclerView.RecycledViewPool pool){
        this.list = findNestedRecyclerView(itemView);
        for (RecyclerView rv:list){
            rv.setRecycledViewPool(pool);
        }
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为View设置透明度
     * @param viewId
     * @param value
     * @return
     */
    public ViewHolder setAlpha(int viewId,float value){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            getView(viewId).setAlpha(value);
        } else
        {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * 切换文字正常和选中的样式
     * @param viewId
     * @param selected
     * @return
     */
    public ViewHolder setTextState(int viewId,boolean selected){
        TextView textView = getView(viewId);
        textView.setSelected(selected);
        return this;
    }

    /**
     * 切换图片正常和选中的样式
     * @param viewId
     * @param selected
     * @return
     */
    public ViewHolder setImageState(int viewId,boolean selected){
        ImageView imageView = getView(viewId);
        imageView.setSelected(selected);
        return this;
    }

    public ViewHolder setImageUri(int viewId,Uri path){
        SimpleDraweeView simpleDraweeView = getView(viewId);
        simpleDraweeView.setImageURI(path);
        return this;
    }
    // 适用于整个item中多个空间的时间绑定
    public ViewHolder setOnClickViewListener(int viewId, final OnViewClickListener listener) {
                    final View view = getView(viewId);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                if (listener!=null){
                                    int position = Integer.parseInt(itemView.getTag(R.id.tag_first).toString());
                                    listener.OnViewClick(ViewHolder.this,view,position);
                }
            }
        });
        return this;
    }

    public void setRecylerViewPool() {

    }
}
