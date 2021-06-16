package com.udc.grandapp.manager.transferObjects

class DatosCreateDevice(name: String, description: String, url: String, puerto: String, tipo: String) : DatosOperacionGeneric() {

    var name: String = name
    var description: String = description
    var url: String = url
    var puerto: String = puerto
    var tipo: String = tipo
}