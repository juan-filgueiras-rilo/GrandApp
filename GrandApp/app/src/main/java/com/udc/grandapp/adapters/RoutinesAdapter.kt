package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.items.Routine
import kotlinx.android.synthetic.main.custom_rutina.view.*

class RoutinesAdapter(context : Context, val items: List<Routine>, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<RoutinesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<Routine> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_rutina, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Routine, listener: (ClipData.Item) -> Unit) = with(itemView) {
            nombreRutina.text = item.nombre
            descripcion.text = item.descripcion
            ejecutar.setOnClickListener {
                Toast.makeText(context, "Pulsado el botón de ejecutar", Toast.LENGTH_LONG).show()
            }
            eliminar.setOnClickListener {
                Toast.makeText(context, "Pulsado el botón de eliminar", Toast.LENGTH_LONG).show()
            }
            modificar.setOnClickListener {
                Toast.makeText(context, "Pulsado el botón de modificar", Toast.LENGTH_LONG).show()
            }
        }
    }
}