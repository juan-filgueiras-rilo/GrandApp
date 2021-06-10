package com.udc.grandapp.webServiceGrandServer;

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.manager.transferObjects.DatosCreateRoutine
import com.udc.grandapp.manager.transferObjects.DatosLogin
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

public class CreateRoutineService(): GrandServer() {

        fun createRoutine(datos: GenericManager.Companion.DatosThreaded, token: String) {
            val datosPeticion: DatosCreateRoutine = datos.mDatosOperaction as DatosCreateRoutine
            println(datosPeticion.userId)
            println(datosPeticion.dayList)
            println(datosPeticion.description)
            println(datosPeticion.deviceList)
            println(datosPeticion.name)
            println(datosPeticion.hour)

            var dispositivosParse: String = ""

            for (i in datosPeticion.deviceList) {
                dispositivosParse.plus( "    {" +
                        "    \"name\": \"" + i.nombre + "\",\n" +
                        "    \"description\": \"" + i.descripcion + "\"\n" +
                        "    \"userId\": \"" + i.id + "\"\n" +
                        "    },\n")

            }

            var daysParse: String = ""

            for (i in datosPeticion.dayList) {
                daysParse.plus( "    {" +
                        "    \"day\": \"" + i + "\",\n" +
                        "    },\n")

            }

            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"name\": \"" + datosPeticion.name + "\",\n" +
                    "    \"description\": \"" + datosPeticion.description + "\",\n" +
                    "    \"userId\": \"" + datosPeticion.userId + "\",\n" +
                    "    \"deviceList\": \"[\"\n" +
                    dispositivosParse +
                    "    ],\n" +
                    "    \"dayList\": \"[\"\n" +
                    daysParse +
                    "    ],\n" +
                    "    \"hour\": \"" + datosPeticion.hour + "\"\n" +
                    "}")

            doPostRequest(body, MetodoCreateRoutine, datos)
        }
}