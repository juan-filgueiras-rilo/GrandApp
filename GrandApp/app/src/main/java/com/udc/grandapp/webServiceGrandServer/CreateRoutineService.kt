package com.udc.grandapp.webServiceGrandServer;

import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

public class CreateRoutineService(): GrandServer() {

        fun createRoutine(name: String, desc: String, userId: String, deviceList: List<DevicesModel>, token: String): GenericModel {
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

            return GenericModel("0", "", "")
        }
}