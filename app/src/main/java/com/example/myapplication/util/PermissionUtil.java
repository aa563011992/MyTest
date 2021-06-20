package com.example.myapplication.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * Created by wyl on 2018/11/21.
 */

public class PermissionUtil {
    private static final String TAG = "PermissionUtil";
    private static final String packageName = "com.doc360.client";

    /**
     * @date create time 2019/3/18
     * @author wyl
     * @Description 启动应用时申请必要权限
     * @Param
     * @Version v550
     */
    public static void requestNecessaryPermissionWhenAPPStart(final Runnable after, final Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    try {
                        after.run();
                        if (aBoolean) {
                            // 用户已经同意该权限

                        } else {
                            // 用户拒绝了该权限
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * @author: wyl
     * @description: 检测是否有某权限
     * @date: 2021/5/25 14:22
     */
    public static boolean checkPermission(String permission, Context context) {
        PackageManager pm = context.getPackageManager();
        return (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permission, context.getPackageName()));
    }

    /**
     * @author: wyl
     * @description: 同时获取文件存储与拍照权限
     * @date: 2021/5/24 10:08
     */
    public static void requestCameraAndStorage(final Runnable after, final Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {
                    try {
                        after.run();
                        if (aBoolean) {
                            // 用户已经同意该权限

                        } else {
                            // 用户拒绝了该权限
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void requestCameraAndStorage(final Runnable after, final Activity activity,String... strings) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(strings)
                .subscribe(aBoolean -> {
                    try {
                        after.run();
                        if (aBoolean) {
                            // 用户已经同意该权限

                        } else {
                            // 用户拒绝了该权限
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
