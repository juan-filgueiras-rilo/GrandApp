package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.UpdateProfileModel
import com.udc.grandapp.webServiceGrandServer.UpdateProfileService
import java.lang.Exception

class UpdateProfileManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExceute(datos: Companion.DatosThreaded) {
        var updateProfile: UpdateProfileModel?
        try {
            updateProfile = UpdateProfileService().updateProfile()
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            updateProfile = null
        }
        datos.mResultado = updateProfile as Any
    }
}