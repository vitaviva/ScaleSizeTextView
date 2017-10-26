package com.vitaviva.scalesizetextview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import de.greenrobot.event.EventBus;

public class ScaleSizeTextView extends TextView {

    private float originTextSize;
    private ScaleTextSizeUtil.ScaleInfo scaleInfo;

    public ScaleSizeTextView(Context context) {
        this(context, null, 0);
    }

    public ScaleSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ScaleSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScaleSizeTextView(Context context, AttributeSet attrs, int defStyleAttr,
                             int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        originTextSize = getTextSize();
        /**
         * 不可去掉readScaleFromCache(),computeAndSetTextSize()两个函数的调用，
         * 否则父view的高度尺寸可能会出问题
         */
        //        readScaleFromCache();
        //        computeAndSetTextSize();
        if (!isInEditMode() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (getMaxLines() == 1) {
                setSingleLine(true);
            }
        }
    }

    @Override
    public void setTextSize(float size) {
        originTextSize = size;
        /**
         * 不可去掉readScaleFromCache(),computeAndSetTextSize()两个函数的调用，
         * 否则父view的高度尺寸可能会出问题
         */
        readScaleFromCache();
        computeAndSetTextSize();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (getMaxLines() == 1) {
                setSingleLine(true);
            }
        }
    }

    private void readScaleFromCache() {
        scaleInfo = isInEditMode() ? ScaleTextSizeUtil.ScaleInfo.NORMAL
                : ScaleTextSizeUtil.getInstance(getContext()).getCurScale();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        /**
         * 不可去掉readScaleFromCache(),computeAndSetTextSize()两个函数的调用，
         * 否则list中view复用时可能出问题
         */
        readScaleFromCache();
        computeAndSetTextSize();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    void setScale(ScaleTextSizeUtil.ScaleInfo scale) {
        if (scaleInfo != scale) {
            scaleInfo = scale;
            computeAndSetTextSize();
        }
    }

    private void computeAndSetTextSize() {
        float scaledTextSize = scaleInfo.compute(getContext(), originTextSize);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
    }

    public void onEventMainThread(TextSizeScaleChangedAction textSizeScaleChangedAction) {
        setScale(textSizeScaleChangedAction.getScaleInfo());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        readScaleFromCache();
        computeAndSetTextSize();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
