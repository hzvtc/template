package com.aquarius.common.view.recylerview;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置网格布局水平间距和竖直间距
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int horizontalSpacing;
    private int verticalSpacing;

    public SpacesItemDecoration(int horizontalSpacing, int verticalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        //列数
        int spanCount = gridLayoutManager.getSpanCount();
        int position = parent.getChildAdapterPosition(view);
        //不是第一列
        if (position%spanCount!=0){
            outRect.left = horizontalSpacing;
        }
        //不是第一行
        if (position/spanCount!=0){
            outRect.top = verticalSpacing;
        }
    }
}
