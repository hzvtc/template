package com.aquarius.common.view.recylerview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.aquarius.common.R;

/**
 * 适用于整个item的点击事件
 */

public class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetectorCompat;//手势探测器
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childViewUnder != null) {
                            ViewHolder childViewHolder = (ViewHolder) mRecyclerView.
                                    getChildViewHolder(childViewUnder);
                            int position = Integer.parseInt(childViewUnder.getTag(R.id.tag_first).toString());
                            onItemClick(childViewHolder, position);

                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childViewUnder != null) {
                            ViewHolder childViewHolder = (ViewHolder) mRecyclerView.
                                    getChildViewHolder(childViewUnder);
                            int position = Integer.parseInt(childViewUnder.getTag(R.id.tag_first).toString());
                            onLongClick(childViewHolder, position);
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public void onItemClick(ViewHolder viewHolder, int position) {
    }

    public void onLongClick(ViewHolder viewHolder, int position) {
    }
}
