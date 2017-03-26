package com.teemo.schoolmap.common.uitl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Teemo on 2017/3/19.
 * ClassName: SchoolMapApp
 * Description: 封装 Bitmap 相关的方法，因为默认的 Bitmap 加载很耗内存，会导致OOM，所以重写
 */
public class BitmapUtil {

    /**
     * 获取 Bitmap 合适的缩放比例
     *
     * @param requireWidth  屏幕宽
     * @param requireHeight 屏幕高
     * @param options       Bitmap 设置
     * @return 合适的缩放比例
     */
    public static int getFitInSampleSize(int requireWidth, int requireHeight, BitmapFactory.Options options) {
        int inSampleSize = 1;
        if (options.outWidth > requireWidth || options.outHeight > requireHeight) {
            int widthRatio = Math.round((float) options.outWidth / (float) requireWidth);
            int heightRatio = Math.round((float) options.outHeight / (float) requireHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 从文件路径加载 Bitmap
     *
     * @param filePath      文件路径
     * @param requireWidth  宽
     * @param requireHeight 高
     * @return Bitmap
     */
    public static Bitmap getFitSampleBitmap(String filePath, int requireWidth, int requireHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = getFitInSampleSize(requireWidth, requireHeight, options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 从 Resources 加载 Bitmap
     *
     * @param resources     文件路径
     * @param requireWidth  宽
     * @param requireHeight 高
     * @return Bitmap
     */
    public static Bitmap getFitSampleBitmap(Resources resources, int resourceId, int requireWidth, int requireHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resourceId, options);
        options.inSampleSize = getFitInSampleSize(requireWidth, requireHeight, options);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resourceId, options);
    }

    /**
     * 从 字节流 加载 Bitmap
     *
     * @param inputStream   inputStream
     * @param requireWidth  宽
     * @param requireHeight 高
     * @return Bitmap
     * @throws Exception
     */
    public static Bitmap getFitSampleBitmap(InputStream inputStream, int requireWidth, int requireHeight) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        byte[] bytes = readStream(inputStream);
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = getFitInSampleSize(requireWidth, requireHeight, options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }


    /**
     * 从字节流中读取二进制数组
     *
     * @param inputStream inputStream
     * @return 二进制数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inputStream.close();
        return outStream.toByteArray();
    }


}
