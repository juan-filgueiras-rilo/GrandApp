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
import com.udc.grandapp.fragments.AddRoutine
import com.udc.grandapp.fragments.DeviceView
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.manager.listeners.IResponseFragmentManagerGeneric
import com.udc.grandapp.model.DevicesModel
import kotlinx.android.synthetic.main.custom_lista.view.*


class DeviceListAdapter(context : Context, val items: List<CustomerDevice>, responseManager : IResponseFragmentManagerGeneric, activity : FragmentActivity, val listener: (ClipData.Item) -> Unit)  : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<CustomerDevice> = items
    private var mActivity : FragmentActivity = activity
    private var mresponseManager : IResponseFragmentManagerGeneric = responseManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_lista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], mresponseManager, listener, mActivity)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerDevice, mresponseManager: IResponseFragmentManagerGeneric , listener: (ClipData.Item) -> Unit, activity: FragmentActivity) = with(itemView) {
            nombreDispositivo.text = item.nombre
            descripcion.text = item.descripcion

            r_customlista.setOnClickListener {
                mresponseManager.onSuccesResponse(CustomerDevice(item.id, item.nombre, item.descripcion, item.url, item.puerto, item.tipo))
                //CommonMethods.clearExistFragments(context as FragmentActivity)
                // Al seleccionar un dispositivo deberíamos poder seguir configurando la rutina
                (context as FragmentActivity).supportFragmentManager.popBackStack()



            }
        }
    }

}