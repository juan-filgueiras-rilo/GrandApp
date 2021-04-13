package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.fragments.RoutineView
import com.udc.grandapp.items.CustomerRoutine
import kotlinx.android.synthetic.main.custom_rutina.view.*

class RoutinesSummaryAdapter(context : Context, val items: List<CustomerRoutine>, activity: FragmentActivity, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<RoutinesSummaryAdapter.ViewHolder>(){
    private var mContext : Context = context
    private var mItems : List<CustomerRoutine> = items
    private var mActivity : FragmentActivity = activity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutinesSummaryAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.custom_rutina, parent, false)
        view.findViewById<TextView>(R.id.modificar).visibility = View.GONE
        return RoutinesSummaryAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutinesSummaryAdapter.ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mActivity)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerRoutine, listener: (ClipData.Item) -> Unit, activity: FragmentActivity) = with(itemView) {
            nombreRutina.text = item.name
            descripcion.text = item.description
            ejecutar.setOnClickListener {
                Toast.makeText(context, "Ejecutar rutina", Toast.LENGTH_LONG).show()
            }
            eliminar.setOnClickListener {
                Toast.makeText(context, "Eliminar rutina", Toast.LENGTH_LONG).show()
            }
            custom_rutina_parent.setOnClickListener {
                val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                ft.replace(R.id.mainFragment, RoutineView())
                ft.addToBackStack("RoutinesSummaryAdapter")
                ft.commit()
            }
        }
    }

}