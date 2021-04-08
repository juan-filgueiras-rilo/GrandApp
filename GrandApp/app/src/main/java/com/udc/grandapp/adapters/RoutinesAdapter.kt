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
import com.udc.grandapp.fragments.NewRoutine
import com.udc.grandapp.fragments.Routine
import com.udc.grandapp.fragments.UpdateRoutine
import com.udc.grandapp.fragments.UpdateRoutineDevicesFragment
import com.udc.grandapp.items.CustomerRoutine
import kotlinx.android.synthetic.main.custom_lista.view.*
import kotlinx.android.synthetic.main.custom_rutina.view.*
import kotlinx.android.synthetic.main.custom_rutina.view.descripcion
import kotlinx.android.synthetic.main.custom_rutina_dispositivo.view.*
import kotlinx.android.synthetic.main.custom_rutina_dispositivo.view.line1

class RoutinesAdapter(context : Context, val items: List<CustomerRoutine>, fragmentManager: FragmentManager, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<RoutinesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<CustomerRoutine> = items
    private var mFragmentManager : FragmentManager = fragmentManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.custom_rutina, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mFragmentManager)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerRoutine, listener: (ClipData.Item) -> Unit, fragmentManager: FragmentManager) = with(itemView) {
            nombreRutina.text = item.name
            descripcion.text = item.description
            ejecutar.setOnClickListener {
                Toast.makeText(context, "Ejecutar rutina", Toast.LENGTH_LONG).show()
            }
            eliminar.setOnClickListener {
                Toast.makeText(context, "Eliminar rutina", Toast.LENGTH_LONG).show()
            }
            modificar.setOnClickListener {
                var fr = fragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentRoutines, NewRoutine())
                fr?.commit()
            }
            custom_rutina_parent.setOnClickListener {
                var fr = fragmentManager?.beginTransaction()
                fr?.replace(R.id.fragmentRoutines, UpdateRoutine())
                fr?.commit()
            }
        }
    }
}