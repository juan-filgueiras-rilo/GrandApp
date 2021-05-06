package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class DeleteRoutineService: GrandServer() {

    fun deleteRoutine(id: String, token: String): GenericModel {
        try {
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

            doDeleteRequest(body, "")
        }catch (e:Exception){
            e.printStackTrace()
        }
        return GenericModel("0", "", "")
    }
}