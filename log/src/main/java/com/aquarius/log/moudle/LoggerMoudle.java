package com.aquarius.log.moudle;

import android.content.Context;
import com.aquarius.base.BaseMoudle;
import com.aquarius.log.LogUtil;
import com.orhanobut.logger.*;

import java.text.SimpleDateFormat;

/**
 * logger 作为框架的入口 其中使用了多种设计模式 代理模式--LoggerPrinter 用于委托打印日志的
 * 功能 面向接口编程在该框架中也有体现 LogAdapter对应的两个子类
 */
public class LoggerMoudle implements BaseMoudle {
    public static String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    @Override
    public void init(Context context) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .logStrategy(LogUtil.createDiskLogStrategy(context))
                .dateFormat(new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN))
                .build();
        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
