package com.ddancn.drawpanel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntRange

/**
 * @author ddan.zhuang
 * @date 2019/12/24
 *
 */
const val TAG = "DrawPanel"

class DrawPanel(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var paint = Paint()
    private var path = Path()
    private val pathList = ArrayList<Path>()
    private val paintList = ArrayList<Paint>()
    private var isDrawing = false

    init {
        paint = Paint()
        paint.color = Color.RED
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        // 画出已保存的路径
        pathList.forEachIndexed { index, path -> canvas?.drawPath(path, paintList[index]) }
        // 如果手指正在绘制路径，也画出当前路径
        if (isDrawing) {
            canvas?.drawPath(path, paint)
        }
    }

    /**
     * 处理触摸事件
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                path = Path()
                path.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                pathList.add(path)
                paintList.add(paint)
                paint = Paint(paint)
            }
        }
        return true
    }

    /**
     * 撤销上一个操作
     */
    fun revert() {
        if (pathList.isNotEmpty()) {
            val index = pathList.indexOf(path)
            pathList.remove(path)
            // 同时删除上一个保存的画笔
            paintList.removeAt(index)
            // 替换当前路径为集合中最新的
            path = if (pathList.isEmpty()) Path() else pathList[pathList.lastIndex]
            invalidate()
        }
    }

    /**
     * 清空路径和画笔
     */
    fun clear() {
        pathList.clear()
        paintList.clear()
        invalidate()
    }

    /**
     * 设置画笔粗细
     */
    fun setWidth(@IntRange(from = 0, to = 9) progress: Int) {
        paint.strokeWidth = (2 + progress * 3).toFloat()
    }

    /**
     * 设置画笔颜色
     */
    fun setColor(@ColorInt color: Int) {
        paint.color = color
    }
}