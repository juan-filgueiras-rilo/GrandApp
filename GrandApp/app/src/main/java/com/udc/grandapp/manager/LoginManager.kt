package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosLogin
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.LoginService
import java.lang.Exception
import java.net.SocketTimeoutException

class LoginManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        try {
            LoginService().solicitudLogin(datos)
        } catch (e: Exception) {
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable {
                Toast.makeText(
                    datos.mActivity,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            })
        }
    }
}