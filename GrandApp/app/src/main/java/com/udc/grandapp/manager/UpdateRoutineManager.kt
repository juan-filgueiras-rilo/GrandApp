package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.manager.transferObjects.DatosUpdateRoutine
import com.udc.grandapp.model.UpdateRoutineModel
import com.udc.grandapp.webServiceGrandServer.UpdateRoutineService
import java.lang.Exception
import java.net.SocketTimeoutException

class UpdateRoutineManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var updateRoutine: UpdateRoutineModel?
        try {
            val datosPeticion: DatosUpdateRoutine = datos.mDatosOperaction as DatosUpdateRoutine
            updateRoutine = UpdateRoutineService().updateRoutine(datosPeticion.id, datosPeticion.name,
            datosPeticion.description, datosPeticion.userId)//TODO deviceList
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            updateRoutine = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
            updateRoutine = null
        }
        datos.mResultado = updateRoutine as Any
    }
}