package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.manager.transferObjects.DatosCreateDevice
import com.udc.grandapp.manager.transferObjects.DatosUpdateDevice
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.UpdateDeviceModel
import okhttp3.RequestBody

class UpdateDeviceService(): GrandServer() {

    fun updateDevice(datos: GenericManager.Companion.DatosThreaded, token: String): GenericModel {
        val datosPeticion = datos.mDatosOperaction as DatosUpdateDevice
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"id\": \"${datosPeticion.id}\",\n" +
                    "    \"name\": \"${datosPeticion.name}\",\n" +
                    "    \"description\": \"${datosPeticion.description}\"\n" +
                    "}")

            doPutRequest(body, MetodoUpdateDevice, datos)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return GenericModel("0", "", "")
    }
}