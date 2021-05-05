package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class LoginService: GrandServer() {

    fun solicitudLogin(email: String, password: String): GenericModel{
        val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                "    \"email\": \"juan1@juan1.com\"\n" +
                "    \"password\": \"juan1\",\n" +
                "}")

        doPostRequest(body, MetodoLogin)

        return GenericModel("0", "", "")
    }
}