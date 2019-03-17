package com.aquarius.common.task;

public interface TaskWatcher {
    void onTaskMessage(int var1, int var2, int var3, Object var4);

    void onTaskError(int var1, int var2, int var3, Object var4);
}
