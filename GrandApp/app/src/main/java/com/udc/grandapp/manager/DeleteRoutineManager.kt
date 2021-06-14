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
        var deleteRoutine: GenericModel?
        try {
            val datosPeticion: DatosDeleteRoutine = datos.mDatosOperaction as DatosDeleteRoutine
            deleteRoutine = DeleteRoutineService().deleteRoutine(datosPeticion.routineId, infoBd()!!.token)
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            deleteRoutine = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
            deleteRoutine = null
        }
        datos.mResultado = deleteRoutine!!
    }
}