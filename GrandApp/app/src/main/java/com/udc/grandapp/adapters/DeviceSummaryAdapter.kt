package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.fragments.DeviceView
import com.udc.grandapp.fragments.UpdateDevice

import com.udc.grandapp.items.CustomerDeviceSummary
import com.udc.grandapp.model.DevicesModel
import kotlinx.android.synthetic.main.custom_lista.view.*

class DeviceSummaryAdapter(context : Context, val items: List<DevicesModel>, activity : FragmentActivity, val listener: (ClipData.Item) -> Unit)  : RecyclerView.Adapter<DeviceSummaryAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<DevicesModel> = items
    private var mActivity : FragmentActivity = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_lista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mActivity)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: DevicesModel, listener: (ClipData.Item) -> Unit, activity: FragmentActivity) = with(itemView) {
            nombreDispositivo.text = item.nombre
            descripcion.text = item.descripcion

            line1.setOnClickListener {
                val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                ft.replace(R.id.mainFragment, DeviceView())
                ft.addToBackStack("Summary")
                ft.commit()
            }
        }
    }

}