package com.aquarius.log;

import com.orhanobut.logger.Logger;
// tag标签日志封装类
public class MLog {
    public static void d(String tag,String message, Object... args){
        Logger.t(tag);
        Logger.d(message,args);
    }

    public static void d(String tag,Object object){
        Logger.t(tag);
        Logger.d(object);
    }

    public static void e(String tag,String message, Object... args){
        Logger.t(tag);
        Logger.e(message,args);
    }

    public static void e(String tag, Throwable throwable,  String message,  Object... args){
        Logger.t(tag);
        Logger.e(throwable,message,args);
    }

    public static void w(String tag,String message, Object... args){
        Logger.t(tag);
        Logger.w(message,args);
    }

    public static void i(String tag,String message, Object... args){
        Logger.t(tag);
        Logger.i(message,args);
    }

    public static void v(String tag,String message, Object... args){
        Logger.t(tag);
        Logger.e(message,args);
    }

    public static void wtf(String tag,String message, Object... args){
        Logger.t(tag);
        Logger.wtf(message,args);
    }

    public static void json(String tag,String json){
        Logger.t(tag);
        Logger.json(json);
    }

    public static void xml(String tag,String xml){
        Logger.t(tag);
        Logger.xml(xml);
    }
}
