package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class GetRoutinesService: GrandServer() {

    fun getRoutines(): GenericModel {
        val body: RequestBody = RequestBody.create(mediaType, "")

        doGetRequest(body, MetodoGetRoutinesByUserId)

        return GenericModel("0", "", "")
    }
}