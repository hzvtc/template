package com.aquarius.common.util;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.aquarius.common.base.BaseFragment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FragmentMgr {
    private static final String TAG = "FragmentMgr";
    LinkedHashMap<String, BaseFragment> mFragementMap = new LinkedHashMap();
    static FragmentMgr mInstance;
    HashMap<String, Class<?>> mFragementTypeMap;
    private FragmentManager mFragmentManager = null;
    private FragmentActivity mActivity = (FragmentActivity) LocalActivityMgr.getInstance().getTopActivity();

    public static FragmentMgr getInstance() {
        if (mInstance == null) {
            mInstance = new FragmentMgr();
        }

        return mInstance;
    }

    private FragmentMgr() {
        this.mFragmentManager = this.mActivity.getSupportFragmentManager();
    }

    public void putFragment(String tag, BaseFragment fragment) {
        this.mFragementMap.put(tag, fragment);
    }

    public BaseFragment getFragment(String tag) {
        return (BaseFragment)this.mFragementMap.get(tag);
    }

    public void popFragment(String tag) {
        if (this.mFragementMap.containsKey(tag)) {
            BaseFragment bf = (BaseFragment)this.mFragementMap.get(tag);
            if (this.mFragmentManager.getBackStackEntryCount() > 0) {
                this.mFragmentManager.popBackStackImmediate(tag, 1);
            }

            LogUtil.d("FragmentMgr", " pop popBackStackImmediate tag:" + tag);
            bf.onDetach();
            this.mFragementMap.remove(tag);
        }

    }

    public void popTopFragment() {
        BaseFragment topFragment = this.getTopFragment();
        if (topFragment != null) {
            this.popFragment(topFragment.getFragmentTag());
        }

        LogUtil.d("FragmentMgr", "popTopFragment topFragment tag:" + topFragment.getFragmentTag());
    }

    public BaseFragment getTopFragment() {
        Iterator it = this.mFragementMap.entrySet().iterator();

        BaseFragment lastFragment;
        Map.Entry entry;
        for(lastFragment = null; it.hasNext(); lastFragment = (BaseFragment)entry.getValue()) {
            entry = (Map.Entry)it.next();
        }

        LogUtil.d("FragmentMgr", "getTopFragment lastFragment:" + lastFragment);
        return lastFragment;
    }

    public void popToFragmentByTag(String tag) {
        if (tag != null && !"".equals(tag)) {
            if (this.mFragementMap.containsKey(tag)) {
                Iterator it = this.mFragementMap.entrySet().iterator();
                BaseFragment curFragment = null;

                while(it.hasNext()) {
                    Map.Entry entry = (Map.Entry)it.next();
                    String key = entry.getKey().toString();
                    if (!tag.equals(key)) {
                        curFragment = (BaseFragment)entry.getValue();
                        this.popFragment(curFragment.getFragmentTag());
                    }
                }

                LogUtil.d("FragmentMgr", "popToFragmentByTag curFragment:" + curFragment);
            }

        } else {
            this.clearFragment();
        }
    }

    public void popToFragmentByClass(Class<?> cls) {
        if (cls == null) {
            this.clearFragment();
        } else {
            BaseFragment curFragment = null;

            for(BaseFragment preFragment = null; this.mFragementMap.size() > 0 && this.mFragmentManager.getBackStackEntryCount() > 0; LogUtil.d("FragmentMgr", " size: " + this.mFragementMap.size() + " count:" + this.mFragmentManager.getBackStackEntryCount())) {
                curFragment = this.getTopFragment();
                if (curFragment.equals(preFragment) && preFragment != null) {
                    LogUtil.d("FragmentMgr", " last fragment is:" + curFragment);
                    break;
                }

                LogUtil.d("FragmentMgr", " popToFragmentByClass curFragment:" + curFragment);
                if (!curFragment.getClass().toString().equals(cls.toString())) {
                    LogUtil.d("FragmentMgr", " popFragment curFragment tag:" + curFragment.getFragmentTag());
                    this.mFragmentManager.popBackStack();
                    this.mFragementMap.remove(curFragment.getFragmentTag());
                    preFragment = curFragment;
                }
            }

        }
    }

    public int getFragmentCount() {
        return this.mFragementMap.size();
    }

    public void clearFragment() {
        BaseFragment fragment = null;
        Iterator it = this.mFragementMap.entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String tag = entry.getKey().toString();
            if (this.mFragmentManager.getBackStackEntryCount() > 0) {
                this.mFragmentManager.popBackStackImmediate(tag, 1);
            }

            BaseFragment fr = (BaseFragment)entry.getValue();
            fr.onDestroy();
        }

        this.mFragementMap.clear();
    }

    public void pushTypesFragment(String tag, Class<?> classfragment) {
        if (this.mFragementTypeMap == null) {
            this.mFragementTypeMap = new HashMap();
        }

        this.mFragementTypeMap.put(tag, classfragment);
    }

    public Class<?> getTypeFragment(String tag) {
        return this.mFragementTypeMap.containsKey(tag) ? (Class)this.mFragementTypeMap.get(tag) : null;
    }
}
