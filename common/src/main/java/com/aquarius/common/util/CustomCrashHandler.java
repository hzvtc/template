package com.aquarius.common.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.UndeclaredThrowableException;
import java.text.SimpleDateFormat;
import java.util.*;

import static android.content.Context.ALARM_SERVICE;

public class CustomCrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CustomCrashHandler";
    private Context mContext;
    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
    private static CustomCrashHandler mInstance = new CustomCrashHandler();
    private String packageName = null;
    private int restartTime = 2000;

    private CustomCrashHandler() {
    }

    public static CustomCrashHandler getInstance() {
        return mInstance;
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        this.savaInfoToSD(this.mContext, ex);
        LogUtil.d("CustomCrashHandler", ex.getLocalizedMessage());
        this.reStart();
        LogUtil.d("CustomCrashHandler", "researt kill:" + this.packageName + " time:" + this.restartTime);
        LocalActivityMgr.getInstance().clearActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void reStart() {
        if (this.packageName != null) {
            Intent intent = this.mContext.getPackageManager().getLaunchIntentForPackage(this.packageName);
            PendingIntent pi = PendingIntent.getActivity(mContext, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + restartTime, pi);

        }
    }

    public void setReStartPackage(String packageName, int startTime) {
        this.packageName = packageName;
        this.restartTime = startTime;
    }

    public void setReStartPackage(String packageName) {
        this.packageName = packageName;
    }

    public void setCustomCrashHanler(Context context) {
        this.mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap();
        map.put("versionName", AppUtil.getVersionName(context));
        map.put("versionCode", "" + AppUtil.getVersionCode(context));
        map.put("MODEL", Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", Build.PRODUCT);
        return map;
    }

    /**
     * 获取Throwable里头的错误信息
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        String msg = "";
        if (throwable instanceof UndeclaredThrowableException){
            Throwable targetEx = ((UndeclaredThrowableException) throwable).getUndeclaredThrowable();
            if (targetEx != null){
                msg = targetEx.getMessage();
            }
        } else {
            msg = throwable.getMessage();
        }
        return msg;
    }

    /**
     * 将异常信息保存在本地
     * @param context
     * @param ex
     * @return
     */
    private String savaInfoToSD(Context context, Throwable ex) {
        String fileName = SDCardUtils.getSDCardPath() + "vms" + File.separator + "crash" +
                File.separator + this.paserTime(System.currentTimeMillis()) + ".log";
        StringBuffer sb = new StringBuffer();
        Iterator var6 = this.obtainSimpleInfo(context).entrySet().iterator();

        while (var6.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var6.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        sb.append(this.obtainExceptionInfo(ex));
        if (SDCardUtils.isSDCardEnable()) {
            File dir = new File(fileName);
            FileUtil.writeFileByChars(dir,sb.toString());
        }

        return fileName;
    }

    private String paserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String times = format.format(new Date(milliseconds));
        return times;
    }
}
