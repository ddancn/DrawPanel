package com.ddancn.drawpanel

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var drawPanel: DrawPanel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawPanel = findViewById(R.id.draw_panel);

        findViewById<Button>(R.id.btn_revert).setOnClickListener {
            drawPanel.revert()
        }
        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            drawPanel.clear()
        }
        findViewById<Button>(R.id.btn_eraser).setOnClickListener {
            drawPanel.setMode(DrawPanel.EditMode.ERASER)
        }
        findViewById<Button>(R.id.btn_magnifier).setOnClickListener {
            drawPanel.setMode(DrawPanel.EditMode.MAGNIFIER)
        }

        // 粗细选择
        findViewById<SeekBar>(R.id.seek_bar).setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                drawPanel.width = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // 颜色选择
        findViewById<CircleColorGroup>(R.id.color_group).setOnColorChosenListener { color ->
            drawPanel.setColor(color)
        }
    }
}
