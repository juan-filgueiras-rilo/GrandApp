package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.view.InputDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.adapters.RoutinesSummaryAdapter
import com.udc.grandapp.adapters.ViewRoutineAdapter
import com.udc.grandapp.items.RoutinesDevice
import com.udc.grandapp.manager.GetDevicesManager
import com.udc.grandapp.manager.UpdateDeviceManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.manager.transferObjects.DatosUpdateDevice
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.model.UpdateDeviceModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.fragment_editdevice.*

class UpdateDevice(val id: Long, val nombre: String, val descripcion: String, val readOnly: Boolean) : Fragment() {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_editdevice, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
        if(readOnly) {
            rootView.findViewById<EditText>(R.id.editTextNombre).visibility = View.GONE
            rootView.findViewById<TextView>(R.id.viewNombre).visibility = View.VISIBLE
            rootView.findViewById<EditText>(R.id.editTextDescripcion).visibility = View.GONE
            rootView.findViewById<TextView>(R.id.viewDescripcion).visibility = View.VISIBLE
            rootView.findViewById<Button>(R.id.aceptar).visibility = View.GONE
            rootView.findViewById<Button>(R.id.cancelar).visibility = View.GONE
        } else {
            rootView.findViewById<Button>(R.id.aceptar).visibility = View.VISIBLE
            rootView.findViewById<Button>(R.id.cancelar).visibility = View.VISIBLE
        }
        val routineList: List<RoutinesModel> = UserConfigManager(context as FragmentActivity).getRoutinesFromBD()

        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                RoutinesSummaryAdapter(it, routineList, it1, R.layout.custom_rutina_dispositivo) { Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show() }
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(readOnly) {
            viewNombre.text = nombre
            viewDescripcion.text = descripcion
        } else {
            editTextNombre.setText(nombre)
            editTextDescripcion.setText(descripcion)
        }
        aceptar.setOnClickListener {
            updateDevices()
            CommonMethods.clearExistFragments(context as FragmentActivity)
        }
        cancelar.setOnClickListener {
            CommonMethods.clearExistFragments(context as FragmentActivity)
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    private fun updateDevices() {
        val mUpdateDeviceManager = UpdateDeviceManager(context as Activity)
        val activity: Activity = context as Activity

        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: GenericModel) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val device: UpdateDeviceModel = UpdateDeviceModel.Parse(modelResponse.json)
                    UserConfigManager(activity).actualizarDevice(device, activity)

                } else {
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
                        .setMessage("Error al actualizar los dispositivos")
                        .setNeutralButton("OK") { dialog, which ->
                            // Respond to positive button press
                        }.show()
            }
        }
        val responseManager: IResponseManagerGeneric = ResponseManager()
        mUpdateDeviceManager.realizarOperacion(responseManager, DatosUpdateDevice(id.toString(), editTextNombre.text.toString(), editTextDescripcion.text.toString()))
    }
}
