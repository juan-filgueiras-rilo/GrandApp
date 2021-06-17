package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.manager.UpdateRoutineManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseFragmentManagerGeneric
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.model.UpdateRoutineModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.edit_rutina.*
import java.util.*

class UpdateRoutine(layout: Int, idRoutine: Int) : Fragment() {

    private var layout : Int = layout
    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private val idRoutine : Int = idRoutine
    private lateinit var responseManager: IResponseFragmentManagerGeneric


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.edit_rutina, container, false)
        rootView.findViewById<Button>(R.id.guardarRutina).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.cancelarRutina).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.addDispositivoButton).visibility = View.VISIBLE
        rootView.findViewById<TextView>(R.id.addDispositivoText).visibility = View.VISIBLE
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        val listaExample: MutableList<CustomerDevice> = getDevicesFromByIdRutinaBD(context as FragmentActivity, idRoutine)

        class ResponseManager() : IResponseFragmentManagerGeneric {
            override  fun onSuccesResponse(model: CustomerDevice) {
                var repetido = listaExample.find { disp -> model.equals(disp) }
                if (repetido == null) {
                    listaExample.add(model)
                }
                refresh(listaExample)
            }
            override fun onErrorResponse(model: String) {

            }
        }
        responseManager = ResponseManager()

        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, listaExample as ArrayList<CustomerDevice>, it1, R.layout.custom_dispositivosrutina, {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }, this)
            }
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(layout) {
            1 -> {
                guardarRutina.setOnClickListener {
                    updateRoutines()
                    CommonMethods.clearExistFragments(context as FragmentActivity)
                }
                cancelarRutina.setOnClickListener {
                    Toast.makeText(context, "Cancelar", Toast.LENGTH_LONG).show()
                    CommonMethods.clearExistFragments(context as FragmentActivity)
                }
                addDispositivoButton.setOnClickListener {
                    val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                    ft?.replace(R.id.crearRutina, DeviceList(responseManager))
                    ft?.addToBackStack("Update Routine")
                    ft?.commit()
                }
            }
            2 -> {
                guardarRutina.setOnClickListener {
                    updateRoutines()
                    (context as FragmentActivity).supportFragmentManager.popBackStack()
                }
                cancelarRutina.setOnClickListener {
                    Toast.makeText(context, "Cancelar", Toast.LENGTH_LONG).show()
                    (context as FragmentActivity).supportFragmentManager.popBackStack()
                }
                /*addDispositivoButton.setOnClickListener {
                    val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                    ft?.replace(R.id.crearRutina, DeviceList(responseManager))
                    ft?.addToBackStack("Update Routine")
                    ft?.commit()
                }*/

            }
        }
        dayPicker.locale = Locale("es", "ES")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun updateRoutines(){
        val mUpdateRoutineManager: UpdateRoutineManager = UpdateRoutineManager(context as Activity)
        val activity: Activity = context as Activity
        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: GenericModel) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: UpdateRoutineModel =  UpdateRoutineModel.Parse(modelResponse.json)
                    //TODO login?
                    startActivity(Intent(MainScreenActivity::class.simpleName))
                }
                else {
                    //Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                        MaterialAlertDialogBuilder(activity)
                                .setTitle("Error")
                                .setMessage(modelResponse.mensaje)
                                .setNeutralButton("OK") { dialog, which ->
                                    // Respond to positive button press
                                }.show()


                }

            }

            override fun onErrorResponse(model: String) {
                    //Toast.makeText(context, "Error al actualizar los dispositivos (Diálogo)", Toast.LENGTH_LONG).show()
                        MaterialAlertDialogBuilder(activity)
                                .setTitle("Error")
                                .setMessage("Error al actualizar las rutinas")
                                .setNeutralButton("OK") { dialog, which ->
                                    // Respond to positive button press
                                }.show()
            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mUpdateRoutineManager.realizarOperacion(responseManager, DatosOperacionGeneric())
    }

    private fun getDevicesFromByIdRutinaBD(context: Context, idRoutine: Int): MutableList<CustomerDevice> {
        val dbManager = UserConfigManager(context)
        val devicesModel: List<DevicesModel> = dbManager.getDevicesByRoutineFromBD(idRoutine)
        val customerDevices: MutableList<CustomerDevice> = arrayListOf<CustomerDevice>()
        for (device in devicesModel) {
            customerDevices.add(CustomerDevice(device.id.toLong(), device.nombre, device.descripcion, device.url, device.puerto.toLong(), device.tipo))
        }
        UserConfigManager.reiniciarInfoPersistente(context)
        return customerDevices
    }
    fun refresh(listaExample: List<CustomerDevice>) {
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, listaExample as ArrayList<CustomerDevice>, it1, R.layout.custom_dispositivosrutina, {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }, this)
            }
        }
    }

}