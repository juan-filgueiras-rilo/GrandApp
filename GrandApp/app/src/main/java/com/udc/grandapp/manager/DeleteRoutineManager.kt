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
        try {
            DeleteRoutineService().deleteRoutine(datos, infoBd()!!.token)
        } catch (e: SocketTimeoutException){
            e.printStackTrace()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}