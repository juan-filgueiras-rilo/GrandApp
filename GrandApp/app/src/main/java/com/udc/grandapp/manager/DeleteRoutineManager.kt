package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosDeleteRoutine
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.DeleteRoutineService
import java.lang.Exception
import java.net.SocketTimeoutException

class DeleteRoutineManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var DeleteRoutine: GenericModel?
        try {
            val datosPeticion: DatosDeleteRoutine = datos.mDatosOperaction as DatosDeleteRoutine
            DeleteRoutine = DeleteRoutineService().deleteRoutine(datosPeticion.id)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            DeleteRoutine = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            DeleteRoutine = null
        }
        datos.mResultado = DeleteRoutine as Any
    }
}