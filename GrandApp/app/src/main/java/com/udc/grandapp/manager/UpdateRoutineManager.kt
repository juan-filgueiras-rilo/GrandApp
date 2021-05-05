package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.UpdateRoutineModel
import com.udc.grandapp.webServiceGrandServer.UpdateRoutineService
import java.lang.Exception

class UpdateRoutineManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExceute(datos: Companion.DatosThreaded) {
        var updateRoutine: UpdateRoutineModel?
        try {
            updateRoutine = UpdateRoutineService().updateProfile()
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            updateRoutine = null
        }
        datos.mResultado = updateRoutine as Any
    }
}