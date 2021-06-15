package com.udc.grandapp.manager.configuration

import android.content.ContentValues
import com.udc.grandapp.model.UserInfoModel
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.view.View
import com.udc.grandapp.model.DevicesModel

class UserConfigManager(context: Context) : SQLiteOpenHelper(context, "GrandApp", null, 4){

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

    fun insertarDeviceBBDD(devices : List<DevicesModel>) {
        val db = this.writableDatabase
        // UserConfigManager(this).deleteDevicesFromBD() //Borramos lo que haya e insertamos de nuevo
        try {
            for (device in devices) {
                val values = ContentValues().apply {
                    put("deviceId", device.id)
                    put("nombre", device.nombre)
                    put("descripcion", device.descripcion)
                    put("tipo", "")
                    put("protocolo", "")
                }
                val newRowId = db?.insert("DBDevice", null, values)
            }
        } catch (e : java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getDevicesFromBD(): List<DevicesModel> {
        val retval : MutableList<DevicesModel> =  arrayListOf()
        try {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM DBDevice", null)
            while (res.moveToNext()) {
                retval.add(DevicesModel(res.getString(0), res.getString(2),res.getString(5)))
            }
        } catch (e:Exception){
           e.printStackTrace()
        }
        return retval
    }

    fun deleteDevicesFromBD() {
        try {
            val db = this.writableDatabase
            db.delete("DBDevice","id > 0", null)
        } catch (e:Exception){
            e.printStackTrace()
        }

    }

}