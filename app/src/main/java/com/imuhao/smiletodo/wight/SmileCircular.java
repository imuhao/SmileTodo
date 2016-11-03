package com.imuhao.smiletodo.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.utils.DiffUtils;


/**
 * Created by smile on 16-10-10.
 * 圆形太阳
 */

public class SmileCircular extends View {

    private float mWidth;
    private float mStartAngle;
    private Paint mPaint;
    private float mMaxRadius = DiffUtils.dp2px(getContext(), 3);
    private int mColor;
    private RotateAnimation mRotateAnimation;

    public SmileCircular(Context context) {
        this(context, null, 0);
    }

    public SmileCircular(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmileCircular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mColor = getResources().getColor(R.color.backgroupColor);

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mRotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setRepeatCount(-1);    //不停顿
        mRotateAnimation.setFillAfter(true);//停在最后
        mRotateAnimation.setInterpolator(new LinearInterpolator());//设置差值器
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() > getMeasuredWidth()) {
            mWidth = getMeasuredWidth();
        } else {
            mWidth = getMeasuredHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画边缘小圆
        for (int i = 0; i < 9; i++) {
            float x2 = (float) ((mWidth / 2.f - mMaxRadius) * Math.cos(mStartAngle + 45f * i * Math.PI / 180f));
            float y2 = (float) ((mWidth / 2.f - mMaxRadius) * Math.sin(mStartAngle + 45f * i * Math.PI / 180f));
            canvas.drawCircle(mWidth / 2 - x2, mWidth / 2 - y2, mMaxRadius, mPaint);
        }
        //画中心圆
        canvas.drawCircle(mWidth / 2f, mWidth / 2f, mWidth / 2 - mMaxRadius * 6, mPaint);

    }

    /**
     * 开始动画
     */
    public void startAnim() {
        stopAnim();
        mRotateAnimation.setDuration(3500);
        startAnimation(mRotateAnimation);

    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    /**
     * 暂停动画
     */
    public void stopAnim() {
        clearAnimation();
    }
}
