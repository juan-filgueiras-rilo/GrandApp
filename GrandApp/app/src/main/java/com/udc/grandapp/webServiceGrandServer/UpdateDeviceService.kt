package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.UpdateDeviceModel

class UpdateDeviceService(): GrandServer() {

    fun updateDevice(): UpdateDeviceModel {
        return UpdateDeviceModel("token")
    }
}