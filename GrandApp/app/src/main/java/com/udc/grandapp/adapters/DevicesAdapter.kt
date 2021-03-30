package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.items.CustomerDevice
import kotlinx.android.synthetic.main.custom_dispositivo.view.*

class DevicesAdapter(context : Context, val items: List<CustomerDevice>, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<CustomerDevice> = items


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_dispositivo, parent, false)
        return DevicesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerDevice, listener: (ClipData.Item) -> Unit) = with(itemView) {
            nombreProducto_cd.text = item.nombre
            bombilla_cd.setBackgroundColor(context.resources.getColor(R.color.green))
        }
    }
}