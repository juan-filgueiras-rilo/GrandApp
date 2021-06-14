package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class GetDevicesService: GrandServer() {

    fun getDevices(datos: GenericManager.Companion.DatosThreaded, userId: String, token: String) {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "")
            doGetRequest(datos, body, MetodoGetDevicesByUserId, token)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}