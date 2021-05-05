package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GetDevicesModel


class GetDevicesService: GrandServer() {

    fun getDevices(): GetDevicesModel {
        return GetDevicesModel("nombre")
    }
}