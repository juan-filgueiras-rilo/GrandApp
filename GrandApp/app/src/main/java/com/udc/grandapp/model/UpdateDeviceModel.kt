package com.udc.grandapp.model

class UpdateDeviceModel() {
    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var userid: String

    constructor(id: String, userName: String, descripcion: String, userid: String) : this() {
        this.id = id
        this.nombre = userName
        this.descripcion = descripcion
        this.userid = userid
    }

    companion object{
        fun Parse(json: String): UpdateDeviceModel{
            return UpdateDeviceModel("1", "bombilla", "bombilla", "user1")
        }
    }
}
