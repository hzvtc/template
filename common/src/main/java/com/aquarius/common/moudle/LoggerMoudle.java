package com.aquarius.common.moudle;

import android.content.Context;
import com.aquarius.common.base.BaseMoudle;
import com.aquarius.common.log.LogUtil;
import com.orhanobut.logger.*;

/**
 * logger 日志框架涉及到设计模式之策略模式 实现日志输出到控制台 输出到本地的功能
 * 建造者模式 具体建造者 Builder 不存在抽象建造者 抽象使用者 FormatStrategy 具体使用者CsvFormatStrategy PrettyFormatStrategy
 * 使用HandlerThread+handler 执行日志写入操作
 */
public class LoggerMoudle implements BaseMoudle {

    @Override
    public void init(Context context) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .tag("LoggerMoudle")
                .logStrategy(LogUtil.createDiskLogStrategy(context))
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
    }
}
