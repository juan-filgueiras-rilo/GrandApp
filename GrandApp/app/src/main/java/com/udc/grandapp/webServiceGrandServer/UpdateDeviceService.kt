package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.UpdateDeviceModel
import okhttp3.RequestBody

class UpdateDeviceService(): GrandServer() {

    fun updateDevice(id: String, name: String, description: String, userId: String, token: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"id\": \"1\",\n" +
                    "    \"name\": \"bombilla\",\n" +
                    "    \"description\": \"bombilla molona\"\n" +
                    "    \"userId\": \"1\"\n" +
                    "}")

            doPutRequest(body, MetodoCreateDevice)
        }catch (e:Exception){
            e.printStackTrace()
        }


        return GenericModel("0", "", "")
    }
}