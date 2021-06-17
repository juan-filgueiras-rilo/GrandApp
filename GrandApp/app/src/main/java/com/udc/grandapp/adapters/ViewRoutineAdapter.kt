package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.R
import com.udc.grandapp.fragments.RoutineView
import com.udc.grandapp.fragments.UpdateRoutine
import com.udc.grandapp.items.RoutinesDevice
import com.udc.grandapp.model.RoutinesModel
import kotlinx.android.synthetic.main.custom_rutina_dispositivo.view.*


class ViewRoutineAdapter(context : Context, val items: List<RoutinesDevice>, fragmentActivity: FragmentActivity, layout : Int, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<ViewRoutineAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<RoutinesDevice> = items
    private var mActivity : FragmentActivity = fragmentActivity
    private var mLayoutPrincipal : Int = layout


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_rutina_dispositivo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mActivity, mLayoutPrincipal)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RoutinesDevice, listener: (ClipData.Item) -> Unit, activity: FragmentActivity, layout: Int) = with(itemView) {
            nombreRutinaDispositivo.text = item.nombre
            descripcion.text = item.descripcion
            ver.setOnClickListener {
                val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                when(layout){
                    1 -> {
                        //ft.replace(R.id.mainFragment, RoutineView(item))
                        ft.replace(R.id.mainFragment, RoutineView(RoutinesModel()))

                    }
                    2 -> {
                        ft.replace(R.id.drawerLayout, UpdateRoutine(2))
                    }
                }
                ft.addToBackStack("ViewRoutineAdapter")
                ft.commit()
            }
            borrar.setOnClickListener {
                MaterialAlertDialogBuilder(context)
                    .setTitle(resources.getString(R.string.titlealert))
                    .setMessage(resources.getString(R.string.supporting_textRoutineFromDevice))
                    .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                        // Respond to negative button press
                    }
                    .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                        // Respond to positive button press
                    }
                    .show()
            }
        }
    }
}