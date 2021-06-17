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
import com.udc.grandapp.fragments.UpdateRoutine
import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.manager.DeleteRoutineManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosDeleteRoutine
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.custom_rutina.view.*
import kotlinx.android.synthetic.main.custom_rutina.view.descripcion

class RoutinesAdapter(context : Context, val items: List<CustomerRoutine>, activity: FragmentActivity, val listener: (ClipData.Item) -> Unit,val fragment: Fragment) : RecyclerView.Adapter<RoutinesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<CustomerRoutine> = items
    private var mActivity : FragmentActivity = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.custom_rutina, parent, false)
        view.findViewById<TextView>(R.id.modificar).visibility = View.VISIBLE
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mActivity)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerRoutine, listener: (ClipData.Item) -> Unit, activity: FragmentActivity) = with(itemView) {
            nombreRutina.text = item.name
            descripcion.text = item.description
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
                        eliminarRutina(activity)
                    }
                    .show()
            }
            modificar.setOnClickListener {
                val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentRoutines, UpdateRoutine(1, item.id.toInt()))
                ft.addToBackStack("RoutinesAdapter")
                ft.commit()
            }
            custom_rutina_parent.setOnClickListener {
                val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragmentRoutines, UpdateRoutine(1, item.id.toInt()))
                ft.addToBackStack("RoutinesAdapter")
                ft.commit()
            }
        }

        fun eliminarRutina(activity: FragmentActivity) {
            val deleteRoutineManager: DeleteRoutineManager = DeleteRoutineManager(activity)
            class ResponseManager() : IResponseManagerGeneric {
                override fun onSuccesResponse(model: GenericModel) {
                    val modelResponse: GenericModel = model as GenericModel
                    if (modelResponse.error == "0") {
                        //val routine: CreateRoutineModel =  CreateRoutineModel.Parse(modelResponse.json)
                        //insertarRutinaBD(login)
                        //TODO Insertar en BD, cambiar estructura de bd para almacenar dispositivos asociados a rutina?
                        CommonMethods.clearExistFragments(activity)
                    }
                    else {//Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                        MaterialAlertDialogBuilder(activity)
                                .setTitle("Error")
                                .setMessage(modelResponse.mensaje)
                                .setNeutralButton("OK") { dialog, which ->
                                    // Respond to positive button press
                                }.show()
                    }
                }

                override fun onErrorResponse(model: String) {
                    MaterialAlertDialogBuilder(activity)
                            .setTitle("Error")
                            .setMessage("Error al guardar la rutina")
                            .setNeutralButton("OK") { dialog, which ->
                                // Respond to positive button press
                            }.show()
                }
        }
            val responseManager: IResponseManagerGeneric = ResponseManager()
            deleteRoutineManager.realizarOperacion(responseManager, DatosDeleteRoutine("13"))
        }
    }
}
