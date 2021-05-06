package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.manager.transferObjects.DatosUpdateDevice
import com.udc.grandapp.model.UpdateDeviceModel
import com.udc.grandapp.webServiceGrandServer.UpdateDeviceService
import java.lang.Exception
import java.net.SocketTimeoutException

class UpdateDeviceManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var updateDevice: UpdateDeviceModel?
        try {
            val datosPeticion: DatosUpdateDevice = datos.mDatosOperaction as DatosUpdateDevice
            updateDevice = UpdateDeviceService().updateDevice(datosPeticion.id, datosPeticion.name, datosPeticion.description, datosPeticion.userId)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            updateDevice = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            updateDevice = null
        }
        datos.mResultado = updateDevice as Any
    }
}