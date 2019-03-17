package com.aquarius.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.aquarius.common.R;

/**
 * Created by FJQ on 2018/7/10.
 * Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static final int ANIMATION_IN = 1;
    private static final int ANIMATION_BACK = 2;
    protected BaseFragment mBaseFragment;
    protected int curFragmentTag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    protected void refreshView() {

    }

    protected void initView() {

    }

    public abstract int getLayoutId();
    public void switchFragment(int witchFragment, Bundle bundle, int animType) {
        curFragmentTag = witchFragment;
        goToFragement(getFragmentByTag(witchFragment),bundle,animType);
    }
    protected void goToFragement(BaseFragment fragment, Bundle bundle, int animType) {
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //跳转页面
        if (animType == ANIMATION_IN) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (animType==ANIMATION_BACK){
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        fragmentTransaction.replace(getContainerViewId(), fragment);
        fragmentTransaction.commit();
        mBaseFragment = fragment;
    }
    protected abstract int getContainerViewId();
    protected abstract BaseFragment getFragmentByTag(int witchFragment);
}
