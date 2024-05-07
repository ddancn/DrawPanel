package com.ddancn.drawpanel

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author ddan.zhuang
 * @date 2020/1/17
 * 一个颜色选择器，包含很多个CircleColor
 */
class CircleColorGroup(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
    private var onChosen: ((color: Int) -> Unit)? = null
    private var colorList =
        mutableListOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.WHITE,
            Color.GRAY,
            Color.BLACK
        )

    private var rvColor: RecyclerView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_color_group, this)
        rvColor = findViewById(R.id.rv_color)
        rvColor.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        rvColor.adapter = ColorAdapter(colorList) { color, position ->
            onChosen?.invoke(color)
            setChosen(position)
        }
    }

    /**
     * 设置颜色选中时的监听事件
     */
    fun setOnColorChosenListener(onChosen: (color: Int) -> Unit) {
        this.onChosen = onChosen
    }

    /**
     * 设置颜色列表
     */
    fun setColorList(list: List<Int>) {
        colorList.clear()
        colorList.addAll(list)
    }

    /**
     * 设置选中项
     */
    fun setChosen(position: Int) {
        if (position in 0..colorList.lastIndex) {
            rvColor.children.forEachIndexed { index, view ->
                (view as CircleColor).setChosen(index == position)
            }
        }
    }
}