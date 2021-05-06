package com.udc.grandapp.manager

import android.app.Activity
import android.widget.Toast
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.webServiceGrandServer.GetDevicesService
import com.udc.grandapp.webServiceGrandServer.GetRoutinesService
import java.lang.Exception
import java.net.SocketTimeoutException

class GetRoutinesManager (activity: Activity) : GenericManager(activity) {
    override fun onWorkerExecute(datos: Companion.DatosThreaded) {
        var routines: GenericModel?
        try {
            routines = GetRoutinesService().getRoutines()
        }catch (e: SocketTimeoutException){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, "Servidores no disponibles", Toast.LENGTH_LONG).show() })
            routines = null
        }catch (e: Exception){
            e.printStackTrace()
            datos.mActivivity.runOnUiThread(Runnable { Toast.makeText(datos.mActivivity, e.message, Toast.LENGTH_LONG).show() })
            routines = null
        }
        datos.mResultado = routines as Any
    }
}