package com.udc.grandapp.webServiceGrandServer;

import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.manager.transferObjects.DatosCreateRoutine
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

public class CreateRoutineService(): GrandServer() {

        fun createRoutine(datos: GenericManager.Companion.DatosThreaded, token: String) {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"name\": \"Rutina bien molona\",\n" +
                    "    \"description\": \"Esta rutina mola mogollon\"\n" +
                    "    \"userId\": \"1\"\n" +
                    "    \"deviceList\": \"[\"\n" +
                    "    {" +
                    "    \"name\": \"bombilla\",\n" +
                    "    \"description\": \"bombilla molona\"\n" +
                    "    \"userId\": \"1\"\n" +
                    "    }" +
                    "    ]" +
                    "}")

            doPostRequest(body, MetodoCreateRoutine, datos)
        }
}