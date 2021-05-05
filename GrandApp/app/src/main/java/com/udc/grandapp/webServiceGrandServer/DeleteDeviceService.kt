package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class DeleteDeviceService: GrandServer() {

    fun deleteDevice(id : String, name: String, description: String, userId: String): GenericModel {
        val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                "    \"id\": \"1\"\n" +
                "    \"name\": \"Aspirador Roomba\",\n" +
                "    \"description\": \"Roomba\"\n" +
                "    \"userId\": \"1\",\n" +
                "}")

        doDeleteRequest(body, "")

        return GenericModel("0", "", "")
    }
}