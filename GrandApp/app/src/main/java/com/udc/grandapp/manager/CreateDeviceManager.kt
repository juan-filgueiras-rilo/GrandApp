package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.CreateDeviceModel
import com.udc.grandapp.webServiceGrandServer.CreateDeviceService
import java.lang.Exception

class CreateDeviceManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExceute(datos: Companion.DatosThreaded) {
        var createDevice: CreateDeviceModel?
        try {
            createDevice = CreateDeviceService().createDevice()
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            createDevice = null
        }
        datos.mResultado = createDevice as Any
    }
}