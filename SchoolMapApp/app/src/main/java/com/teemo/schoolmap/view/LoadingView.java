package com.teemo.schoolmap.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/28 20:13
 * @description 正在加载 view
 */
public class LoadingView extends View {

    private Paint paint;
    private Shape currentShape = Shape.ROUND;
    private Path path;

    public enum Shape {
        ROUND, SQUARE, TRIANGLE, STAR
    }

    /**
     * 一般在代码中创建引用使用这个构造函数
     *
     * @param context context
     */
    public LoadingView(Context context) {
        this(context, null);
    }

    /**
     * 在XML文件中使用View会调用该构造函数
     *
     * @param context context
     * @param attrs   attrs
     */
    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        path = new Path();
    }

    /**
     * 绘制
     *
     * @param canvas canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (currentShape) {
            case SQUARE:
                paint.setColor(Color.parseColor("#FF9999"));
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
                break;
            case ROUND:
                int circleRadius = Math.min(getWidth(), getHeight()) / 2;
                paint.setColor(Color.parseColor("#99FFCC"));
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, circleRadius, paint);
                break;
            case TRIANGLE:
                paint.setColor(Color.parseColor("#99CCFF"));
                path.reset();
                path.moveTo(getWidth() / 2, 0);
                path.lineTo(0, getHeight());
                path.lineTo(getWidth(), getHeight());
                path.close();
                canvas.drawPath(path, paint);
                break;
            case STAR:
                paint.setColor(Color.parseColor("#FFBB00"));
                path.reset();
                path.moveTo(0, (float) (0.4 * getHeight()));
                path.lineTo(getWidth(), (float) (0.4 * getHeight()));
                path.lineTo((float) (0.2 * getWidth()), getHeight());
                path.lineTo((float) (0.5 * getWidth()), 0);
                path.lineTo((float) (0.8 * getWidth()), getHeight());
                path.close();
                canvas.drawPath(path, paint);
                break;
            default:
                break;
        }
    }

    /**
     * 改变当前形状
     *
     * @param shape shape
     */
    public void changeShape(Shape shape) {
        switch (shape) {
            case ROUND:
                currentShape = Shape.STAR;
                break;
            case STAR:
                currentShape = Shape.TRIANGLE;
                break;
            case TRIANGLE:
                currentShape = Shape.SQUARE;
                break;
            case SQUARE:
                currentShape = Shape.ROUND;
                break;
            default:
                break;
        }
        // 重绘
        invalidate();
    }

    /**
     * @return 获取当前形状
     */
    public Shape getCurrentShape() {
        return currentShape;
    }
}
