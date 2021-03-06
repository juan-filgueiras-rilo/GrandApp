package com.udc.grandapp.fragments

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.items.RoutinesDevice
import com.udc.grandapp.manager.CreateRoutineManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseFragmentManagerGeneric
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosCreateRoutine
import com.udc.grandapp.model.CreateRoutineModel
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.custom_dispositivosrutina.view.*
import kotlinx.android.synthetic.main.custom_lista.*
import kotlinx.android.synthetic.main.custom_lista.view.*
import kotlinx.android.synthetic.main.edit_rutina.*
import java.util.*
import kotlin.collections.ArrayList

class AddRoutine : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private lateinit var responseManager: IResponseFragmentManagerGeneric
    private val mDeviceList: ArrayList<Int> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.edit_rutina, container, false)
        rootView.findViewById<Button>(R.id.guardarRutina).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.cancelarRutina).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.addDispositivoButton).visibility = View.VISIBLE
        rootView.findViewById<TextView>(R.id.addDispositivoText).visibility = View.VISIBLE
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        var listaExample = mutableListOf<CustomerDevice>()

        class ResponseManager() : IResponseFragmentManagerGeneric {
            override  fun onSuccesResponse(model: CustomerDevice) {
                var repetido = listaExample.find { disp -> model.equals(disp) }
                if (repetido == null) {
                    listaExample.add(model)
                    mDeviceList.add(model.id.toInt())
                }
                refresh(listaExample)
            }
            override fun onErrorResponse(model: String) {

            }
        }
        responseManager = ResponseManager()


        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guardarRutina.setOnClickListener {
            Toast.makeText(context, "Guardar Rutina", Toast.LENGTH_LONG).show()
            if (!validarRutina()){
                addRutine()
                CommonMethods.clearExistFragments(context as FragmentActivity)
            }
        }
        cancelarRutina.setOnClickListener {
            Toast.makeText(context, "Cancelar", Toast.LENGTH_LONG).show()
            CommonMethods.clearExistFragments(context as FragmentActivity)
        }
        addDispositivoButton.setOnClickListener {
            Toast.makeText(context, "A??adir dispositivo", Toast.LENGTH_LONG).show()
            val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.crearRutina, DeviceList(responseManager))
            ft?.addToBackStack("Add Routines")
            ft?.commit()
        }
        dayPicker.locale = Locale("es", "ES")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun validarRutina(): Boolean{
        var error: Boolean = false
        if (editTextNombre.text == null || editTextNombre.text!!.isEmpty()) {
            error = true
        }
        if (editTextDescripcion.text == null || editTextDescripcion.text!!.isEmpty()){
            error = true
        }
        return error
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun addRutine(){
        if (editTextNombre.text != null && !editTextNombre.text.toString().isEmpty() &&
                !editTextDescripcion.text.isEmpty() && editTextDescripcion.text.toString() != null) {
            val CreateRoutineManager: CreateRoutineManager = CreateRoutineManager(context as Activity)
            val activity: Activity = context as Activity
            class ResponseManager() : IResponseManagerGeneric {
                override fun onSuccesResponse(model: GenericModel) {
                    val modelResponse: GenericModel = model as GenericModel
                    if (modelResponse.error == "0") {
                        val routine: List<RoutinesModel> =  RoutinesModel.ParseList(modelResponse.json)
                        UserConfigManager(activity).insertarRoutinesBBDD(routine)
                        activity.runOnUiThread{goBack()}
                    }
                    else {//Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                        activity.runOnUiThread {
                            MaterialAlertDialogBuilder(activity)
                                .setTitle("Error")
                                .setMessage(modelResponse.mensaje)
                                .setNeutralButton("OK") { dialog, which ->
                                    // Respond to positive button press
                                }.show()
                        }
                    }
                }

                override fun onErrorResponse(model: String) {
                    activity.runOnUiThread{MaterialAlertDialogBuilder(activity)
                        .setTitle("Error")
                        .setMessage(model)
                        .setNeutralButton("OK") { dialog, which ->
                            // Respond to positive button press
                        }.show()}
                }
            }
            val responseManager: IResponseManagerGeneric = ResponseManager()
            var dias:  MutableList<String> = mutableListOf()
            for (i in dayPicker.selectedDays) {
                dias.add(i.toString())
            }

            var devices: MutableList<DevicesModel> = mutableListOf()
            for (id in mDeviceList) {
                devices.add(DevicesModel(
                        id.toString(),
                        "",
                        "",
                        "",
                        "",
                        "")) //TODO CORREGIR ESTA VAINA
            }

            CreateRoutineManager.realizarOperacion(responseManager, DatosCreateRoutine(editTextNombre.text.toString(),
                    editTextDescripcion.text.toString(), UserConfigManager.getUserInfoPersistente(context as Activity)!!.userId,
                    dias, datePicker1.hour, datePicker1.minute, devices))
        }
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
    fun goBack() {
        //CommonMethods.clearExistFragments(context as FragmentActivity)
    }
}