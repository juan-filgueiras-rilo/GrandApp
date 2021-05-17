package com.udc.grandapp.dbmodel

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table


@Table(name = "DBUser")
open class DBUser(userId: String, token: String, userName: String, email: String, role: String) : Model() {
    @Column(name = "userId")
    var userId: String = userId
    @Column(name = "token")
    var token: String = token
    @Column(name = "userName")
    var userName: String = userName
    @Column(name = "email")
    var email: String = email
    @Column(name = "role")
    var role: String = role
}
