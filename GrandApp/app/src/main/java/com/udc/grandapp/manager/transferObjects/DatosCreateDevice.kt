package com.udc.grandapp.manager.transferObjects

class DatosCreateDevice(name: String, description: String, url: String, userId:String) : DatosOperacionGeneric() {

    var name: String = name
    var description: String = description
    var url: String = url
    var userId: String = userId
}