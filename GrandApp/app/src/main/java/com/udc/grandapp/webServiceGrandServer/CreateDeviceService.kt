package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.manager.transferObjects.DatosCreateDevice
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class CreateDeviceService: GrandServer() {

    fun createDevice(datos: GenericManager.Companion.DatosThreaded, token: String) {
        val datosPeticion: DatosCreateDevice = datos.mDatosOperaction as DatosCreateDevice
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    //"    \"id\": \"" + datosPeticion.id + "\",\n" +
                    "    \"name\": \"" + datosPeticion.name + "\",\n" +
                    "    \"description\": \""+ datosPeticion.description + "\",\n" +
                    "    \"url\": \""+ datosPeticion.url + "\"\n" +
                    "}")

            doPostRequest(body, MetodoCreateDevice, datos, true)

        } catch (e:Exception){
            e.printStackTrace()
        }
    }
}
