package com.example.myapplication.util;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//创建一个类同时实现RecyclerView的OnItemTouchListener接口和ScaleGestureDetector的OnScaleGestureListener接口
public class RecyclerViewScaler implements RecyclerView.OnItemTouchListener, ScaleGestureDetector.OnScaleGestureListener {
    RecyclerView recView;
    ScaleGestureDetector scaleDetector;
    float scaleBase = 1;
    float scaleLevel = 1;
    float average = 1;
    float scaledRecord = 0;

    @Override //这个方法用于判断点触手势要不要被拦截，要拦截的话就会把手势送给下面的onTouchEvent去处理。
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        recView = rv;//获取外面传过来的要进行缩放的RecyclerView

        //初始化ScaleGestureDetector对象，让它绑定我们要进行缩放处理的rv，指定监听器用本身这个OnScaleGestureListener 
        scaleDetector = new ScaleGestureDetector(rv.getContext(), this);

        //缩放的手势起码要两根手指来完成吧？所以检测到RecyclerView上的触点大于1的时候，return true就是把这类手势转送给onTouchEvent方法去处理。
        if (e.getPointerCount() > 1) {
            return true;
        } else {//其他操作则不拦截，仍由RecyclerView自身去处理。
            return false;
        }
    }

    @Override  //这个是转发手势的接口，这次我们在这里要做的就简单了，
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        scaleDetector.onTouchEvent(e);//直接把手势交给ScaleGestureDetector去处理。
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        //这个方法我们这次不需要用到，但是OnItemTouchListener要求必须实现它，留空即可。
    }

    //以下几个方法都是OnScaleGestureListener接口要求必须实现的。寻寻觅觅找到这篇文章的同学，应该早就知道下面几个的实现方法，我也就不说得太详细了：
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        average = (average + detector.getCurrentSpan()) / 2;//采用取平均值的方法来防抖动
        scaleLevel = average / scaleBase;
        if (scaledRecord != 0) {
            scaleLevel *= scaledRecord;//在上次的基础上进行放大（否则会跳回1倍大小重新开始缩放）；
        }
        recView.setScaleX(scaleLevel);
        recView.setScaleY(scaleLevel);
        recView.invalidate();
//        recView.setVisibility(View.INVISIBLE);
//        recView.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        recView.setPivotX(detector.getFocusX());
        recView.setPivotY(detector.getFocusY());
        average = scaleBase = detector.getCurrentSpan();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        scaledRecord = scaleLevel;
        if (scaleLevel < 1) {
            scaleLevel = 1;
            scaledRecord = 1;
            recView.setScaleX(scaleLevel);
            recView.setScaleY(scaleLevel);
            recView.invalidate();
        }
    }
}

