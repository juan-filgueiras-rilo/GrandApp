package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.CreateRoutineModel
import com.udc.grandapp.webServiceGrandServer.CreateRoutineService
import java.lang.Exception

class CreateRoutineManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExceute(datos: Companion.DatosThreaded) {
        var createRoutine: CreateRoutineModel?
        try {
            createRoutine = CreateRoutineService().createRoutine()
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            createRoutine = null
        }
        datos.mResultado = createRoutine as Any
    }
}