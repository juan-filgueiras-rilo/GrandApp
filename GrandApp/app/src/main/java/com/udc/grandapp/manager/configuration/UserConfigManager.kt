package com.udc.grandapp.manager.configuration

import com.udc.grandapp.model.UserInfoModel
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.view.View
import com.udc.grandapp.model.DevicesModel

class UserConfigManager(context: Context) : SQLiteOpenHelper(context, "GrandApp", null, 5){

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
            userInfoModel = UserInfoModel(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6))
        }catch (e:Exception){
            userInfoModel = UserInfoModel("", "", "", "", "", "")
        }

        return userInfoModel
    }

    fun actualizarTokenBD(user:UserInfoModel, token: String){
        val db = this.writableDatabase
        val userID: String = user.userId
        db.rawQuery("UPDATE DBUser set token = $token WHERE userId = $userID", null)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getDevicesFromBD(): List<DevicesModel> {
        val retval : MutableList<DevicesModel> =  arrayListOf()
        try {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM DBDevice", null)
            res.moveToFirst()
            while (res.moveToNext()) {
                retval.add(DevicesModel(res.getString(0), res.getString(2),res.getString(5)))
            }
        }catch (e:Exception){
           e.printStackTrace()
        }
        return retval
    }

    fun deleteDevicesFromBD() {
        val db = this.writableDatabase
        db.rawQuery("DELETE FROM DBDevice WHERE id > 0", null)
    }

}