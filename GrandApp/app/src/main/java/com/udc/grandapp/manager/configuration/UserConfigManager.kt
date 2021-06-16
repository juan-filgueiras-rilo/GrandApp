package com.udc.grandapp.manager.configuration

import android.content.ContentValues
import android.app.Activity
import com.udc.grandapp.model.UserInfoModel
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.view.View
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.RoutinesModel

class UserConfigManager(context: Context) : SQLiteOpenHelper(context, "GrandApp", null, 6){

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

    fun actualizarTokenBD(user:UserInfoModel, token: String, context: Activity){
        val db = this.writableDatabase
        val userID: String = user.userId
        db.rawQuery("UPDATE DBUser set token = $token WHERE userId = $userID", null)
        reiniciarInfoPersistente(context)
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
                    put("Id", device.id)
                    put("nombre", device.nombre)
                    put("descripcion", device.descripcion)
                    put("url", device.url)
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
                retval.add(DevicesModel(res.getString(0), res.getString(3),res.getString(1), res.getString(6)))
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

    fun getDevicesByRoutineFromBD(): List<DevicesModel> {
        val retval : MutableList<DevicesModel> =  arrayListOf()

        return retval
    }
/*
    fun insertarRoutinesBBDD(routines : List<RoutinesModel>) {
        val db = this.writableDatabase
        try {
            for (routine in routines) {
                val values = ContentValues().apply {
                    put("routineId", routine.id)
                    put("nombre", routine.nombre)
                    put("descripcion", routine.descripcion)
                    put("hora", routine.hora)
                    put("minuto", routine.minuto)
                }
                val newRowId = db?.insert("DBRoutine", null, values)
            }
        } catch (e : java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getRoutinesFromBD(): List<RoutinesModel> {
        val retval : MutableList<RoutinesModel> =  arrayListOf()
        try {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM DBRoutine", null)
            while (res.moveToNext()) {
                retval.add(RoutinesModel(res.getString(0), res.getString(2),res.getString(5), getDevicesByRoutineFromBD()))
            }
        } catch (e:Exception){
            e.printStackTrace()
        }
        return retval
    }

    fun deleteRoutinesFromBD() {
        try {
            val db = this.writableDatabase
            db.delete("DBRoutine","id > 0", null)
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

 */
}