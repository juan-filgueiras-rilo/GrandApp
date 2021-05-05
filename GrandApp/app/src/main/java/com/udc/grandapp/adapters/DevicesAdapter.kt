package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.fragments.UpdateDevice
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.custom_dispositivo.view.*
import kotlinx.android.synthetic.main.custom_lista.view.*

class DevicesAdapter(context : Context, val items: List<CustomerDevice>, activity : FragmentActivity, layout: Int, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<CustomerDevice> = items
    private var mActivity : FragmentActivity = activity
    private var mLayout : Int = layout


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(mLayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mLayout, mActivity)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerDevice, listener: (ClipData.Item) -> Unit, mLayout: Int, activity: FragmentActivity) = with(itemView) {
            when (mLayout) {
                R.layout.custom_dispositivo -> {
                    nombreProducto_cd.text = item.nombre
                    bombilla_cd.setBackgroundColor(context.resources.getColor(R.color.green))
                    consulta.setOnClickListener {
                        val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                        ft.replace(R.id.fragmentDevices, UpdateDevice())
                        ft.addToBackStack("DevicesAdapter")
                        ft.commit()
                    }
                    encender.setOnClickListener {
                        Toast.makeText(context, "Encender/Apagar", Toast.LENGTH_LONG).show()
                    }
                    eliminar.setOnClickListener {
                        Toast.makeText(context, "Eliminar dispositivo", Toast.LENGTH_LONG).show()
                    }
                }
                R.layout.custom_lista -> {
                    nombreDispositivo.setText(item.nombre)
                    descripcion.setText(item.url)
                    r_customlista.setOnClickListener{
                        CommonMethods.clearExistFragments(context as FragmentActivity)
                    }
                }
                R.layout.custom_nuevodispositivo -> {

                }
            }
        }
    }
}