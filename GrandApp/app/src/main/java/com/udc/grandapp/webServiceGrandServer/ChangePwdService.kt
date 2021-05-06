package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class ChangePwdService: GrandServer() {

    fun changePwd(oldpassword: String, newpassword: String, token: String): GenericModel {
        try {
            val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                    "    \"oldpassword\": \"juan1\",\n" +
                    "    \"newpassword\": \"juan2\"\n" +
                    "}")

            doPutRequest(body, MetodoChangePass)
        }catch (e:Exception){
            e.printStackTrace()
        }

        return GenericModel("0", "", "")
    }
}