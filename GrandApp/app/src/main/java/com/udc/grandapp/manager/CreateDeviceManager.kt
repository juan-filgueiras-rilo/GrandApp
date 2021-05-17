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
        try {
            CreateDeviceService().createDevice(datos, infoBd()!!.token)
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
        }
    }
}