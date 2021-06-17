package com.udc.grandapp.fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.fragment_devices.*


class Devices : Fragment() {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var mCustomerDevices: MutableList<CustomerDevice>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_devices, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titulo.text = activity?.resources?.getString(R.string.mis_dispositivos)
        addDevice.setOnClickListener {
            Toast.makeText(context, "Nuevo dispositivo", Toast.LENGTH_LONG).show()
            val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            ft?.detach(this)
            ft?.replace(R.id.main_container, NewDevice())
            ft?.addToBackStack("Devices")
            ft?.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                mCustomerDevices = getDevicesFromBD(it)
                DevicesAdapter(it, mCustomerDevices as ArrayList<CustomerDevice>, it1, R.layout.custom_dispositivo, {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }, this)
            }
        }
    }

    fun reload() {
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                mCustomerDevices = getDevicesFromBD(it)
                DevicesAdapter(it, mCustomerDevices as ArrayList<CustomerDevice>, it1, R.layout.custom_dispositivo, {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }, this)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun getDevices() {
        val mGetDevicesManager: GetDevicesManager = GetDevicesManager(context as Activity)

        class ResponseManager() : IResponseManagerGeneric {
            val activity: Activity = context as Activity
            override fun onSuccesResponse(model: GenericModel) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: List<DevicesModel> = DevicesModel.Parse(modelResponse.json)
                    //TODO login?
                    startActivity(Intent(MainScreenActivity::class.simpleName))
                } else {
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
                //Toast.makeText(context, "Error al obtener los dispositivos (Diálogo)", Toast.LENGTH_LONG).show()
                MaterialAlertDialogBuilder(activity)
                        .setTitle("Error")
                        .setMessage("Al obtener los dispositivos")
                        .setNeutralButton("OK") { dialog, which ->
                            // Respond to positive button press
                        }.show()


            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mGetDevicesManager.realizarOperacion(responseManager, DatosOperacionGeneric())
    }

    private fun getDevicesFromBD(context: Context): MutableList<CustomerDevice> {
        val dbManager = UserConfigManager(context)
        val devicesModel: List<DevicesModel> = dbManager.getDevicesFromBD()
        val customerDevices: MutableList<CustomerDevice> = arrayListOf<CustomerDevice>()
        for (device in devicesModel) {
            customerDevices.add(CustomerDevice(device.id.toLong(), device.nombre, device.descripcion, device.url, device.puerto.toLong(), device.tipo))
        }
        UserConfigManager.reiniciarInfoPersistente(context)
        return customerDevices
    }
}