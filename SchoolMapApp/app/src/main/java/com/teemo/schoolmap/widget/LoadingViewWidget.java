package com.teemo.schoolmap.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.view.LoadingView;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/28 20:53
 * @description 正在加载 view 的布局
 */
public class LoadingViewWidget extends LinearLayout {

    private LoadingView loadingView;
    private ImageView ivShadow;
    private TextView tvLoading;
    private int fallDistance;

    public LoadingViewWidget(Context context) {
        this(context, null);
    }

    public LoadingViewWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingViewWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoadingViewWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_loading_view, null);
        loadingView = (LoadingView) view.findViewById(R.id.lv_loading);
        ivShadow = (ImageView) view.findViewById(R.id.iv_shadow);
        tvLoading = (TextView) view.findViewById(R.id.tv_loading);
        addView(view);
        fallDistance = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getContext().getResources().getDisplayMetrics());

        fall();
    }

    private void fall() {
        ObjectAnimator lvTranslationAnimator = ObjectAnimator.ofFloat(loadingView, "translationY", fallDistance, 0);
        ObjectAnimator lvRotateAnimator = ObjectAnimator.ofFloat(loadingView, "rotation", 0, loadingView.getCurrentShape() == LoadingView.Shape.TRIANGLE ? 360 :-360);
        ObjectAnimator tvColorAnimator = ObjectAnimator.ofArgb(tvLoading, "textColor", Color.parseColor("#757575"), Color.parseColor("#F58300"));
        ObjectAnimator ivScaleAnimator = ObjectAnimator.ofFloat(ivShadow, "scaleX", 1.0f, 0.3f);
        lvTranslationAnimator.setInterpolator(new DecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                up();
            }
        });
        animatorSet.play(lvTranslationAnimator).with(ivScaleAnimator).with(tvColorAnimator).with(lvRotateAnimator);
        animatorSet.start();
    }

    private void up() {
        ObjectAnimator lvTranslationAnimator = ObjectAnimator.ofFloat(loadingView, "translationY", 0, fallDistance);
        ObjectAnimator lvRotateAnimator = ObjectAnimator.ofFloat(loadingView, "rotation", 0, loadingView.getCurrentShape() == LoadingView.Shape.TRIANGLE ? -360 :360);
        ObjectAnimator tvColorAnimator = ObjectAnimator.ofArgb(tvLoading, "textColor", Color.parseColor("#F58300"), Color.parseColor("#757575"));
        ObjectAnimator ivScaleAnimator = ObjectAnimator.ofFloat(ivShadow, "scaleX", 0.3f, 1.0f);
        lvTranslationAnimator.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loadingView.changeShape(loadingView.getCurrentShape());
                fall();
            }
        });

        animatorSet.play(lvTranslationAnimator).with(ivScaleAnimator).with(tvColorAnimator).with(lvRotateAnimator);
        animatorSet.start();
    }
}
