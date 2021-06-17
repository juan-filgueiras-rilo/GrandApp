package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.manager.transferObjects.DatosDeleteRoutine
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class DeleteRoutineService: GrandServer() {

    fun deleteRoutine(datos: GenericManager.Companion.DatosThreaded, token: String): GenericModel {
        val datosPeticion = datos.mDatosOperaction as DatosDeleteRoutine
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"id\": \"${datosPeticion.id}\"\n"+
                    "}")
            doDeleteRequest(body, MetodoDeleteRoutine, datos, token)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return GenericModel("0", "", "")
    }
}