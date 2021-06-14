package com.udc.grandapp.model;

import com.activeandroid.annotation.Column

data class UserInfoModel(
    var email: String,
    var role: String,
    var token: String,
    var userId: String,
    var userName: String,
    var pwd: String
)
