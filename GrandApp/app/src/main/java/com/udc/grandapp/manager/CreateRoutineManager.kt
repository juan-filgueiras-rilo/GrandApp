package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.manager.transferObjects.DatosCreateRoutine
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.model.CreateRoutineModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.CreateRoutineService
import java.lang.Exception
import java.net.SocketTimeoutException

class CreateRoutineManager(activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        try {
            CreateRoutineService().createRoutine(datos, infoBd()!!.token)
        }catch (e: Exception){
            e.printStackTrace()
           // datos.mActivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivity, e.message, Toast.LENGTH_LONG).show() })
        }
    }
}