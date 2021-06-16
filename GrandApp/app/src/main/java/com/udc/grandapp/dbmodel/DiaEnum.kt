package com.udc.grandapp.dbmodel

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import java.util.*

@Table(name = "DiaEnum")
class DiaEnum(day: String): Model(){
    @Column(name = "day")
    var day: String = day

}