package com.udc.grandapp.webServiceGrandServer

 import com.udc.grandapp.model.UpdateRoutineModel
 import okhttp3.RequestBody

class UpdateRoutineService: GrandServer() {

    fun updateProfile(): UpdateRoutineModel {
        val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                "    \"id\": \"1\"\n" +
                "    \"name\": \"Aspirador Roomba\",\n" +
                "    \"description\": \"Roomba\"\n" +
                "    \"userId\": \"1\",\n" +
                "}")

        doPutRequest(body, "")

        return UpdateRoutineModel("nombre")
    }
}