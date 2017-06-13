package com.wisdomregulation.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.widget.Toast;

import org.apkplug.Bundle.InstallBundler;
import org.apkplug.Bundle.InstallInfo;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkFactory;
import org.osgi.framework.StartCallback;

import java.io.File;

public class PdfPrintMaster {
    /**
     * @param pdfpath   pdf路劲
     * 在 application中使用
    try {
    FrameworkInstance frame= FrameworkFactory.getInstance().start(null,this);
    } catch (Exception e) {
    e.printStackTrace();
    }
     * @param activity
     * @param result startactivity的resquestcode
     */
    private static boolean isplugin = false;

    public static void print(final String pdfpath, final Activity activity, int resquestcode) {
        if (isplugin) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(activity, "启动插件--请稍等", Toast.LENGTH_SHORT).show();
                    try {
                        FrameworkFactory.getInstance().start(null,activity.getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    InstallBundler ib = new InstallBundler(FrameworkFactory.getInstance().getFrame().getSystemBundleContext());
                    ib.Debug(false); //设置为调试模式, 在调试模式下才会重复安装assets目录下的apk文件, 而非调试模式下只会安装一次
                    InstallInfo installinfo = new InstallInfo();
                    installinfo.isInstallAPK = true;  //设置可以安装普通的apk文件 , apkplug默认情况只能安装含有assets/plugin.xml的apk文件,因此对于普通的apk文件需要配置这个参数为true
                    ib.installForAssets("PrinterShare_Crack.app", "100", installinfo, new installCallback() {
                        //在非调试模式下 只有String assetsName, String version 这两个变量改变才会重新执行安装流程
                        @Override
                        public void callback(int arg0, org.osgi.framework.Bundle arg1) {
                            //Log.e("status改动了一点点",""+arg0);
                            if (arg0 == installCallback.success_install) {

                            } else if (arg0 == installCallback.success_update) {

                            } else if (arg0 == installCallback.error_cache_not_updated) {

                            }
                        }
                    });
                    org.osgi.framework.Bundle[] all_bundles = FrameworkFactory.getInstance().getFrame().getSystemBundleContext().getBundles();
                    org.osgi.framework.Bundle plug = all_bundles[1]; //取出第二个插件, 因为第一个插件永远是由apkplug自动生成的SystemBundle
                    try {
                        plug.start(new StartCallback() {

                            @Override
                            public void onSuccess(org.osgi.framework.Bundle bundle) {

//                            String plugMianActivity=bundle.getBundleActivity().split(",")[0]; //获取插件的启动Activity
//                            Log.v("SRXP",plugMianActivity);
                                Uri fileuri = Uri.fromFile(new File(pdfpath));
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setClassName(activity, "com.dynamixsoftware.printershare.ActivityPrintPDF");
                                intent.setDataAndType(fileuri, "application/pdf");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);

                            }

                            @Override
                            public void onFail(org.osgi.framework.Bundle bundle, Throwable throwable) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            }).start();

        } else {
//            Uri fileuri = Uri.fromFile(new File(pdfpath));
//            if (Util_Apk.appIsInstalled13(activity, Static_BookLib.pack)) {
//                File file = new File(fileuri.toString());
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                i.setPackage(Static_BookLib.pack);
//                i.setDataAndType(fileuri, "application/pdf");
//                activity.startActivity(i);
//            } else {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Looper.prepare();
//                        Toast.makeText(activity, "安装打印插件", Toast.LENGTH_SHORT).show();
//                        Util_Apk.appInstall13(activity, Static_BookLib.pack, Static_BookLib.apk);
//                        Looper.loop();
//                    }
//                }).start();
//
//            }
        }

    }
}
