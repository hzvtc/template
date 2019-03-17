package com.aquarius.common.util;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class LocalActivityMgr {
    private static final String TAG = "LocalActivityMgr";
    ArrayList<Activity> mSocket = new ArrayList();
    static LocalActivityMgr mInstance;
    private Context mContext;

    public static LocalActivityMgr getInstance() {
        if (mInstance == null) {
            mInstance = new LocalActivityMgr();
        }

        return mInstance;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return (Context)(this.getTopActivity() != null ? this.getTopActivity() : this.mContext);
    }

    private LocalActivityMgr() {
    }

    public void pushActivity(Activity activity) {
        this.mSocket.add(activity);
    }

    public void popActivity(Activity activity) {
        if (this.mSocket.contains(activity)) {
            int index = this.mSocket.indexOf(activity);
            if (this.mSocket.size() > 0 && index < this.mSocket.size()) {
                Activity screen = (Activity)this.mSocket.get(index);
                screen.finish();
                this.mSocket.remove(activity);
            }
        }

    }

    public Activity getTopActivity() {
        if (this.mSocket.size() > 0) {
            Activity screen = (Activity)this.mSocket.get(this.mSocket.size() - 1);
            return screen;
        } else {
            return null;
        }
    }

    public int getActivityCount() {
        return this.mSocket.size();
    }

    public void removeActivity(Activity activity) {
        if (this.mSocket.contains(activity)) {
            this.mSocket.remove(activity);
        }

    }

    public void popActivity() {
        if (this.mSocket.size() > 0) {
            Activity screen = (Activity)this.mSocket.remove(this.mSocket.size() - 1);
            screen.finish();
        }

    }

    public void clearActivity() {
        Activity screen = null;

        while(this.mSocket.size() > 0) {
            screen = (Activity)this.mSocket.remove(this.mSocket.size() - 1);
            screen.finish();
        }

    }

    public ArrayList<Activity> getAllActivity() {
        return this.mSocket;
    }
}
