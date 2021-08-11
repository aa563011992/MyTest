package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.Window;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Method;

public class ComicReaderRecyclerView extends RecyclerView implements OnTouchListener {
    private static final String TAG = "ComicReaderRecyclerView";
    public OnMenuListener onMenuListener;
    public OnDoubleTapListener onDoubleTapListener = new OnDoubleTapListener() {
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (onMenuListener != null && onMenuListener.w()) {
                onMenuListener.I(((Activity) context).getWindow());
            }
            if (u > 0 || pointCount != 0) {
                return false;
            }
            motionEvent.getX();
            float y = motionEvent.getY();
            if (y > (((float) getHeight()) * 1.0f) / 3.0f && y < ((((float) getHeight()) * 1.0f) * 2.0f) / 3.0f) {
                u = System.currentTimeMillis();
                if (v == 1.0f) {
                    matrix1.setScale(2.0f, 2.0f, motionEvent.getX(), (b * 2.0f) + motionEvent.getY());
                } else {
                    matrix.getValues(l);
                    y = (l[5] + motionEvent.getY()) / l[4];
                    matrix1.reset();
                    matrix1.getValues(m);
                    m[5] = y;
                    matrix1.setValues(m);
                }
                u(matrix1, m);
                invalidate();
            }
            return false;
        }

        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            try {
                motionEvent.getX();
                float y = motionEvent.getY();
                if (y <= getHeight() / 3.0f || y >=  getHeight() * 2.0f / 3.0f) {
                    if (y < getHeight() / 3.0f) {
                        if (g != null) {
                            g.o();
                        }
                    } else if (g != null) {
                        g.i();
                    }
                    return false;
                } else if (scrolling) {
                    scrolling = false;
                    return false;
                } else {
                    if (g != null) {
                        g.l();
                    }
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    };
    private Adapter adapter;
    public OnScrollListener D = new OnScrollListener() {
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            scrollState = newState;
            if (onRollScrollListener != null) {
                onRollScrollListener.m(recyclerView, newState);
                d = System.currentTimeMillis();
            }
            if (newState == 0) {
//                MemoryManager.c().d();
            }
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            if (onRollScrollListener != null) {
                onRollScrollListener.t(recyclerView);
            }
            int findFirstVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            if (findFirstVisibleItemPosition != z) {
//                MemoryManager.c().d();
                z = findFirstVisibleItemPosition;
            }
        }
    };
    //    public MemoryCycle E = new MemoryCycle() {
//        public void U0(float f) {
//            if (f == 3000.0f) {
//                A();
//            }
//        }
//    };
    public float b = 0.0f;
    public GestureDetector gestureDetector = new GestureDetector(new OnGestureListener() {
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public void onLongPress(MotionEvent motionEvent) {
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public void onShowPress(MotionEvent motionEvent) {
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
    });
    public long d;
    public Context context;
    public float f = -1.0f;
    public OnSingleClickListener g;
    public Matrix matrix;
    public Matrix matrix1;
    public Matrix matrix2;
    public Matrix matrix3;
    public float[] l = new float[9];
    public float[] m = new float[9];
    public float[] n = new float[9];
    public int pointCount = 0;
    public PointF lastTouchPoint;
    public PointF q;
    public PointF r;
    public float s;
    public float t;
    public long u = -1;
    public float v = 1.0f;
    public int scrollState;
    public OnRollScrollListener onRollScrollListener;
    public boolean scrolling = false;
    public int z = 0;

    public interface OnMenuListener {
        void I(Window window);

        boolean w();
    }

    public interface OnRollScrollListener {
        void onScrollToTop();

        void onScrollToBottom();

        void m(RecyclerView recyclerView, int i);

        void t(RecyclerView recyclerView);
    }

    public interface OnSingleClickListener {
        void i();

        void l();

        void o();
    }

    public ComicReaderRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        setOnTouchListener(this);
        addOnScrollListener(this.D);
//        MemoryManager.c().b(this.E);
        post(new Runnable() {
            public void run() {
                w();
            }
        });
        setItemAnimator(null);
    }

    public void A() {
        try {
            getRecycledViewPool().clear();
            Object g = ReflectUtil.g(this, "mRecycler", getClass());
            if (g != null) {
                Method c = ReflectUtil.c(g.getClass(), "clear");
                c.setAccessible(true);
                ReflectUtil.h(c, g);
                c = ReflectUtil.c(g.getClass(), "clearScrap");
                c.setAccessible(true);
                ReflectUtil.h(c, g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void dispatchScrollToBottomEvent(float f) {
        if (f < -100.0f && isBottom()) {
            if (onRollScrollListener != null) {
                onRollScrollListener.onScrollToBottom();
            }
        }
    }

    public final void dispatchScrollToTopEvent(float f) {
        if (f > 100.0f && isTop()) {
            if (onRollScrollListener != null) {
                onRollScrollListener.onScrollToTop();
            }
        }
    }

    public void dispatchDraw(Canvas canvas) {
        ComicReaderRecyclerView.super.dispatchDraw(canvas);
    }

    public void onDraw(Canvas canvas) {
        ComicReaderRecyclerView.super.onDraw(canvas);
        float f;
        if (this.u > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.u < 200) {
                this.matrix.getValues(this.l);
                this.matrix1.getValues(this.m);
                f = ((float) (currentTimeMillis - this.u)) / 200.0f;
                if (f < 0.0f) {
                    f = 0.0f;
                } else if (f > 1.0f) {
                    f = 1.0f;
                }
                for (int i = 0; i < 9; i++) {
                    float[] fArr = this.n;
                    float[] fArr2 = this.l;
                    fArr[i] = fArr2[i] + ((this.m[i] - fArr2[i]) * f);
                }
                this.matrix2.setValues(this.n);
                canvas.setMatrix(this.matrix2);
                invalidate();
                return;
            }
            this.u = -1;
            canvas.setMatrix(this.matrix1);
            this.matrix.set(this.matrix1);
            this.matrix.getValues(this.l);
            this.v = this.l[4];
            invalidate();
            return;
        }
        canvas.setMatrix(this.matrix);
        float[] fArr3 = this.l;
        f = fArr3[4];
        this.v = f;
        this.b = (-fArr3[5]) / f;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.u > 0 || this.matrix3 == null || this.matrix == null) {
            this.pointCount = 0;
            return true;
        }
        if (this.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            this.gestureDetector.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction() & 0xff;
        if (action != MotionEvent.ACTION_DOWN) {
            float v;
            if (action != MotionEvent.ACTION_UP) {
                if (action == MotionEvent.ACTION_MOVE) {
                    if (this.scrollState != 2) {
                        this.scrolling = false;
                    }
                    if (Math.abs(motionEvent.getY(0) - this.lastTouchPoint.y) > ((float) ViewConfiguration.get(getContext()).getScaledTouchSlop())) {
                        if (onMenuListener != null && onMenuListener.w()) {
                            onMenuListener.I(((Activity) this.context).getWindow());
                        }
                    }
                    if (this.pointCount == 1) {
                        this.matrix.set(this.matrix3);
                        try {
                            float dx = motionEvent.getX() - this.lastTouchPoint.x;
                            float dy = motionEvent.getY() - this.lastTouchPoint.y;
                            Log.i(TAG, "dx=" + dx + ",dy=" + dy);
                            this.matrix.postTranslate(dx, dy);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        t(this.matrix, this.l);
                        invalidate();
                    }
                    if (this.pointCount == 2) {
                        v = v(motionEvent);
                        if (v > 10.0f) {
                            v /= this.t;
                            float f = this.v;
                            if ((f > 0.5f || v > this.s) && (f < 3.5f || v < this.s)) {
                                this.matrix.set(this.matrix3);
                                Matrix matrix = this.matrix;
                                PointF pointF = this.r;
                                matrix.postScale(v, v, pointF.x, pointF.y);
                                u(this.matrix, this.l);
                                if (this.f < 0.0f && this.l[4] < 1.0f) {
                                    this.f = this.b;
                                }
                                this.s = v;
                                invalidate();
                            }
                        }
                    }
                } else if (action != MotionEvent.ACTION_CANCEL) {
                    if (action == MotionEvent.ACTION_POINTER_DOWN) {
                        v = v(motionEvent);
                        this.t = v;
                        if (v > 10.0f) {
                            this.matrix3.set(this.matrix);
                            this.r = z(motionEvent);
                            this.pointCount = 2;
                        }
                    }
                }
            }
            if (this.pointCount == 2) {
                v = this.v;
                if (v < 1.0f) {
                    this.u = System.currentTimeMillis();
                    this.matrix1.reset();
                    this.matrix1.getValues(this.m);
                    float[] fArr = this.m;
                    fArr[5] = -this.f;
                    this.matrix1.setValues(fArr);
                    u(this.matrix1, this.m);
                    this.f = -1.0f;
                    invalidate();
                } else if (v > 2.0f) {
                    this.u = System.currentTimeMillis();
                    this.matrix1.set(this.matrix);
                    Matrix matrix2 = this.matrix1;
                    float f2 = this.v;
                    float f3 = 2.0f / f2;
                    float f4 = 2.0f / f2;
                    PointF pointF2 = this.r;
                    matrix2.postScale(f3, f4, pointF2.x, pointF2.y);
                    this.f = -1.0f;
                    invalidate();
                }
            } else {
                this.pointCount = 0;
            }
            v = motionEvent.getY() - this.lastTouchPoint.y;
            dispatchScrollToTopEvent(v);
            dispatchScrollToBottomEvent(v);
        } else {
            this.pointCount = 1;
            this.matrix3.set(this.matrix);
            try {
                this.lastTouchPoint.set(motionEvent.getX(), motionEvent.getY());
                this.q.set(motionEvent.getX(), motionEvent.getY());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                this.scrolling = true;
            }
        }
        try {
            return super.onTouchEvent(motionEvent);
        } catch (Exception unused) {
            return false;
        }
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
    }

    public void setOnComicScrollListener(OnRollScrollListener onRollScrollListener) {
        this.onRollScrollListener = onRollScrollListener;
    }

    public void setOnMenuListener(OnMenuListener onMenuListener) {
        this.onMenuListener = onMenuListener;
    }

    public void setSingleClickListener(OnSingleClickListener onSingleClickListener) {
        this.g = onSingleClickListener;
    }

    public final void t(Matrix matrix, float[] fArr) {
        matrix.getValues(fArr);
        if (fArr[2] > 0.0f) {
            fArr[2] = 0.0f;
        }
        if (fArr[2] < ((float) (-getWidth())) * (fArr[0] - 1.0f)) {
            fArr[2] = ((float) (-getWidth())) * (fArr[0] - 1.0f);
        }
        if (fArr[5] > 0.0f) {
            fArr[5] = 0.0f;
        }
        if (fArr[5] < (((float) (-getHeight())) * fArr[4]) + ((float) getHeight())) {
            fArr[5] = (((float) (-getHeight())) * fArr[4]) + ((float) getHeight());
        }
        matrix.setValues(fArr);
    }

    public final void u(Matrix matrix, float[] fArr) {
        matrix.getValues(fArr);
        if (fArr[5] > 0.0f) {
            fArr[5] = 0.0f;
        }
        if (fArr[5] < (((float) (-getHeight())) * fArr[4]) + ((float) getHeight())) {
            fArr[5] = (((float) (-getHeight())) * fArr[4]) + ((float) getHeight());
        }
        matrix.setValues(fArr);
    }

    public final float v(MotionEvent motionEvent) {
        try {
            float x = motionEvent.getX(0) - motionEvent.getX(1);
            float y = motionEvent.getY(0) - motionEvent.getY(1);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Math.sqrt((double) ((x * x) + (y * y))));
            stringBuilder.append("");
            return Float.parseFloat(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public final void w() {
        this.gestureDetector.setOnDoubleTapListener(this.onDoubleTapListener);
        Matrix matrix = new Matrix();
        this.matrix = matrix;
        matrix.setTranslate(0.0f, 0.0f);
        this.matrix.getValues(this.l);
        this.matrix1 = new Matrix();
        this.matrix2 = new Matrix();
        this.matrix3 = new Matrix();
        this.lastTouchPoint = new PointF();
        this.q = new PointF();
        this.r = new PointF();
        this.t = 1.0f;
    }

    public boolean isTop() {
        LayoutManager layoutManager = getLayoutManager();
        boolean z = false;
        if (layoutManager != null && (layoutManager instanceof LinearLayoutManager)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int findFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            int i = -1;
            if (findFirstCompletelyVisibleItemPosition == -1) {
                findFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int top = getTop() + getPaddingTop();
                View findViewByPosition = linearLayoutManager.findViewByPosition(findFirstCompletelyVisibleItemPosition);
                if (findViewByPosition != null) {
                    i = findViewByPosition.getTop();
                }
                if (findFirstCompletelyVisibleItemPosition == 0 && top == i) {
                    z = true;
                }
                return z;
            } else if (findFirstCompletelyVisibleItemPosition == 0) {
                z = true;
            }
        }
        return z;
    }

    public boolean isBottom() {
        LayoutManager layoutManager = getLayoutManager();
        boolean z = false;
        if (layoutManager != null && (layoutManager instanceof LinearLayoutManager)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int findLastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            int i = -1;
            if (findLastCompletelyVisibleItemPosition == -1) {
                findLastCompletelyVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int bottom = getBottom() - getPaddingBottom();
                View findViewByPosition = linearLayoutManager.findViewByPosition(findLastCompletelyVisibleItemPosition);
                if (findViewByPosition != null) {
                    i = findViewByPosition.getBottom();
                }
                if (findLastCompletelyVisibleItemPosition == this.adapter.getItemCount() - 1 && bottom == i) {
                    z = true;
                }
                return z;
            } else if (findLastCompletelyVisibleItemPosition == this.adapter.getItemCount() - 1) {
                z = true;
            }
        }
        return z;
    }

    public final PointF z(MotionEvent motionEvent) {
        PointF pointF = new PointF();
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
        return pointF;
    }
}