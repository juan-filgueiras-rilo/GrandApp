package com.udc.grandapp.webServiceGrandServer

 import com.udc.grandapp.model.DevicesModel
 import com.udc.grandapp.model.GenericModel
 import com.udc.grandapp.model.UpdateRoutineModel
 import okhttp3.RequestBody

class UpdateRoutineService: GrandServer() {

    fun updateRoutine(id: String, name: String, description: String, userId: String, deviceLIst: List<DevicesModel> , token: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"id\": \"1\"\n" +
                    "    \"name\": \"Aspirador Roomba\",\n" +
                    "    \"description\": \"Roomba\"\n" +
                    "    \"userId\": \"1\",\n" +
                    "}")

            doPutRequest(body, "")
        }catch (e:Exception){
            e.printStackTrace()
        }


        return GenericModel("0", "", "")
    }
}