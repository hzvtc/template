package com.aquarius.common.log;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.aquarius.common.util.SDCardUtils;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.LogStrategy;

public class LogUtil {
    private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file
    @NonNull
    static <T> T checkNotNull(@Nullable final T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    public static LogStrategy createDiskLogStrategy(Context context){
        String folder= SDCardUtils.getAppPath(context);
        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        Handler handler = new WriteHandler(ht.getLooper(), folder, MAX_BYTES);
        return new DiskLogStrategy(handler);
    }
}
