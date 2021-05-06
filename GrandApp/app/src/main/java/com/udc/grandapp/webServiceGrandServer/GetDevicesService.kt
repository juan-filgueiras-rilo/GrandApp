package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class GetDevicesService: GrandServer() {

    fun getDevices(): GenericModel {
        val body: RequestBody = RequestBody.create(mediaType, "")

        doGetRequest(body, MetodoGetDevicesByUserId)

        return GenericModel("0", "", "")
    }
}