package com.udc.grandapp.manager.transferObjects

class DatosCreateRoutine(name: String, description: String, userId:String,
                         deviceList: List<DatosCreateDevice>) : DatosOperacionGeneric() {
    var name: String = name
    var description: String = description
    var userId: String = userId
    var deviceList: List<DatosCreateDevice> = deviceList
}