package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosDeleteDevice
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.DeleteDeviceService
import java.lang.Exception
import java.net.SocketTimeoutException

class DeleteDeviceManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var DeleteDevice: GenericModel?
        try {
            val datosPeticion: DatosDeleteDevice = datos.mDatosOperaction as DatosDeleteDevice
            DeleteDevice = DeleteDeviceService().deleteDevice(datosPeticion.id)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            DeleteDevice = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            DeleteDevice = null
        }
        datos.mResultado = DeleteDevice as Any
    }
}