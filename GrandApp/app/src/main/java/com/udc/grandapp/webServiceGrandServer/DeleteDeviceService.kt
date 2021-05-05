package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel

class DeleteDeviceService: GrandServer() {

    fun deleteDevice(): GenericModel {
        return GenericModel("0", "")
    }
}