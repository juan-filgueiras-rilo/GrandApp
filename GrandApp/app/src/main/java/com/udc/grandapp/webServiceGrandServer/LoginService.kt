package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class LoginService: GrandServer() {

    fun solicitudLogin(email: String, password: String): GenericModel{
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"email\": \"juan1@juan1.com\"\n" +
                    "    \"password\": \"juan1\",\n" +
                    "}")

            doPostRequest(body, MetodoLogin)
        }catch (e:Exception){
            e.printStackTrace()
        }


        return GenericModel("0", "", "")
    }
}