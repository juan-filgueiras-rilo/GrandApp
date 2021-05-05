package com.udc.grandapp.webServiceGrandServer

import okhttp3.*
import java.io.IOException

open class GrandServer {

    private val namespace: String = "GrandApp"
    val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8");

    private val url: String = "http://192.168.56.1:8080" // Esta es la URL de la maquina a la que conectarse

    //Aquí irían los nombres de todos los métodos del web service
    //Users
    val MetodoLogin: String = "/users/login"
    val MetodoLoginFromServiceToken: String = "/users/loginFromServiceToken"
    val MetodoSignUp: String = "/users/signUp"
    val MetodoChangePass: String = "/users/changePassword"

    //Devices
    val MetodoGetDevicesByUserId: String = "/devices/getDevicesByUserId"
    val MetodoCreateDevice: String = "/devices/create"

    //Routines
    val MetodoGetRoutinesByUserId: String = "/routines/getRoutinesByUserId"
    val MetodoCreateRoutine: String = "/routines/create"

    fun createPostRequest(body: RequestBody, metodo:String): Request {
        val url = url.plus(metodo)
        return Request.Builder()
            .post(body)
            .url(url)
            .header("Connection", "keep-alive")
            .header("Accept", "*/*")
            .header("Content-Type", "application/json")
            .header("Cache-Control", "no-cache")
            .build()
    }

    fun doPostRequest(body: RequestBody, metodo: String) {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")

        client.newCall(createPostRequest(body, metodo)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) = println(
                response.body()?.string()
            )
        })
    }

    fun createPutRequest(body: RequestBody, metodo:String): Request {
        val url = url.plus(metodo)
        return Request.Builder()
            .put(body)
            .url(url)
            .header("Connection", "keep-alive")
            .header("Accept", "*/*")
            .header("Content-Type", "application/json")
            .header("Cache-Control", "no-cache")
            .build()
    }

    fun doPutRequest(body: RequestBody, metodo: String) {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")

        client.newCall(createPutRequest(body, metodo)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) = println(
                response.body()?.string()
            )
        })
    }

    fun createGetRequest(body: RequestBody, metodo:String): Request {
        val url = url.plus(metodo)
        return Request.Builder()
            .post(body)
            .url(url)
            .header("Connection", "keep-alive")
            .header("Accept", "*/*")
            .header("Content-Type", "application/json")
            .header("Cache-Control", "no-cache")
            .build()
    }

    fun doGetRequest(body: RequestBody, metodo: String) {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")

        client.newCall(createGetRequest(body, metodo)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) = println(
                response.body()?.string()
            )
        })
    }

    fun createDeleteRequest(body: RequestBody, metodo:String): Request {
        val url = url.plus(metodo)
        return Request.Builder()
            .delete(body)
            .url(url)
            .header("Connection", "keep-alive")
            .header("Accept", "*/*")
            .header("Content-Type", "application/json")
            .header("Cache-Control", "no-cache")
            .build()
    }

    fun doDeleteRequest(body: RequestBody, metodo: String) {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")

        client.newCall(createDeleteRequest(body, metodo)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) = println(
                response.body()?.string()
            )
        })
    }
}