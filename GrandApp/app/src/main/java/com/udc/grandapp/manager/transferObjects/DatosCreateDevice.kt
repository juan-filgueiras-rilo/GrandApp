package com.udc.grandapp.manager.transferObjects

class DatosCreateDevice(name: String, url: String, description: String, userId:Long) : DatosOperacionGeneric() {
    var name: String = name
    var url: String = url
    var description: String = description
    var userId: Long = userId
}