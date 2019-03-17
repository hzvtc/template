package com.aquarius.common.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

//dialog基类 基于dialogfragment
public abstract class BaseDialog extends DialogFragment {
    private static final String TAG = "BaseDialog";
    protected static final String KEY_LAYOUT_ID = "layoutId";

    protected static final String KEY_WIDTH = "width";

    protected static final String KEY_HEIGHT = "height";

    protected static final String KEY_CANCEL = "cancel";

    protected int mWidth;//View的宽
    protected int mHeight;//View的高
    protected boolean mCancel;//是否可以点击外面取消
    protected int mLayoutId;//View的Id

    public BaseDialog () {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLayoutId = getArguments().getInt(KEY_LAYOUT_ID, 0);
            mWidth = getArguments().getInt(KEY_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT);
            mHeight = getArguments().getInt(KEY_HEIGHT,ViewGroup.LayoutParams.WRAP_CONTENT);
            mCancel = getArguments().getBoolean(KEY_CANCEL, true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去除标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //mLayoutId 布局Id
        return inflater.inflate(mLayoutId, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            //去除背景
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //设置大小
            getDialog().getWindow().setLayout(mWidth,mHeight);
            //设置是否点击外面可以取消
            getDialog().setCanceledOnTouchOutside(mCancel);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //do something
    }
}
