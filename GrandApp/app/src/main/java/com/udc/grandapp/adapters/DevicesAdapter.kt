package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.fragments.Devices
import com.udc.grandapp.fragments.EditDevices
import com.udc.grandapp.items.CustomerDevice
import kotlinx.android.synthetic.main.custom_dispositivo.view.*
import kotlinx.android.synthetic.main.custom_nuevodispositivo.view.*

class DevicesAdapter(context : Context, val items: List<CustomerDevice>, fragmentManager : FragmentManager, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<CustomerDevice> = items
    private var mFragmentManager : FragmentManager = fragmentManager


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_dispositivo, parent, false)
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
            nombreProducto_cd.text = item.nombre
            bombilla_cd.setBackgroundColor(context.resources.getColor(R.color.green))
            consulta.setOnClickListener {
                var fr = fragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentDevices, EditDevices())
                fr?.commit()
            }
            encender.setOnClickListener {
                Toast.makeText(context, "Encender/Apagar", Toast.LENGTH_LONG).show()
            }
        }
    }
}