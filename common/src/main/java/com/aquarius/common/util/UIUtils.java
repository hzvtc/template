package com.aquarius.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class UIUtils {
    public static boolean isEmpty(List list){
        return list==null||list.size()==0;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity, String... permission)
    {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission)
        {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED)
            {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    public static Activity getActivity(Object object) {
        Activity result = null;
        if (object instanceof Activity) {
            result = (Activity) object;
        } else if (object instanceof Fragment) {
            result = ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            result = ((android.app.Fragment) object).getActivity();
        }
        return result;
    }

    public static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        Activity activity = UIUtils.getActivity(object);
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,perm);
    }

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
