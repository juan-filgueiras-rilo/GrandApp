package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GetDevicesModel
import okhttp3.RequestBody

class GetDevicesService: GrandServer() {

    fun getDevices(): GetDevicesModel {
        val body: RequestBody = RequestBody.create(mediaType, "")

        doGetRequest(body, MetodoGetDevicesByUserId)

        return GetDevicesModel("nombre")
    }
}