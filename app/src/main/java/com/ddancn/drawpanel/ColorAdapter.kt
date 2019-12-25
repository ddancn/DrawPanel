package com.ddancn.drawpanel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author ddan.zhuang
 * @date 2019/12/24
 *
 */
class ColorAdapter(
    private val colorList: List<Int>,
    private val onChosen: (color: Int, position: Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorView: CircleColorView = itemView.findViewById(R.id.color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = colorList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = colorList[position]
        holder.colorView.setColor(color)
        holder.colorView.setChosen(position == 0)
        holder.colorView.setOnClickListener { onChosen(colorList[position], position) }
    }

}