package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.manager.transferObjects.DatosUpdateProfile
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.UpdateProfileModel
import com.udc.grandapp.webServiceGrandServer.UpdateProfileService
import java.lang.Exception
import java.net.SocketTimeoutException

class UpdateProfileManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var updateProfile: GenericModel?
        try {
            val datosPeticion: DatosUpdateProfile = datos.mDatosOperaction as DatosUpdateProfile
            updateProfile = UpdateProfileService().updateProfile(datosPeticion.userName, datosPeticion.email, infoBd()!!.token)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            updateProfile = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
            updateProfile = null
        }
        datos.mResultado = updateProfile!!
    }
}