package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.R
import com.udc.grandapp.fragments.RoutineView
import com.udc.grandapp.fragments.UpdateRoutine
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.webServiceHandleDevice.HandleDeviceService
import kotlinx.android.synthetic.main.custom_rutina.view.*
import kotlinx.android.synthetic.main.custom_rutina.view.descripcion
import kotlinx.android.synthetic.main.custom_rutina_dispositivo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutinesSummaryAdapter(context: Context, val items: List<RoutinesModel>, activity: FragmentActivity, layout: Int, val listener: (ClipData.Item) -> Unit, val fragment: Fragment) : RecyclerView.Adapter<RoutinesSummaryAdapter.ViewHolder>() {
    private var mContext: Context = context
    private var mItems: List<RoutinesModel> = items
    private var mActivity: FragmentActivity = activity
    private var mLayout: Int = layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutinesSummaryAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(mLayout, parent, false)
        return RoutinesSummaryAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutinesSummaryAdapter.ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mLayout, mActivity, fragment)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RoutinesModel, listener: (ClipData.Item) -> Unit, mLayout: Int, activity: FragmentActivity, fragment: Fragment) = with(itemView) {
            when (mLayout) {
                R.layout.custom_rutina -> {
                    nombreRutina.text = item.nombre
                    descripcion.text = item.descripcion
                    modificar.visibility = View.GONE
                    eliminar.visibility = View.GONE
                    ejecutar.setOnClickListener {
                        //Toast.makeText(context, "Ejecutar rutina", Toast.LENGTH_LONG).show()
                        var devices: List<DevicesModel> = UserConfigManager(context).getDevicesByRoutineFromBD(item.id.toInt())
                        for (i in devices) {
                            val scope = CoroutineScope(Dispatchers.IO)
                            scope.launch {
                                val status = HandleDeviceService(i.url,i.puerto.toInt(),i.tipo).queryDevice()
                                if (status == "on") {
                                    HandleDeviceService(
                                        i.url,
                                        i.puerto.toInt(),
                                        i.tipo
                                    ).powerOffDevice()
                                } else {
                                    HandleDeviceService(
                                        i.url,
                                        i.puerto.toInt(),
                                        i.tipo
                                    ).powerOnDevice()
                                }
                            }
                        }
                    }
                    custom_rutina_parent.setOnClickListener {
                        val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                        ft.replace(R.id.mainFragment, RoutineView(item))
                        ft.addToBackStack("RoutinesSummaryAdapter")
                        ft.commit()
                    }
                }
                R.layout.custom_rutina_dispositivo -> {
                    routineName.text = item.nombre
                    routineDescription.text = item.descripcion
                    ver.setOnClickListener{
                        val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                        ft?.detach(fragment)
                        ft.replace(R.id.main_container, UpdateRoutine(2, item.id.toInt()))
                        ft.addToBackStack("RoutinesSummaryAdapter")
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

                            }
                            .show()
                    }

                }
                R.layout.custom_rutina_dispositivo_read_only -> {
                    routineName.text = item.nombre
                    routineDescription.text = item.descripcion
                }
            }
        }
    }

}