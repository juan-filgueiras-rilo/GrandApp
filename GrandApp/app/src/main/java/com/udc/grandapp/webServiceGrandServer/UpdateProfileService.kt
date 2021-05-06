package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.UpdateProfileModel
import okhttp3.RequestBody

class UpdateProfileService: GrandServer() {

    fun updateProfile(userName: String, email: String, token: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"userName\": \"juan1\",\n" +
                    "    \"email\": \"juan@juan.com\"\n" +
                    "}")

            doPutRequest(body, MetodoChangePass)
        }catch (e:Exception){
            e.printStackTrace()
        }


        return GenericModel("", "", "")
    }
}