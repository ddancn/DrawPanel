package com.ddancn.drawpanel

import android.content.Context
import android.graphics.*
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
const val MAGNIFIER_SIZE = 200f
const val MAGNIFIER_FACTOR = 2.0f

class DrawPanel(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var paint = Paint()
    private val magnifierPaint = Paint()
    private var path = Path()
    private val pathList = ArrayList<Path>()
    private val paintList = ArrayList<Paint>()
    private var isDrawing = false
    private var preX = 0f
    private var preY = 0f
    private var mode = EditMode.PEN

    init {
        paint = Paint()
        paint.color = Color.RED
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f
        paint.isAntiAlias = true
        magnifierPaint.color = Color.LTGRAY
        magnifierPaint.strokeWidth = 3f
        magnifierPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        // 用保存的画笔，画出保存的路径
        pathList.forEachIndexed { index, path -> canvas?.drawPath(path, paintList[index]) }
        // 如果手指正在绘制路径，也画出当前路径；撤销/清空时不画
        if (isDrawing) {
            // 模式为放大镜时，画出放大区域
            if (mode == EditMode.MAGNIFIER) {
                canvas?.drawLine(MAGNIFIER_SIZE, 0f, MAGNIFIER_SIZE, MAGNIFIER_SIZE, magnifierPaint)
                canvas?.drawLine(0f, MAGNIFIER_SIZE, MAGNIFIER_SIZE, MAGNIFIER_SIZE, magnifierPaint)
            } else {
                canvas?.drawPath(path, paint)
            }
        }
    }

    private fun makeRect(): Rect {
        val halfSize = MAGNIFIER_SIZE / MAGNIFIER_FACTOR / 2
        return Rect(
            (preX - halfSize).toInt(),
            (preY - halfSize).toInt(),
            (preX + halfSize).toInt(),
            (preY + halfSize).toInt()
        )
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
                preX = event.x
                preY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                // 画贝塞尔曲线比较圆滑
                path.quadTo(preX, preY, event.x, event.y)
                preX = event.x
                preY = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                // 不刷新的话，放大镜框可能会还在
                invalidate()
                if (mode != EditMode.MAGNIFIER) {
                    pathList.add(path)
                    paintList.add(paint)
                    paint = Paint(paint)
                }
            }
        }
        return true
    }

    /**
     * 撤销上一个操作
     */
    fun revert() {
        if (pathList.isNotEmpty()) {
            pathList.removeAt(pathList.lastIndex)
            paintList.removeAt(paintList.lastIndex)
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
        setMode(EditMode.PEN)
        paint.color = color
    }

    /**
     * 切换模式
     */
    fun setMode(m: EditMode) {
        mode = m
        when (mode) {
            EditMode.PEN -> paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
            EditMode.ERASER -> paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            EditMode.MAGNIFIER -> paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
    }

    enum class EditMode {
        PEN,
        ERASER,
        MAGNIFIER
    }
}