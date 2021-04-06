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
import com.udc.grandapp.items.RoutinesDevice
import kotlinx.android.synthetic.main.custom_rutina_dispositivo.view.*


class EditDevicesAdapter(context : Context, val items: List<RoutinesDevice>, fragmentManager : FragmentManager, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<EditDevicesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<RoutinesDevice> = items
    private var mFragmentManager : FragmentManager = fragmentManager


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_rutina_dispositivo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mFragmentManager)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RoutinesDevice, listener: (ClipData.Item) -> Unit, fragmentManager: FragmentManager) = with(itemView) {
            nombreProducto.text = item.nombre
            descripcion.text = item.descripcion
            ver.setOnClickListener {
                var fr = fragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentEditDevices, NewRoutine())
                fr?.commit()
            }
            borrar.setOnClickListener {
                Toast.makeText(context, "Borrar rutina", Toast.LENGTH_LONG).show()
            }
        }
    }
}