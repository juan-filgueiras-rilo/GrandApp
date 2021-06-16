package com.udc.grandapp.dbmodel

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import java.util.*

@Table(name = "Routine_day")
class Routine_day(IdRoutine: Integer, day: String): Model(){
    @Column(name = "IdRoutine")
    var IdRoutine: Integer = IdRoutine
    @Column(name = "day")
    var day: String = day
}