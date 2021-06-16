package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.GetDevicesService
import com.udc.grandapp.webServiceGrandServer.GetRoutinesService
import java.lang.Exception
import java.net.SocketTimeoutException

class GetRoutinesManager (activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        try {
//            GetRoutinesService().getRoutines(datos, "1","eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEsInJvbGUiOiJVU0VSIiwiZXhwIjoxNjIzODc1MDYyfQ.KUnldMJR1SM2w8baWuCWMQKbH7OccCONfF69ouetBXy1bkNelm4SZLWvGq1dan7l8OZhz4R6QPXDUgwLgKP3-Q")
            GetRoutinesService().getRoutines(datos, infoBd()!!.userId, infoBd()!!.token)
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
        }
    }
}