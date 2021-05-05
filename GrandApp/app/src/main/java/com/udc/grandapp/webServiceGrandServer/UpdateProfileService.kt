package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.UpdateProfileModel
import okhttp3.RequestBody

class UpdateProfileService: GrandServer() {

    fun updateProfile(): UpdateProfileModel {
        val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                "    \"userName\": \"juan1\",\n" +
                "    \"email\": \"juan@juan.com\"\n" +
                "}")

        doPutRequest(body, MetodoChangePass)

        return UpdateProfileModel("nombre")
    }
}