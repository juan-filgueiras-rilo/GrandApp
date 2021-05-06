package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosCreateDevice
import com.udc.grandapp.model.CreateDeviceModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.CreateDeviceService
import java.lang.Exception
import java.net.SocketTimeoutException

class CreateDeviceManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var createDevice: GenericModel?
        try {
            val datosPeticion: DatosCreateDevice = datos.mDatosOperaction as DatosCreateDevice
            createDevice = CreateDeviceService().createDevice(datosPeticion.name, datosPeticion.description, datosPeticion.userId, infoBd()!!.token)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            createDevice = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
            createDevice = null
        }
        datos.mResultado = createDevice as Any
    }
}