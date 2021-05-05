package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel

class ChangePwdService: GrandServer() {

    fun cgangePwd(): GenericModel {
        return GenericModel("0", "")
    }
}