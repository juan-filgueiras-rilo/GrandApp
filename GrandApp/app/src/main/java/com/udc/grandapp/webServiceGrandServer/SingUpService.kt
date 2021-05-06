package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.*


class SingUpService(): GrandServer() {

    fun signup(nombre: String, email: String, pwd: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"userName\": \"juan1\",\n" +
                    "    \"password\": \"juan1\",\n" +
                    "    \"email\": \"juan1@juan1.com\"\n" +
                    "}")

            doPostRequest(body, MetodoSignUp)

        }catch (e:Exception){
            e.printStackTrace()
        }

        return GenericModel("0", "", "")
    }
}
/*
    fun signup(nombre: String, email: String, pwd: String): SignUpModel {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")
        val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                "    \"userName\": \"juan\",\n" +
                "    \"password\": \"juan\",\n" +
                "    \"email\": \"juan@juan.com\"\n" +
                "}")
        val request = Request.Builder()
            .post(body)
            .url("http://192.168.56.1:8080/users/signUp")
            .header("Connection", "keep-alive")
            .header("Accept", "/*")
            .header("Content-Type", "application/json")
            .header("Cache-Control", "no-cache")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) = println(
                response.body()?.string()
            )
        })
        return GenericModel("", "", "")
    }
    */