package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.CreateDeviceModel

class CreateDeviceService: GrandServer() {

    fun createDevice(): CreateDeviceModel {
        return CreateDeviceModel("nombre")
    }
}
