package com.udc.grandapp.model

class DevicesModel() {
        lateinit var id: String
        lateinit var nombre: String
        lateinit var descripcion: String

        constructor(id: String, userName: String, descripcion: String) : this() {
                this.id = id
                this.nombre = userName
                this.descripcion = descripcion
        }

        companion object{
                fun Parse(json: String): List<DevicesModel>{
                        return  listOf(DevicesModel(), DevicesModel(), DevicesModel())
                }
        }
}
