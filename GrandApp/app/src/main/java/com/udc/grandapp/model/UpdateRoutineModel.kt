package com.udc.grandapp.model

class UpdateRoutineModel() {
    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var userid: String
    lateinit var devices: List<DevicesModel>

    constructor(id: String, nombre: String, descripcion: String, userid: String, devices: List<DevicesModel>) : this() {
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.userid = userid
        this.devices = devices
    }

    companion object{
        fun Parse(json: String): UpdateRoutineModel{
            return UpdateRoutineModel("1", "rutina", "rutina", "user1", listOf(DevicesModel()))
        }
    }
}
