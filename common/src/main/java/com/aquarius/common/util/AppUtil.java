package com.aquarius.common.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.*;
import java.nio.charset.Charset;

public class AppUtil {
    private static final String TAG = "AppUtil";

    /**
     * 跳转到外部浏览器
     *
     * @param context
     * @param urlStr
     */
    public static void openSystemBrowser(Context context, String urlStr) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(urlStr);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * 判断app是否被安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public static boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     *
     * @param apkPath 要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public static void install(String apkPath, Callback callback) {
        if (!isRoot()) {
            if (callback != null) {
                callback.callback("手机未Root");
            }
            return;
        }
        if (TextUtils.isEmpty(apkPath)) {
            if (callback != null) {
                callback.callback("安装路径不能为空");
            }
            return;
        }
        DataOutputStream dataOutputStream = null;
        InputStream errIs = null;  //error stream
        InputStream inIs = null;   // process stream
        ByteArrayOutputStream resultStream = null;
        try {

            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            //等待任务完成
            process.waitFor();
            int read = -1;

            resultStream = new ByteArrayOutputStream();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                resultStream.write(read);
            }

            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                resultStream.write(read);
            }

            byte[] data = resultStream.toByteArray();
            String result = new String(data);
            String msg = result;
            if (result.contains("Success")) {
                Log.d(TAG, "安装成功");
                msg = "安装成功";
            }
            if (callback != null) {
                callback.callback(msg);
            }
        } catch (Exception e) {
            if (callback != null) {
                callback.callback(e.getLocalizedMessage());
            }

        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (resultStream != null) {
                    resultStream.close();
                }
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (IOException e) {
                if (callback != null) {
                    callback.callback(e.getLocalizedMessage());
                }
            }
        }
        return;
    }

    public static void uninstall(String apkPath, Callback callback) {
        if (!isRoot()) {
            if (callback != null) {
                callback.callback("手机未Root");
            }
            return;
        }
        if (TextUtils.isEmpty(apkPath)) {
            if (callback != null) {
                callback.callback("卸载路径不能为空");
            }
            return;
        }
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm uninstall -k " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            //等待任务完成
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            line = msg;
            if (msg.contains("Success")) {
                line = "卸载成功";
            }
            if (callback != null) {
                callback.callback(line);
            }
        } catch (Exception e) {
            if (callback != null) {
                callback.callback(e.getLocalizedMessage());
            }
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                if (callback != null) {
                    callback.callback(e.getLocalizedMessage());
                }
            }
        }
        return;
    }

    /**
     * 判断手机是否拥有Root权限。
     *
     * @return 有root权限返回true，否则返回false。
     */
    public static boolean isRoot() {
        boolean bool = false;
        try {
            bool = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
