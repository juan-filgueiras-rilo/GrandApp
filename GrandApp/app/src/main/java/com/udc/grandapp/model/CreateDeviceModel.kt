package com.udc.grandapp.model

import org.json.JSONObject

class CreateDeviceModel() {
    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var userid: String

    constructor(id: String, nombre: String, descripcion: String, userId: String) : this() {
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.userid = userid
    }
    companion object{
        fun Parse(json: String): CreateDeviceModel {
            return CreateDeviceModel(
                    JSONObject(JSONObject(json)["device"].toString())["id"].toString(),
                    JSONObject(JSONObject(json)["device"].toString())["nombre"].toString(),
                    JSONObject(JSONObject(json)["device"].toString())["descripcion"].toString(),
                    JSONObject(JSONObject(json)["device"].toString())["userId"].toString(),
            )
        }
    }
}
