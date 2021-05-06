package com.udc.grandapp.manager.configuration

import com.udc.grandapp.model.UserInfoModel
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class UserConfigManager(context: Context) : SQLiteOpenHelper(context, "GrandApp", null, 1){

    companion object {
        var infoPersistente: UserInfoModel? = null
        fun getUserInfoPersistente(context: Context): UserInfoModel?{
            if (infoPersistente == null)
                infoPersistente = UserConfigManager(context).getUserInfoFromBD()
            return infoPersistente as UserInfoModel
        }

        fun reiniciarInfoPersistente(context: Context){
            infoPersistente = UserConfigManager(context).getUserInfoFromBD()
        }
    }

    fun getUserInfoFromBD(): UserInfoModel? {
        var userInfoModel: UserInfoModel?
        try {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM DBUser", null)
            userInfoModel = UserInfoModel(res.getString(0), res.getString(1))
        }catch (e:Exception){
            userInfoModel = UserInfoModel("", "")
        }

        return userInfoModel
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}