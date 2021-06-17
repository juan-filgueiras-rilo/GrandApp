package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.UpdateDeviceService
import java.lang.Exception
import java.net.SocketTimeoutException

class UpdateDeviceManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var updateDevice: GenericModel?
        try {
            updateDevice = UpdateDeviceService().updateDevice(datos, infoBd()!!.token)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            updateDevice = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
            updateDevice = null
        }
        datos.mResultado = updateDevice!!
    }
}