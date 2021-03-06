package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DeviceListAdapter
import com.udc.grandapp.adapters.DeviceSummaryAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.manager.GetDevicesManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseFragmentManagerGeneric
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.utils.CommonMethods

class DeviceList(responseManager: IResponseFragmentManagerGeneric) : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private var responseManager: IResponseFragmentManagerGeneric = responseManager
    private lateinit var mCustomerDevices: MutableList<CustomerDevice>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_w_recycler, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                mCustomerDevices = getDevicesFromBD(it)
                DeviceListAdapter(it, mCustomerDevices, responseManager, it1) {
                }
            }
        }
        return rootView
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun getDevices(){
        val mGetDevicesManager: GetDevicesManager = GetDevicesManager(context as Activity)

        class ResponseManager() : IResponseManagerGeneric {
            val activity: Activity = context as Activity
            override fun onSuccesResponse(model: GenericModel) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: List<DevicesModel> =  DevicesModel.Parse(modelResponse.json)
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
                    //Toast.makeText(context, "Error al obtener los dispositivos (Di??logo)", Toast.LENGTH_LONG).show()
                    MaterialAlertDialogBuilder(activity)
                            .setTitle("Error")
                            .setMessage("Error al obtener los dispositivos")
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