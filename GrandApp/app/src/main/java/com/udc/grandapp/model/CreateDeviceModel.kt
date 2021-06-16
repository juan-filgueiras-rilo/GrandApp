package com.udc.grandapp.model

import org.json.JSONObject

class CreateDeviceModel() {
    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var url: String

    constructor(id: String, nombre: String, descripcion: String, url: String) : this() {
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.url = url
    }
    companion object{
        fun Parse(json: String): CreateDeviceModel {
            return CreateDeviceModel(
                    JSONObject(json)["id"].toString(),
                    JSONObject(json)["name"].toString(),
                    JSONObject(json)["description"].toString(),
                    JSONObject(json)["url"].toString(),
            )
        }
    }
}
