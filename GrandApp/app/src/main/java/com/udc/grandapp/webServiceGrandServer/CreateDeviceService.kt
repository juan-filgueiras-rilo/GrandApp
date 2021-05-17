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
                    "    \"name\": \"bombilla\",\n" +
                    "    \"description\": \"bombilla molona\"\n" +
                    "    \"userId\": \"1\"\n" +
                    "}")

            doPostRequest(body, MetodoCreateDevice, datos)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}
