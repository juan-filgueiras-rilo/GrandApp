package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class DeleteDeviceService: GrandServer() {

    fun deleteDevice(id : String, token: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"id\": \"1\"\n" +
                    "}")

            doDeleteRequest(body, "")
        }catch (e:Exception){
            e.printStackTrace()
        }

        return GenericModel("0", "", "")
    }
}