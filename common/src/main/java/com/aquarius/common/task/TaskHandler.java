package com.aquarius.common.task;

import android.os.Handler;
import android.os.Message;

public abstract class TaskHandler extends Handler implements TaskWatcher {
    public TaskHandler() {
    }

    public abstract void handleError(int var1, int var2, int var3, Object var4);

    public abstract void handleMessage(int var1, int var2, int var3, Object var4);

    public void handleMessage(Message msg) {
        if (msg.what >= 0) {
            this.handleMessage(msg.what, msg.arg1, msg.arg2, msg.obj);
        } else {
            this.handleError(-msg.what, msg.arg1, msg.arg2, msg.obj);
        }

    }

    public final void onTaskMessage(int code, int arg1, int arg2, Object obj) {
        Message msg = Message.obtain(this, code, arg1, arg2, obj);
        this.sendMessage(msg);
    }

    public final void onTaskError(int errCode, int arg1, int arg2, Object obj) {
        errCode = -errCode;
        Message msg = Message.obtain(this, errCode, arg1, arg2, obj);
        this.sendMessage(msg);
    }
}
