package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.fragments.NewRoutine
import com.udc.grandapp.fragments.UpdateRoutine
import com.udc.grandapp.items.CustomerDeviceSummary
import kotlinx.android.synthetic.main.custom_lista.view.*

class DeviceNewRoutinesAdapter(context : Context, val items: List<CustomerDeviceSummary>, fragmentManager : FragmentManager, val listener: (ClipData.Item) -> Unit)  : RecyclerView.Adapter<DeviceNewRoutinesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<CustomerDeviceSummary> = items
    private var mFragmentManager : FragmentManager = fragmentManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_lista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mFragmentManager)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerDeviceSummary, listener: (ClipData.Item) -> Unit, fragmentManager: FragmentManager) = with(itemView) {
            nombreDispositivo.text = item.name
            descripcion.text = item.description

            nombreDispositivo.setOnClickListener {
                var fr = fragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentRoutines, NewRoutine())
                fr?.commit()
            }
        }
    }

}