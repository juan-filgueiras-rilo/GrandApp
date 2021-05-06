package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class CreateDeviceService: GrandServer() {

    fun createDevice(name: String, description: String, userId: String, token: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"name\": \"bombilla\",\n" +
                    "    \"description\": \"bombilla molona\"\n" +
                    "    \"userId\": \"1\"\n" +
                    "}")

            doPostRequest(body, MetodoCreateDevice)

        }catch (e:Exception){
            e.printStackTrace()
        }
        return GenericModel("0", "", "")
    }
}
