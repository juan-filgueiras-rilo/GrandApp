package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GetDevicesModel
import okhttp3.RequestBody

class GetRoutinesService: GrandServer() {

    fun getDevices(): GetDevicesModel {
        val body: RequestBody = RequestBody.create(mediaType, "")

        doGetRequest(body, MetodoGetRoutinesByUserId)

        return GetDevicesModel("nombre")
    }
}