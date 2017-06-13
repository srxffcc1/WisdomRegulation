package com.wisdomregulation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wisdomregulation.frame.CallBack;
import com.wisdomregulation.help.Help_DB;
import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util_Sdk {
    /**
     * 写入数据库文件到指定位置
     *
     * @param context
     */
    private static void initdatabase(Context context) {
        try {
            if (new File(Static_InfoApp.create().getPath()
                    + "/ZhiDbhome/" + "/" + Static_InfoApp.create().getdbname() + "").exists()) {
                new File(Static_InfoApp.create().getPath()
                        + "/ZhiDbhome/" + "/" + Static_InfoApp.create().getdbname() + "").renameTo(new File(Static_InfoApp.create().getPath()
                        + "/ZhiDbhome/" + "/" + Static_InfoApp.create().getdbname() + "delete"));
                new File(Static_InfoApp.create().getPath()
                        + "/ZhiDbhome/" + "/" + Static_InfoApp.create().getdbname() + "delete").delete();
            }
            InputStream is = context.getAssets().open(
                    "" + Static_InfoApp.create().getdbname() + "");
            FileOutputStream fos = new FileOutputStream(
                    Static_InfoApp.create().getPath()
                            + "/ZhiDbhome/" + "/" + Static_InfoApp.create().getdbname() + "");
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            is.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 写入临时数据库文件到指定位置 为升级做准备
     *
     * @param context
     */
    private static void initdatabasetmp(Context context) {
        try {
            InputStream is = context.getAssets().open(
                    "" + Static_InfoApp.create().getdbname() + "");
            FileOutputStream fos = new FileOutputStream(
                    Static_InfoApp.create().getPath()
                            + "/ZhiDbhome/" + "/tmp" + Static_InfoApp.create().getdbname() + "");
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            is.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 将视频文件写入本地---用于演示
     *
     * @param context
     */
    private static void initvideo(Context context) {
//        try {
//            InputStream is = context.getAssets().open(
//                    "testcompany.mp4");
//            FileOutputStream fos = new FileOutputStream(
//                    Static_InfoApp.create().getPath()
//                            + "/ZhiDbhome/" + "/testcompany.mp4");
//            byte[] buffer = new byte[1024];
//            int byteCount = 0;
//            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
//                // buffer字节
//                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
//            }
//            fos.flush();// 刷新缓冲区
//            is.close();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    /**
     * 初始化文件目录
     *
     * @param context
     */
    private static void initdir(Context context,boolean needdeletedir) {

        try {
            String path = Static_InfoApp.create().getPath();
            System.out.println("项目文件目录:"+path);
            if (needdeletedir) {
//                Log.v("SRXDir", "Delete");
                delete(new File(Static_InfoApp.create().getPath() + "/"));
            }

            new File(Static_InfoApp.create().getPath() + "/")
                    .mkdirs();
            new File(Static_InfoApp.create().getPath() + "/TestPdf/")
                    .mkdirs();
            new File(Static_InfoApp.create().getPath() + "/ZhiHead/")
                    .mkdirs();
            new File(Static_InfoApp.create().getPath() + "/ZhiCollect/")
                    .mkdirs();
            new File(Static_InfoApp.create().getPath() + "/ZhiExport/")
                    .mkdirs();
            new File(Static_InfoApp.create().getPath() + "/ZhiApk/")
                    .mkdirs();
            new File(Static_InfoApp.create().getPath() + "/ZhiDbhome/")
                    .mkdirs();
            //System.out.println("项目文件目录生成成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 字体 数据库 和一些 数据
     *
     * @param context
     * @param back
     * @return
     */
    public static int initializeData(Context context, CallBack back) {
        initdir(context,false);
        SharedPreferences sp = context.getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
        boolean fontcreate = sp.getBoolean("fontcreate", false);//判断字体是否写入过 不排除字体被人删除
        int dbcreate = sp.getInt("dbcreate", -1);//判断数据库是否被写入过   默认数据库等级-1  为-1说明可能是重新安装的
        if(dbcreate==-1){
            Log.v("SRXDELETE","删除目录");
            initdir(context,true);
        }

        if (fontcreate) {

        } else {
            if (back != null) {
                back.back("初始化");
            }
            initdir(context,false);
            initapk(context);
            initttfsim(context);
            initttfsimf(context);
            inittest(context);
            initvideo(context);
        }
        sp.edit().putBoolean("fontcreate", true).commit();
        //System.out.println("数据库等级"+dbcreate);
        if (dbcreate == -1||dbcreate==-2) {
            if (Static_InfoApp.create().isshow()) {
                initattach(context);
                initinsert(context);
            }
            if (new File(Static_InfoApp.create().getPath()
                    + "/ZhiDbhome/" + "/" + Static_InfoApp.create().getdbname() + "").exists()) {
                if (Static_InfoApp.create().isshow()) {
                    if (back != null) {
                        back.back("debug初始化");
                    }
                    initdatabasetmp(context);
                    Help_DB.create(context, "" + Static_InfoApp.create().getdbname() + "").updataDatabaseDebug();
                } else {
                    if (back != null) {
                        back.back("初始化");
                    }

                    initdatabase(context);
                    Help_DB.create(context, "" + Static_InfoApp.create().getdbname() + "").updataDatabaseSpecial();
                }

            } else {
                if (back != null) {
                    back.back("初始化");
                }
                initdatabase(context);
                Help_DB.create(context, "" + Static_InfoApp.create().getdbname() + "").updataDatabaseSpecial();
            }
//		initdatabase(context);

        } else {
            if (dbcreate != Static_ConstantLib.anjiandbversion) {
                if (back != null) {
                    back.back("升级数据库");
                }
                if (new File(Static_InfoApp.create().getPath()
                        + "/ZhiDbhome/" + "/" + Static_InfoApp.create().getdbname() + "").exists()) {
                    initdatabasetmp(context);
                    sp.edit().putBoolean("isneedupdatebase", true).commit();
                } else {
                    back.back("初始化");
                    initdatabase(context);
                }


            } else {
                if (new File(Static_InfoApp.create().getPath()
                        + "/ZhiDbhome/" + "/" + Static_InfoApp.create().getdbname() + "").exists()) {

                } else {
                    back.back("初始化");
                    initdatabase(context);
                }
            }
        }
        sp.edit().putInt("dbcreate", Static_ConstantLib.anjiandbversion).commit();
        return 1;
    }

    /**
     * 初始化需要attach的数据库 目前用于debug测试
     *
     * @param context
     */
    private static void initattach(Context context) {
        try {
            InputStream is = context.getAssets().open(
                    "dep.db");
            FileOutputStream fos = new FileOutputStream(
                    Static_InfoApp.create().getPath()
                            + "/ZhiDbhome/" + "/dep.db");
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            is.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 写入测试用insert
     *
     * @param context
     */
    private static void initinsert(Context context) {
        try {
            InputStream is = context.getAssets().open(
                    "orgdata_1.txt");
            FileOutputStream fos = new FileOutputStream(
                    Static_InfoApp.create().getPath()
                            + "/ZhiDbhome/" + "/orgdata_1.txt");
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            is.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 写入测试pdf到指定位置 用于可以在安装完之后测试打印
     *
     * @param context
     */
    private static void inittest(Context context) {
        if (!new File(Static_InfoApp.create().getPath()
                + "/TestPdf" + "/test.pdf").exists()) {
            try {

                InputStream is = context.getAssets().open(
                        "test.pdf");
                FileOutputStream fos = new FileOutputStream(
                        Static_InfoApp.create().getPath()
                                + "/TestPdf" + "/test.pdf");
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入一个破解版的 PrinterShare.apk到指定位置 用于可以读取安装
     *
     * @param context
     */
    private static void initapk(Context context) {
//        if (!new File(Static_InfoApp.create().getPath()
//                + "/ZhiAPk" + "/PrinterShare_Crack").exists()) {
//            try {
//
//                InputStream is = context.getAssets().open(
//                        "PrinterShare_Crack");
//                FileOutputStream fos = new FileOutputStream(
//                        Static_InfoApp.create().getPath()
//                                + "/ZhiAPk" + "/PrinterShare_Crack");
//                byte[] buffer = new byte[1024];
//                int byteCount = 0;
//                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
//                    // buffer字节
//                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
//                }
//                fos.flush();// 刷新缓冲区
//                is.close();
//                fos.close();
//
//
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 写入字体文件 宋体
     *
     * @param context
     */
    private static void initttfsimf(Context context) {
//        if (!new File(Static_InfoApp.create().getPath()
//                + "/simfang.ttf").exists()) {
//            try {
//
//                InputStream is = context.getAssets().open(
//                        "simfang.ttf");
//                FileOutputStream fos = new FileOutputStream(
//                        Static_InfoApp.create().getPath()
//                                + "/simfang.ttf");
//                byte[] buffer = new byte[1024];
//                int byteCount = 0;
//                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
//                    // buffer字节
//                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
//                }
//                fos.flush();// 刷新缓冲区
//                is.close();
//                fos.close();
//
//
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }

    }

    /**
     * 写入字体文件黑体
     *
     * @param context
     */
    private static void initttfsim(Context context) {
//        if (!new File(Static_InfoApp.create().getPath()
//                + "/simhei.ttf").exists()) {
//            try {
//
//                InputStream is = context.getAssets().open(
//                        "simhei.ttf");
//                FileOutputStream fos = new FileOutputStream(
//                        Static_InfoApp.create().getPath()
//                                + "/simhei.ttf");
//                byte[] buffer = new byte[1024];
//                int byteCount = 0;
//                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
//                    // buffer字节
//                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
//                }
//                fos.flush();// 刷新缓冲区
//                is.close();
//                fos.close();
//
//
//            } catch (FileNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 初始化sp信息 重置数据时可以使用 此方法
     *
     * @param context
     */
    public static void initializeSp(Context context) {
        String oldpath = Static_InfoApp.create().getPath();

        SharedPreferences sp = context.getSharedPreferences("releaseconfig",
                Context.MODE_PRIVATE);
        sp.edit().clear().commit();
        sp.edit().putString("sdpath", oldpath).commit();
        sp.edit().putString("isfirst", "1").commit();

    }

    /**
     * 更换应用角色模式
     *
     * @param context
     * @param ip
     * @param istest
     * @param isshow
     * @param ismarker
     * @param callback
     */
    public static void initializeMode(Context context, String ip, boolean istest, boolean isshow, boolean ismarker, final CallBack callback) {
        String oldpath = Static_InfoApp.create().getPath();
        SharedPreferences sp = context.getSharedPreferences("releaseconfig",
                Context.MODE_PRIVATE);
        long oldtime = sp.getLong("oldtime", 0);
        int dbint=sp.getInt("dbcreate", 0);
        sp.edit().clear().commit();
        sp.edit().putLong("oldtime", oldtime).commit();;
        sp.edit().putString("sdpath", oldpath).commit();
        sp.edit().putString("isfirst", "1").commit();
        sp.edit().putString("ip", ip).commit();
        sp.edit().putBoolean("istest", istest).commit();
        sp.edit().putBoolean("isshow", isshow).commit();
        sp.edit().putBoolean("ismarker", ismarker).commit();
        sp.edit().putInt("dbcreate", -2).commit();
        callback.back(null);

    }

    /**
     * 删除文件夹需要递归删除的
     * @param file
     */
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }
}
