package com.udc.grandapp.model

class SignUpLoginModel() {
        lateinit var id: String
        lateinit var userName: String
        lateinit var email: String
        lateinit var role: String
        lateinit var token: String

        constructor(id: String, userName: String, email:String, role: String, token: String) : this() {
                this.id = id
                this.userName = userName
                this.email = email
                this.role = role
                this.token = token
        }

        companion object{
                fun Parse(json: String): SignUpLoginModel{
                        return SignUpLoginModel("1", "laura", "laura@gmail.com", "USER", "token")
                }
        }
}
