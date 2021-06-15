package com.udc.grandapp.model

class RoutinesModel() {
        lateinit var id: String
        lateinit var nombre: String
        lateinit var descripcion: String
        var hora: Int = 0
        var minuto: Int = 0
        lateinit var dias: String
        lateinit var devices: List<DevicesModel>

        constructor(id: String, userName: String, descripcion: String, devices: List<DevicesModel>) : this() {
                this.id = id
                this.nombre = userName
                this.descripcion = descripcion
                this.hora = hora
                this.minuto = minuto
                this.devices = devices
        }

        companion object{
                fun Parse(json: String): List<RoutinesModel>{
                        return  listOf(RoutinesModel(), RoutinesModel(), RoutinesModel())
                }
        }
}
