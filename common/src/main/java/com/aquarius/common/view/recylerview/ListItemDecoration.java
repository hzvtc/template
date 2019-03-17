package com.aquarius.common.view.recylerview;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置水平和竖直布局的间距
 */

public class ListItemDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;

    public ListItemDecoration(int dividerHeight) {
        this.dividerHeight = dividerHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        if (position != 0) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = dividerHeight;
            } else {
                outRect.left = dividerHeight;
            }
        }
    }
}
