package com.ddancn.drawpanel

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
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

        // 颜色选择
        color_group.setOnColorChosenListener { color -> draw_panel.setColor(color) }
    }
}
