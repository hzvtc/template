package com.aquarius.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected Activity activity;
    protected View rootView;
    private String mTag;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.activity = activity;
    }

    //加载应用的布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, rootView);
            initView();
        }
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }
    protected abstract int getLayoutId();
//    public abstract void notifyFragment(int type, int arg1, int arg2, Object obj);
    protected void refreshView(){

    }
    protected void initView(){

    }

    public String getFragmentTag() {
        return this.mTag;
    }

    public void setFragmentTag(String tag) {
        this.mTag = tag;
    }
}
