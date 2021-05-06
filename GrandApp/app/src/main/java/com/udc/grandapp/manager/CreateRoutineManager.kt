package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosCreateRoutine
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.model.CreateRoutineModel
import com.udc.grandapp.webServiceGrandServer.CreateRoutineService
import java.lang.Exception
import java.net.SocketTimeoutException

class CreateRoutineManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var createRoutine: CreateRoutineModel?
        try {
            val datosPeticion: DatosCreateRoutine = datos.mDatosOperaction as DatosCreateRoutine
            createRoutine = CreateRoutineService().createRoutine()
            //createRoutine(datosPeticion.name, datosPeticion.description, datosPeticion.userId, datosPeticion.deviceList)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            createRoutine = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            createRoutine = null
        }
        datos.mResultado = createRoutine as Any
    }
}