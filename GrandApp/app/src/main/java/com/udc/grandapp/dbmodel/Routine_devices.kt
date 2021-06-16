package com.udc.grandapp.dbmodel

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import java.util.*

@Table(name = "Routine_devices")
class Routine_devices(IdRoutine: Integer, IdDevice: Integer): Model(){
    @Column(name = "IdRoutine")
    var IdRoutine: Integer = IdRoutine
    @Column(name = "IdDevice")
    var IdDevice: Integer = IdDevice
}