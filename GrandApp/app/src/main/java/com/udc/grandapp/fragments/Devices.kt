package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.udc.grandapp.manager.GetDevicesManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.fragment_devices.*


class Devices : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_devices, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        val listaExample: List<CustomerDevice> = listOf(CustomerDevice(1,"NombreProducto1", "loadURL"),
            CustomerDevice(2, "NombreProducto2", "loadURL"),
            CustomerDevice(3, "NombreProducto3", "loadURL")
        )

        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, listaExample, it1, R.layout.custom_dispositivo) {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }
            }
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titulo.text = activity?.resources?.getString(R.string.mis_dispositivos)
        addDevice.setOnClickListener {
            Toast.makeText(context, "Nuevo dispositivo", Toast.LENGTH_LONG).show()
            val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.fragmentDevices, NewDevice())
            ft?.addToBackStack("Devices")
            ft?.commit()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun getDevices(){
        val mGetDevicesManager: GetDevicesManager = GetDevicesManager(context as Activity)

        class ResponseManager() : IResponseManagerGeneric {
            val activity: Activity = context as Activity
            override fun onSuccesResponse(model: Any) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: List<DevicesModel> =  DevicesModel.Parse(modelResponse.json)
                    //TODO login?
                    startActivity(Intent(MainScreenActivity::class.simpleName))
                }
                else {
                    //Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                    activity.runOnUiThread {
                        MaterialAlertDialogBuilder(activity)
                                .setTitle(resources.getString(R.string.error))
                                .setMessage(modelResponse.mensaje)
                                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                                    // Respond to positive button press
                                }.show()
                    }
                }

            }

            override fun onErrorResponse(model: Any) {
                //Toast.makeText(context, "Error al obtener los dispositivos (Diálogo)", Toast.LENGTH_LONG).show()
                activity.runOnUiThread { MaterialAlertDialogBuilder(activity)
                        .setTitle(resources.getString(R.string.error))
                        .setMessage(resources.getString(R.string.supporting_textDeviceError))
                        .setNeutralButton(resources.getString(R.string.ok)){ dialog, which ->
                            // Respond to positive button press
                        }.show() }
            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mGetDevicesManager.realizarOperacion(responseManager, DatosOperacionGeneric())
    }
}