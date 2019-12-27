package com.ddancn.drawpanel

import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_revert.setOnClickListener {
            draw_panel.revert()
        }
        btn_clear.setOnClickListener {
            draw_panel.clear()
        }
        btn_eraser.setOnClickListener {
            draw_panel.setMode(DrawPanel.EditMode.ERASER)
        }
        btn_magnifier.setOnClickListener {
            draw_panel.setMode(DrawPanel.EditMode.MAGNIFIER)
        }

        // 粗细选择
        seek_bar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                draw_panel.width = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // 颜色列表
        rv_color.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val colorList =
            listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE, Color.GRAY, Color.BLACK)
        rv_color.adapter = ColorAdapter(colorList) { color, position ->
            draw_panel.setColor(color)
            rv_color.children.forEachIndexed { index, view ->
                (view as CircleColorView).setChosen(index == position)
            }
        }
    }
}
