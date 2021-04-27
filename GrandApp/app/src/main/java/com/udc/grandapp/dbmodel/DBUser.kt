package com.udc.grandapp.dbmodel

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table


@Table(name = "DBUser")
open class DBUser(userId: String, token: String) : Model() {
    @Column(name = "userId")
    var userId: String = userId
    @Column(name = "token")
    var token: String = token
}
