package com.udc.grandapp.model

import android.content.ContentValues.TAG
import android.util.Log
import org.json.JSONObject

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
                        Log.e(TAG, json)
                        return SignUpLoginModel(
                                JSONObject(JSONObject(json)["user"].toString())["id"].toString(),
                                JSONObject(JSONObject(json)["user"].toString())["userName"].toString(),
                                JSONObject(JSONObject(json)["user"].toString())["email"].toString(),
                                JSONObject(JSONObject(json)["user"].toString())["role"].toString(),
                                JSONObject(json)["serviceToken"].toString()
                        )
                }
        }
}
