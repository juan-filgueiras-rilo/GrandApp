package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.GetDevicesService
import java.lang.Exception
import java.net.SocketTimeoutException

class GetDevicesManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        try {
            GetDevicesService().getDevices(datos, "1","eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEsInJvbGUiOiJVU0VSIiwiZXhwIjoxNjIzNzg2MTk3fQ.MZHKP2jMHVLRjs2rmZ7dMBVcK25_7dpkexhjo4Y7oNIeooQ7BOlbFOLdLC5o3CnmX5Kbj9yaVpVigd7gVA2ahQ")
//            devices = GetDevicesService().getDevices(infoBd()!!.userId, infoBd()!!.token)
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
        }
    }
}