package com.udc.grandapp.webServiceGrandServer;

import com.udc.grandapp.model.CreateRoutineModel
import okhttp3.RequestBody

public class CreateRoutineService(): GrandServer() {

        fun createRoutine(): CreateRoutineModel {
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

            doPostRequest(body, MetodoCreateRoutine)

            return CreateRoutineModel("nombre")
        }
}