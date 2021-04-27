package com.udc.grandapp.dbmodel

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

@Table(name = "DBDevice")
class DBDevice(deviceId: String, nombre: String, tipo: String, protocolo: String): Model() {
    @Column(name = "deviceId")
    var deviceId: String = deviceId
    @Column(name = "nombre")
    var nombre: String = nombre
    @Column(name = "tipo")
    var tipo: String = tipo
    @Column(name = "protocolo")
    var protocolo: String = protocolo


}