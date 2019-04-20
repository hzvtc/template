package com.aquarius.base;

import android.content.Context;

import java.util.ArrayList;

public class MoudleManager {
    private static final String TAG = "LocalActivityMgr";
    ArrayList<BaseMoudle> mSocket = new ArrayList();
    static MoudleManager mInstance;
    private Context mContext;

    public static MoudleManager getInstance() {
        if (mInstance == null) {
            mInstance = new MoudleManager();
        }

        return mInstance;
    }

    public void initModules(Context context) {
        this.mContext = context;
        for (BaseMoudle moudle:mSocket){
            moudle.init(context);
        }
    }

    public void addMoudle(BaseMoudle moudle) {
        this.mSocket.add(moudle);
    }

    public int getMoudleCount() {
        return this.mSocket.size();
    }

    public void removeMoudle(BaseMoudle moudle) {
        if (this.mSocket.contains(moudle)) {
            this.mSocket.remove(moudle);
        }
    }
}
