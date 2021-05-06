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
        var loginModel: GenericModel?
        try {
            val datosPeticion: DatosLogin = datos.mDatosOperaction as DatosLogin
            loginModel = LoginService().solicitudLogin(datosPeticion.nombre, datosPeticion.pwd)
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable {
                Toast.makeText(
                    datos.mActivity,
                    "Servidores no disponibles",
                    Toast.LENGTH_LONG
                ).show()
            })
            loginModel = null
        } catch (e: Exception) {
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable {
                Toast.makeText(
                    datos.mActivity,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            })
            loginModel = null

        }
        datos.mResultado = loginModel as Any
    }
}