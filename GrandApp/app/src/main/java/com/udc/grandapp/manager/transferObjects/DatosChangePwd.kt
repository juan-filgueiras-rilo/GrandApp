package com.udc.grandapp.manager.transferObjects

class DatosChangePwd(name: String, description: String, userId:String) : DatosOperacionGeneric() {
    var oldPwd: String = name
    var newPwd: String = description
}