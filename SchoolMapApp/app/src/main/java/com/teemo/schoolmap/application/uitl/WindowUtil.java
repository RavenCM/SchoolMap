package com.teemo.schoolmap.application.uitl;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.21 01:17
 * @description
 */
public class WindowUtil {

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    public static int dipToPx(Context context, int dip) {
        double density = context.getResources().getDisplayMetrics().density;
        return (int) ((float) dip * density + 0.5F);
    }
}
