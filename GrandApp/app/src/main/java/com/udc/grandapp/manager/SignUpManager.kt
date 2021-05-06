package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.SingUpService
import java.lang.Exception
import java.net.SocketTimeoutException

class SignUpManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var signup: GenericModel?
        try {
            val datosPeticion: DatosSingUp = datos.mDatosOperaction as DatosSingUp
            signup = SingUpService().signup(datosPeticion.mUser, datosPeticion.mEmail, datosPeticion.mPassword)
        }catch (e:SocketTimeoutException){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            signup = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
            signup = null
        }
        datos.mResultado = signup as Any
    }
}