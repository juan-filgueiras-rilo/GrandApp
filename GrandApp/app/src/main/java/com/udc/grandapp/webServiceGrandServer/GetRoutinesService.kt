package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class GetRoutinesService: GrandServer() {

    fun getRoutines(userId: String, token: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "")
            doGetRequest(body, MetodoGetRoutinesByUserId)

            val routine : CustomerRoutine = CustomerRoutine(1, "rutina", "Rutina molona", "as")
        }catch (e:Exception){
            e.printStackTrace()
        }
        return GenericModel("0", "", "")
    }
}