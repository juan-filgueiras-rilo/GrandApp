package com.udc.grandapp.manager.transferObjects

import com.udc.grandapp.model.DevicesModel

class DatosCreateRoutine(name: String, description: String, userId:String, dayList: List<String>,
                         hour:String, deviceList: List<DevicesModel>) : DatosOperacionGeneric() {
    var name: String = name
    var description: String = description
    var dayList: List<String> = dayList
    var hour: String = hour
    var userId: String = userId
    var deviceList: List<DevicesModel> = deviceList
}