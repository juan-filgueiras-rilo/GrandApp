package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.LoginModel

class MetodoLogin: GrandServer() {

    fun solicitudLogin(): LoginModel{
        return LoginModel()
    }
}