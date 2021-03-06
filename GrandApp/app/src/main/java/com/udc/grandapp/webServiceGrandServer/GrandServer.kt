package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.model.GenericModel
import okhttp3.*
import java.io.IOException

open class GrandServer {

    private val namespace: String = "GrandApp"
    val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8");

    private val url: String = "https://iot-rangers-backend.herokuapp.com" // Esta es la URL de la maquina a la que conectarse
    //private val url: String = "http://192.168.0.11:8080" // Esta es la URL de la maquina a la que conectarse

    //Aquí irían los nombres de todos los métodos del web service
    //Users
    val MetodoLogin: String = "/users/login"
    val MetodoLoginFromServiceToken: String = "/users/loginFromServiceToken"
    val MetodoSignUp: String = "/users/signUp"
    val MetodoChangePass: String = "/users/changePassword"

    //Devices
    val MetodoGetDevicesByUserId: String = "/devices/getDevicesByUserId"
    val MetodoCreateDevice: String = "/devices/create"
    val MetodoUpdateDevice: String = "/devices"
    val MetodoDeleteDevice: String = "/devices"

    //Routines
    val MetodoGetRoutinesByUserId: String = "/routines/getRoutinesByUserId"
    val MetodoCreateRoutine: String = "/routines/create"
    val MetodoDeleteRoutine: String = "/routines"

    fun createPostRequest(body: RequestBody, metodo: String, useToken: Boolean, token: String): Request {
        val url = url.plus(metodo)
        val re: Request.Builder? = Request.Builder().post(body)
                .url(url)
                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
        if (useToken)
            return re!!
                    .header("Authorization", "Bearer " + token)
                    .build()
        else
            return re!!.build()
    }

    fun doPostRequest(body: RequestBody, metodo: String, datos: GenericManager.Companion.DatosThreaded, useToken: Boolean, token: String) {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")

        client.newCall(createPostRequest(body, metodo, useToken, token)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                var resultado: GenericModel? = null
                var resp: String? = null
                try {
                    resp = response.body()!!.string()
                } catch (e: Exception) {
                    resp = null
                }

                if (resp == null || resp.toLowerCase().contains("error"))
                    resultado = GenericModel("1", resp!!, "")
                else resultado = GenericModel("0", "", resp)
                datos.mResultado = resultado!!
                GenericManager.onPostExecute(datos)
            }
        })

    }

    fun createPutRequest(body: RequestBody, metodo: String, token: String): Request {
        val url = url.plus(metodo)
        return Request.Builder()
                .put(body)
                .url(url)
                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .header("Authorization", "Bearer " + token)
                .build()
    }

    fun doPutRequest(body: RequestBody, metodo: String, datos: GenericManager.Companion.DatosThreaded, token: String) {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")


        client.newCall(createPutRequest(body, metodo, token)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                var resultado: GenericModel? = null
                var resp: String? = null
                try {
                    resp = response.body()!!.string()
                } catch (e: Exception) {
                    resp = null
                }

                if (resp == null || resp.toLowerCase().contains("error"))
                    resultado = GenericModel("1", resp!!, "")
                else resultado = GenericModel("0", "", resp)
                datos.mResultado = resultado!!
                GenericManager.onPostExecute(datos)
            }
        })
    }

    fun createGetRequest(body: RequestBody, metodo: String, token: String): Request {
        val url = url.plus(metodo)
        return Request.Builder()
                .get()
                .url(url)
                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .header("Authorization", "Bearer " + token)
                .build()
    }

    fun doGetRequestS(body: RequestBody, metodo: String, token: String): GenericModel {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")

        client.newCall(createGetRequest(body, metodo, token)).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return GenericModel("0", "OK", response.body()!!.string())
        }
    }

    fun doGetRequest(datos: GenericManager.Companion.DatosThreaded, body: RequestBody, metodo: String, token: String) {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")

        client.newCall(createGetRequest(body, metodo, token)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                var resultado: GenericModel? = null
                var resp: String? = null
                try {
                    resp = response.body()!!.string()
                } catch (e: Exception) {
                    resp = null
                }

                if (resp == null || resp.toLowerCase().contains("error"))
                    resultado = GenericModel("1", resp!!, "")
                else resultado = GenericModel("0", "", resp)
                datos.mResultado = resultado!!
                GenericManager.onPostExecute(datos)
            }
        })
    }

    fun createDeleteRequest(body: RequestBody, metodo: String, token: String): Request {
        val url = url.plus(metodo)
        return Request.Builder()
                .delete(body)
                .url(url)
                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .header("Authorization", "Bearer " + token)
                .build()
    }

    fun doDeleteRequest(body: RequestBody, metodo: String, datos: GenericManager.Companion.DatosThreaded, token: String) {
        val client: OkHttpClient = OkHttpClient()
        val mediaType = MediaType.parse("application/json; charset=utf-8")

        client.newCall(createDeleteRequest(body, metodo, token)).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                var resultado: GenericModel? = null
                var resp: String? = null
                try {
                    resp = response.body()!!.string()
                } catch (e: Exception) {
                    resp = null
                }

                if (resp == null || resp.toLowerCase().contains("error"))
                    resultado = GenericModel("1", resp!!, "")
                else resultado = GenericModel("0", "", resp)
                datos.mResultado = resultado!!
                GenericManager.onPostExecute(datos)
            }
        })
    }
}