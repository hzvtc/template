package com.aquarius.common.app;

import android.app.Application;
import com.aquarius.base.MoudleManager;
import com.aquarius.common.moudle.FrescoMoudle;
import com.aquarius.log.moudle.LoggerMoudle;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //日志
        LoggerMoudle loggerMoudle=new LoggerMoudle();
        //图片加载
        FrescoMoudle frescoMoudle=new FrescoMoudle();
        MoudleManager.getInstance().addMoudle(loggerMoudle);
        MoudleManager.getInstance().addMoudle(frescoMoudle);
        MoudleManager.getInstance().initModules(this);
    }
}
