package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosChangePwd
import com.udc.grandapp.manager.transferObjects.DatosCreateDevice
import com.udc.grandapp.model.CreateDeviceModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.ChangePwdService
import com.udc.grandapp.webServiceGrandServer.CreateDeviceService
import java.lang.Exception
import java.net.SocketTimeoutException

class ChangePwdManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var changePwd: GenericModel?
        try {
            val datosPeticion: DatosChangePwd = datos.mDatosOperaction as DatosChangePwd
            changePwd = ChangePwdService().changePwd(datosPeticion.oldPwd, datosPeticion.newPwd, infoBd()!!.token)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            changePwd = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
            changePwd = null
        }
        datos.mResultado = changePwd as Any
    }
}