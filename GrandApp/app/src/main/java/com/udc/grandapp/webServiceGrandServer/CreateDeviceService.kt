package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.CreateDeviceModel
import okhttp3.RequestBody

class CreateDeviceService: GrandServer() {

    fun createDevice(name: String, description: String, userId: String): CreateDeviceModel {
        val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                "    \"name\": \"bombilla\",\n" +
                "    \"description\": \"bombilla molona\"\n" +
                "    \"userId\": \"1\"\n" +
                "}")

        doPostRequest(body, MetodoCreateDevice)

        return CreateDeviceModel("nombre")
    }
}
