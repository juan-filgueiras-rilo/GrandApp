package com.udc.grandapp.manager.transferObjects

class DatosUpdateRoutine(id:String, name: String, description: String, userId:String,
                         deviceList: List<DatosCreateDevice>) : DatosOperacionGeneric() {
    var id: String = id
    var name: String = name
    var description: String = description
    var userId: String = userId
    var deviceList: List<DatosCreateDevice> = deviceList
}