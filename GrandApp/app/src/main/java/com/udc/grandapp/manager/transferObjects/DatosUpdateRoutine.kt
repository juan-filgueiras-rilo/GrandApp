package com.udc.grandapp.manager.transferObjects

import com.udc.grandapp.model.DevicesModel

class DatosUpdateRoutine(id:String, name: String, description: String, userId:String,
                         deviceList: List<DevicesModel>) : DatosOperacionGeneric() {
    var id: String = id
    var name: String = name
    var description: String = description
    var userId: String = userId
    var deviceList: List<DevicesModel> = deviceList
}