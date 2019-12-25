package com.ddancn.drawpanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.min


/**
 * @author ddan.zhuang
 * @date 2019/12/24
 *
 */
class CircleColorView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint: Paint

    private var width = 0f
    private var height = 0f
    private var radius = 0f

    private var isChosen = false

    init {
        // 从xml中获取属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.CircleColorView)
        isChosen = array.getBoolean(R.styleable.CircleColorView_isChosen, false)
        paint.color = array.getColor(R.styleable.CircleColorView_color, 0xff0000)
        array.recycle()
        // 设置选中时边框的paint
        strokePaint = Paint(paint)
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = 3f
        // 如果是白色或黄色，边框设成灰色
        if (paint.color == Color.WHITE || paint.color == Color.YELLOW) {
            strokePaint.color = Color.LTGRAY
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获得要绘制的圆的位置和半径
        width = measuredWidth.toFloat()
        height = measuredHeight.toFloat()
        radius = min(width, height) / 2
    }

    override fun onDraw(canvas: Canvas?) {
        // 画圆
        canvas?.drawCircle(width / 2, height / 2, radius - 8, paint)
        // 如果是白色或黄色，给一个灰色的描边
        if (paint.color == Color.WHITE || paint.color == Color.YELLOW) {
            canvas?.drawCircle(width / 2, height / 2, radius - 8, strokePaint)
        }
        // 如果被选中，画边框
        if (isChosen) {
            canvas?.drawCircle(width / 2, height / 2, radius - 2, strokePaint)
        }
    }

    /**
     * 设置圆和边框颜色
     */
    fun setColor(@ColorInt c: Int) {
        paint.color = c
        strokePaint.color = c
        // 如果是白色，边框设成灰色
        if (paint.color == Color.WHITE || paint.color == Color.YELLOW) {
            strokePaint.color = Color.LTGRAY
        }
        invalidate()
    }

    /**
     * 设置选中状态
     */
    fun setChosen(chosen: Boolean) {
        isChosen = chosen
        invalidate()
    }
}