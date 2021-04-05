package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.fragments.Devices
import com.udc.grandapp.items.CustomerDevice
import kotlinx.android.synthetic.main.custom_nuevodispositivo.view.*

class NewDevicesAdapter(context : Context, val items: List<CustomerDevice>, fragmentManager : FragmentManager, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<NewDevicesAdapter.ViewHolder>(){
    private var mContext : Context = context
    private var mItems : List<CustomerDevice> = items
    private var mFragmentManager : FragmentManager = fragmentManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_nuevodispositivo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mFragmentManager)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerDevice, listener: (ClipData.Item) -> Unit, fragmentManager: FragmentManager) = with(itemView) {
            nombreProducto.text = item.nombre
            enlazar.setOnClickListener {
                var fr = fragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentSearchDevices, Devices())
                fr?.commit()
            }
            rechazar.setOnClickListener {
                var fr = fragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentSearchDevices, Devices())
                fr?.commit()
            }
        }
    }
}