package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.manager.transferObjects.DatosCreateDevice
import com.udc.grandapp.manager.transferObjects.DatosDeleteDevice
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class DeleteDeviceService: GrandServer() {

    fun deleteDevice(datos: GenericManager.Companion.DatosThreaded, token: String): GenericModel {
        val datosPeticion = datos.mDatosOperaction as DatosDeleteDevice
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"id\": \"${datosPeticion.id}\"\n" +
                    "}")

            doDeleteRequest(body, MetodoDeleteDevice, datos)
        } catch (e:Exception){
            e.printStackTrace()
            return GenericModel("1", "failed to delete device", "")
        }
        return GenericModel("0", "", "")
    }
}