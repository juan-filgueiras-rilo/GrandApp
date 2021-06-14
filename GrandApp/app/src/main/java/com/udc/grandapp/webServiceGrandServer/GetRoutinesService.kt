package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class GetRoutinesService: GrandServer() {

    fun getRoutines(datos: GenericManager.Companion.DatosThreaded, userId: String, token: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "")
            doGetRequest(datos, body, MetodoGetRoutinesByUserId, token)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return GenericModel("0", "", "")
    }
}