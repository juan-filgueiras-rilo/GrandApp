package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.model.GenericModel
import okhttp3.*
import java.io.IOException


class SingUpService(): GrandServer() {

    fun signup(datos: GenericManager.Companion.DatosThreaded) {
        val datosPeticion: DatosSingUp = datos.mDatosOperaction as DatosSingUp
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"userName\": \""+ datosPeticion.mUser + "\",\n" +
                    "    \"password\": \"" + datosPeticion.mPassword + "\",\n" +
                    "    \"email\": \"" + datosPeticion.mEmail + "\"\n" +
                    "}")

            doPostRequest(body, MetodoSignUp, datos)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}