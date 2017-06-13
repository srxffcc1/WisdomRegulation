package com.wisdomregulation.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.wisdomregulation.staticlib.Static_ConstantLib;
import com.wisdomregulation.staticlib.Static_InfoApp;

import java.io.File;

/**
 * Created by King6rf on 2017/2/21.
 */

public class Util_Print {
    public static void print(String pdfpath, Activity activity, int resquestcode ){
        Uri fileuri = Uri.fromFile(new File(pdfpath));
        if(Util_Apk.appIsInstalled13(Static_InfoApp.create().getContext(), Static_ConstantLib.pack)){
            File file = new File(fileuri.toString());
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.setPackage(Static_ConstantLib.pack);
            i.setDataAndType(fileuri, "application/pdf");
            activity.startActivity(i);
        }else{
            Util_Apk.appInstall13(activity, Static_ConstantLib.pack, Static_ConstantLib.apk);
            activity.finish();
        }
    }
}
