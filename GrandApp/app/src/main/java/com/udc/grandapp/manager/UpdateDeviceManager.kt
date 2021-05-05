package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.UpdateDeviceModel
import com.udc.grandapp.webServiceGrandServer.UpdateDeviceService
import java.lang.Exception

class UpdateDeviceManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExceute(datos: Companion.DatosThreaded) {
        var signup: UpdateDeviceModel?
        try {
            signup = UpdateDeviceService().updateDevice()
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            signup = null
        }
        datos.mResultado = signup as Any
    }
}