package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.manager.transferObjects.DatosLogin
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class LoginService: GrandServer() {

    fun solicitudLogin(datos: GenericManager.Companion.DatosThreaded){
        try {
            val datosPeticion: DatosLogin = datos.mDatosOperaction as DatosLogin
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"email\": \"juan1@juan1.com\"\n" +
                    "    \"password\": \"juan1\",\n" +
                    "}")

            doPostRequest(body, MetodoLogin, datos)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}