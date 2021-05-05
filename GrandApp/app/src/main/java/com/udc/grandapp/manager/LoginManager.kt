package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.LoginService
import java.lang.Exception

class LoginManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExceute(datos: Companion.DatosThreaded) {
        var loginModel: GenericModel?
        try {
            loginModel = LoginService().solicitudLogin()
        }catch (e:Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            loginModel = null
        }
        datos.mResultado = loginModel as Any
    }
}