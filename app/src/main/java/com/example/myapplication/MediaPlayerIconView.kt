package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class MediaPlayerIconView : View {
    private var itemWidth = 0f
    private var itemHeight = 0f
    private var padding = 0f
    private var margin = 0f
    private val paint = Paint()
    private val TAG = "MediaPlayerIconView"
    private val speed = 1.5f
    private val heightArray = floatArrayOf(0f, 0f, 0f, 0f, 0f)
    private val speedArray = floatArrayOf(0f, 0f, 0f, 0f, 0f)
    var stop = false
        set(value) {
            field = value
            if (!value) {
                postInvalidateDelayed(10)
            }
        }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        try {
            paint.color = Color.parseColor("#ffffff") //设置颜色

            paint.strokeWidth = 1f //设置线宽

            paint.isAntiAlias = true //设置是否抗锯齿

            paint.style = Paint.Style.FILL //设置绘制风格
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        try {
            val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
            val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
            itemWidth = width / 6f
            padding = itemWidth
            margin = width / 12f
            itemHeight = height - padding * 2f

            heightArray[0] = itemHeight * 0.3f
            heightArray[1] = itemHeight
            heightArray[2] = itemHeight * 0.5f
            heightArray[3] = itemHeight * 0.75f
            heightArray[4] = itemHeight * 0.2f

            speedArray[0] = itemHeight * speed * 1.0f / 100f
            speedArray[1] = itemHeight * speed * 3.5f / 100f
            speedArray[2] = itemHeight * speed * 1.67f / 100f
            speedArray[3] = itemHeight * speed * 1.25f / 100f
            speedArray[4] = itemHeight * speed * 2.75f / 100f

            height1 = heightArray[0]
            height2 = heightArray[1]
            height3 = heightArray[2]

            height1p = heightArray[1]
            height2p = heightArray[2]
            height3p = heightArray[3]

            add1 = true
            add2 = false
            add1 = true

            p1 = speedArray[1]
            p2 = speedArray[2]
            p3 = speedArray[3]

        } catch (e: Exception) {
            e.printStackTrace();
        }

    }

    var height1 = 0f
    var height2 = 0f
    var height3 = 0f

    var height1p = 0f
    var height2p = 0f
    var height3p = 0f
    var add1 = false
    var add2 = false
    var add3 = false

    var p1 = 0f
    var p2 = 0f
    var p3 = 0f
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        try {

            if ((add1 && height1 >= height1p) || (!add1 && height1 <= height1p)) {
                height1 = height1p
                when (height1p) {
                    heightArray[0] -> {
                        height1p = heightArray[1]
                        p1 = speedArray[1]
                        add1 = heightArray[1] > heightArray[0]
                    }
                    heightArray[1] -> {
                        height1p = heightArray[2]
                        p1 = speedArray[2]
                        add1 = heightArray[2] > heightArray[1]
                    }
                    heightArray[2] -> {
                        height1p = heightArray[3]
                        p1 = speedArray[3]
                        add1 = heightArray[3] > heightArray[2]
                    }
                    heightArray[3] -> {
                        height1p = heightArray[4]
                        p1 = speedArray[4]
                        add1 = heightArray[4] > heightArray[3]
                    }
                    heightArray[4] -> {
                        height1p = heightArray[0]
                        p1 = speedArray[0]
                        add1 = heightArray[0] > heightArray[4]
                    }
                }
            }
            if ((add2 && height2 >= height2p) || (!add2 && height2 <= height2p)) {
                height2 = height2p
                when (height2p) {
                    heightArray[0] -> {
                        height2p = heightArray[1]
                        p2 = speedArray[1]
                        add2 = heightArray[1] > heightArray[0]
                    }
                    heightArray[1] -> {
                        height2p = heightArray[2]
                        p2 = speedArray[2]
                        add2 = heightArray[2] > heightArray[1]
                    }
                    heightArray[2] -> {
                        height2p = heightArray[3]
                        p2 = speedArray[3]
                        add2 = heightArray[3] > heightArray[2]
                    }
                    heightArray[3] -> {
                        height2p = heightArray[4]
                        p2 = speedArray[4]
                        add2 = heightArray[4] > heightArray[3]
                    }
                    heightArray[4] -> {
                        height2p = heightArray[0]
                        p2 = speedArray[0]
                        add2 = heightArray[0] > heightArray[4]
                    }
                }
            }
            if ((add3 && height3 >= height3p) || (!add3 && height3 <= height3p)) {
                height3 = height3p
                when (height3p) {
                    heightArray[0] -> {
                        height3p = heightArray[1]
                        p3 = speedArray[1]
                        add3 = heightArray[1] > heightArray[0]
                    }
                    heightArray[1] -> {
                        height3p = heightArray[2]
                        p3 = speedArray[2]
                        add3 = heightArray[2] > heightArray[1]
                    }
                    heightArray[2] -> {
                        height3p = heightArray[3]
                        p3 = speedArray[3]
                        add3 = heightArray[3] > heightArray[2]
                    }
                    heightArray[3] -> {
                        height3p = heightArray[4]
                        p3 = speedArray[4]
                        add3 = heightArray[4] > heightArray[3]
                    }
                    heightArray[4] -> {
                        height3p = heightArray[0]
                        p3 = speedArray[0]
                        add3 = heightArray[0] > heightArray[4]
                    }
                }
            }
            //draw first
            canvas.drawRect(
                padding,
                itemHeight + padding - height1,
                padding + itemWidth,
                itemHeight + padding, paint
            )
            //draw second
            canvas.drawRect(
                padding + itemWidth + margin,
                itemHeight + padding - height2,
                padding + itemWidth + margin + itemWidth,
                itemHeight + padding, paint
            )
            //draw third
            canvas.drawRect(
                padding + itemWidth + margin + itemWidth + margin,
                itemHeight + padding - height3,
                padding + itemWidth + margin + itemWidth + itemWidth + margin,
                itemHeight + padding, paint
            )


            if (add1) {
                height1 += p1
            } else {
                height1 -= p1
            }
            if (add2) {
                height2 += p2
            } else {
                height2 -= p2
            }
            if (add3) {
                height3 += p3
            } else {
                height3 -= p3
            }
//            Log.i(TAG, "111-$height1p,$height2p,$height3p|||$height1,$height2,$height3")

            if (!stop) {
                postInvalidateDelayed(10)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}