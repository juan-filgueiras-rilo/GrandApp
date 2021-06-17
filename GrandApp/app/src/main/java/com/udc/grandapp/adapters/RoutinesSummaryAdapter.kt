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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.R
import com.udc.grandapp.fragments.RoutineView
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.model.RoutinesModel
import kotlinx.android.synthetic.main.custom_rutina.view.*
import kotlinx.android.synthetic.main.custom_rutina.view.descripcion
import kotlinx.android.synthetic.main.custom_rutina_dispositivo.view.*

class RoutinesSummaryAdapter(context: Context, val items: List<RoutinesModel>, activity: FragmentActivity, layout: Int, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<RoutinesSummaryAdapter.ViewHolder>() {
    private var mContext: Context = context
    private var mItems: List<RoutinesModel> = items
    private var mActivity: FragmentActivity = activity
    private var mLayout: Int = layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutinesSummaryAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.custom_rutina, parent, false)
        view.findViewById<TextView>(R.id.modificar).visibility = View.GONE
        return RoutinesSummaryAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutinesSummaryAdapter.ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mLayout, mActivity)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RoutinesModel, listener: (ClipData.Item) -> Unit, mLayout: Int, activity: FragmentActivity) = with(itemView) {
            when (mLayout) {
                R.layout.custom_rutina -> {
                    nombreRutina.text = item.nombre
                    descripcion.text = item.descripcion
                    ejecutar.setOnClickListener {
                        Toast.makeText(context, "Ejecutar rutina", Toast.LENGTH_LONG).show()
                    }
                    eliminar.setOnClickListener {
                        MaterialAlertDialogBuilder(context)
                                .setTitle(resources.getString(R.string.titlealert))
                                .setMessage(resources.getString(R.string.supporting_textRoutine))
                                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                                    // Respond to negative button press
                                }
                                .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                                    // Respond to positive button press
                                }
                                .show()
                    }
                    custom_rutina_parent.setOnClickListener {
                        val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                        ft.replace(R.id.mainFragment, RoutineView(/*item*/))
                        ft.addToBackStack("RoutinesSummaryAdapter")
                        ft.commit()
                    }
                }
                R.layout.custom_rutina_dispositivo -> {
                    nombreRutinaDispositivo.text = item.nombre
                    descripcion.text = item.descripcion
                }
            }
        }
    }

}