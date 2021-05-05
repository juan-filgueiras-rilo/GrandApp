package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.GetDevicesModel
import com.udc.grandapp.webServiceGrandServer.GetDevicesService
import java.lang.Exception

class GetDevicesManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExceute(datos: Companion.DatosThreaded) {
        var getDevices: GetDevicesModel?
        try {
            getDevices = GetDevicesService().getDevices()
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            getDevices = null
        }
        datos.mResultado = getDevices as Any
    }
}