package com.udc.grandapp.dbmodel

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import java.util.*

@Table(name = "DBRoutine")
class DBRoutine(routineId: String, nombre: String, fecha_inicio: Date, fecha_fin: Date, dispositivo: String, accion: String,
descripcion: String, userId: Integer, hour: Integer, minute: Integer): Model(){
    @Column(name = "routineId")
    var routineId: String = routineId
    @Column(name = "nombre")
    var nombre: String = nombre
    @Column(name = "fecha_inicio")
    var fecha_inicio: Date = fecha_inicio
    @Column(name = "fecha_fin")
    var fecha_fin: Date = fecha_fin
    @Column(name = "dispositivo")
    var dispositivo: String = dispositivo
    @Column(name = "accion")
    var accion: String = accion
    @Column(name = "descripcion")
    var descripcion: String = descripcion
    @Column(name = "userId")
    var userId: Integer = userId
    @Column(name = "hour")
    var hour: Integer = hour
    @Column(name = "minute")
    var minute: Integer = minute



}