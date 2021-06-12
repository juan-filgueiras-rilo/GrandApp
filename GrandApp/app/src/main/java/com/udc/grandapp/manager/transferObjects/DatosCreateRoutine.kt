package com.udc.grandapp.manager.transferObjects

import com.udc.grandapp.model.DevicesModel

class DatosCreateRoutine(name: String, description: String, userId:String, dayList: List<String>,
                         hour:Int, minute:Int, deviceList: List<DevicesModel>) : DatosOperacionGeneric() {
    var name: String = name
    var description: String = description
    var dayList: List<String> = dayList
    var hour: Int = hour
    var minute: Int = minute
    var userId: String = userId
    var deviceList: List<DevicesModel> = deviceList
}