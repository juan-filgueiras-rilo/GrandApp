package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.items.CustomerDevice
import kotlinx.android.synthetic.main.custom_dispositivo.view.*
import kotlinx.android.synthetic.main.custom_nuevodispositivo.view.*

class NewDevicesAdapter(context : Context, val items: List<CustomerDevice>, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<NewDevicesAdapter.ViewHolder>(){
    private var mContext : Context = context
    private var mItems : List<CustomerDevice> = items


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_nuevodispositivo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerDevice, listener: (ClipData.Item) -> Unit) = with(itemView) {
            nombreProducto.text = item.nombre
            enlazar.setOnClickListener {
                Toast.makeText(context, "Enlazar dispositivo", Toast.LENGTH_LONG)
                var fr = parentFragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentSearchDevices, Devices())
                fr?.commit()
            }
            rechazar.setOnClickListener {
                Toast.makeText(context, "Rechazar dispositivo", Toast.LENGTH_LONG)
                var fr = parentFragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentSearchDevices, Devices())
                fr?.commit()
            }
        }
    }
}