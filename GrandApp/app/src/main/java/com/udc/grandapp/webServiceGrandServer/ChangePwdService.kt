package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel
import okhttp3.RequestBody

class ChangePwdService: GrandServer() {

    fun changePwd(oldpassword: String, newpassword: String): GenericModel {
        val body: RequestBody = RequestBody.create(mediaType, "{\n" +
                "    \"oldpassword\": \"juan1\",\n" +
                "    \"newpassword\": \"juan2\"\n" +
                "}")

        doPutRequest(body, MetodoChangePass)

        return GenericModel("0", "", "")
    }
}