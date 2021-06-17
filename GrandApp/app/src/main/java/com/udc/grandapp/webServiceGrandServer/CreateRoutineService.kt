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
            println(datosPeticion.minute)

            var dispositivosParseAux: String = "\"id\" : 1"

            var dispositivosParse: String = ""

            for (device in datosPeticion.deviceList) {
                dispositivosParse.plus( "    {" +
                        "    \"name\": \"" + device.nombre + "\",\n" +
                        "    \"description\": \"" + device.descripcion + "\"\n" +
                        "    \"userId\": \"" + device.id + "\"\n" +
                        "    },\n")

            }

            val daysBuilder = StringBuilder("")
            for (day in datosPeticion.dayList) {
                daysBuilder.append("\"$day\",")
            }

            val bodySB = StringBuilder("")
            bodySB.append("{\n")
            bodySB.append("    \"name\": \"" + datosPeticion.name + "\",\n")
            bodySB.append("    \"description\": \"" + datosPeticion.description + "\",\n")
            bodySB.append("    \"userId\": \"" + datosPeticion.userId + "\",\n")
            bodySB.append("    \"deviceList\": [\n")
            bodySB.append("        {\n")
            bodySB.append("            " + dispositivosParseAux + "\n")
            bodySB.append("        }\n")
            bodySB.append("    ],\n")
            bodySB.append("    \"dias\": [\n")
            bodySB.append("            " + daysBuilder.toString().dropLast(1) + " ],\n")
            bodySB.append("    \"hour\": \"" + datosPeticion.hour + "\",\n")
            bodySB.append("    \"minute\": \"" + datosPeticion.minute + "\"\n")
            bodySB.append("}")

            val body: RequestBody = RequestBody.create(mediaType, bodySB.toString());
            doPostRequest(body, MetodoCreateRoutine, datos, true, token)
        }
}