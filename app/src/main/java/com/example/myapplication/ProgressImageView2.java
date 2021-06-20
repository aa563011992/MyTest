package com.example.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by wyl on 2019/6/28.
 * 带阴影带进度的
 */

public class ProgressImageView2 extends ImageView {
    private int progressColor;
    private int progressBgColor;
    private int progress;
    private float progressWidth;
    private int maxProgress;
    private Paint progressBgPaint;
    private Paint progressPaint;


    public ProgressImageView2(Context context) {
        super(context);
        init(context, null);
    }

    public ProgressImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {


            try {
                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressImageView2);
                progressColor = typedArray.getColor(R.styleable.ProgressImageView2_pbProgressColor, 0xff0dad51);
                progressBgColor = typedArray.getColor(R.styleable.ProgressImageView2_pbProgressBgColor, 0xffE7E7E7);
                progress = typedArray.getInt(R.styleable.ProgressImageView2_pbProgress, 30);
                maxProgress = typedArray.getInt(R.styleable.ProgressImageView2_pbMaxProgress, 100);
                progressWidth = typedArray.getDimension(R.styleable.ProgressImageView2_pbProgressWidth, 2);
                typedArray.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }


            progressBgPaint = new Paint();
            progressBgPaint.setAntiAlias(true);
            progressBgPaint.setStyle(Paint.Style.STROKE);
            progressBgPaint.setStrokeWidth(progressWidth);
            progressBgPaint.setColor(progressBgColor);
            setLayerType(View.LAYER_TYPE_SOFTWARE, progressBgPaint);
            progressBgPaint.setMaskFilter(new BlurMaskFilter(progressWidth, BlurMaskFilter.Blur.SOLID));

            progressPaint = new Paint();
            progressPaint.setAntiAlias(true);
            progressPaint.setStyle(Paint.Style.STROKE);
            progressPaint.setStrokeWidth(progressWidth);
            progressPaint.setColor(progressColor);
            setLayerType(View.LAYER_TYPE_SOFTWARE, progressPaint);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgressBgColor(int progressBgColor) {
        this.progressBgColor = progressBgColor;
        progressBgPaint.setColor(progressBgColor);
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        progressPaint.setColor(progressColor);
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    public void setProgressWidth(float pwPx) {
        progressBgPaint.setStrokeWidth(pwPx);
        progressPaint.setStrokeWidth(pwPx);
        this.progressWidth = pwPx;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawInternal(canvas);
        super.onDraw(canvas);
    }

    private void drawInternal(Canvas canvas) {
        try {

//            float x = (width - height / 2) / 2;
//            float y = height / 4;
//
//            RectF oval = new RectF(x, y, width - x, height - y);

            float x = getWidth() / 2;
            float y = getHeight() / 2;

            RectF oval = new RectF();
            float radius = Math.min(getWidth(), getHeight()) / 2;
            oval.left = x - radius + progressWidth;
            oval.top = y - radius + progressWidth;
            oval.right = x + radius - progressWidth;
            oval.bottom = y + radius - progressWidth;

            canvas.drawArc(oval, 0, 360, false, progressBgPaint);

            float sweepAngle = (float) progress / (float) maxProgress * 360f;//每个扇形的角度

            canvas.drawArc(oval, 270, sweepAngle, false, progressPaint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
