package com.teemo.schoolmap.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.teemo.schoolmap.R;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/20 17:59
 * @description
 */
public class LoadingViewDialog {

    public static AlertDialog builder(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(R.layout.dialog_loading);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.8f;
        window.setAttributes(lp);
        return dialog;
    }
}
