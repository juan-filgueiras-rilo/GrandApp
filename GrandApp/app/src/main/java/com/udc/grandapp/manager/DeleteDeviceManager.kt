package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosDeleteDevice
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.CreateDeviceService
import com.udc.grandapp.webServiceGrandServer.DeleteDeviceService
import java.lang.Exception
import java.net.SocketTimeoutException

class DeleteDeviceManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        try {
            DeleteDeviceService().deleteDevice(datos, infoBd()!!.token)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}