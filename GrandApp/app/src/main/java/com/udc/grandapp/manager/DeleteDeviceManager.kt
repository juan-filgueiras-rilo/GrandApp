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
        var deleteDevice: GenericModel?
        try {
            val datosPeticion: DatosDeleteDevice = datos.mDatosOperaction as DatosDeleteDevice
            deleteDevice = DeleteDeviceService().deleteDevice(datosPeticion.deviceId, infoBd()!!.token)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            deleteDevice = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
            deleteDevice = null
        }
        datos.mResultado = deleteDevice as Any
    }
}