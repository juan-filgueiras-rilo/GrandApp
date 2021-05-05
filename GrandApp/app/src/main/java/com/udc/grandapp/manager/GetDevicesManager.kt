package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.GetDevicesService
import java.lang.Exception

class GetDevicesManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExceute(datos: Companion.DatosThreaded) {
        var devices: GenericModel?
        try {
            devices = GetDevicesService().getDevices()
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            devices = null
        }
        datos.mResultado = devices as Any
    }
}