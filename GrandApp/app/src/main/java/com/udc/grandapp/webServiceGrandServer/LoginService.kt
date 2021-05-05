package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.LoginModel

class LoginService: GrandServer() {

    fun solicitudLogin(): LoginModel{
        return LoginModel()
    }
}