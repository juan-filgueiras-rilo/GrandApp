package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.SignUpModel
import okhttp3.*
import java.io.*


class SingUpService(): GrandServer() {

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
            .url("http://localhost:8080/users/signUp")
            .header("Connection", "close")
            .header("Accept", "application/json")
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
        return SignUpModel("")
    }
}