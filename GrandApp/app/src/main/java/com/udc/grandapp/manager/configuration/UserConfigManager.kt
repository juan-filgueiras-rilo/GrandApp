package com.udc.grandapp.manager.configuration

import android.content.ContentValues
import android.app.Activity
import com.udc.grandapp.model.UserInfoModel
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.view.View
import com.udc.grandapp.model.*

class UserConfigManager(context: Context) : SQLiteOpenHelper(context, "GrandApp", null, 6) {

    companion object {
        var infoPersistente: UserInfoModel? = null
        fun getUserInfoPersistente(context: Context): UserInfoModel? {
            if (infoPersistente == null)
                infoPersistente = UserConfigManager(context).getUserInfoFromBD()
            return infoPersistente as UserInfoModel
        }

        fun reiniciarInfoPersistente(context: Context) {
            infoPersistente = UserConfigManager(context).getUserInfoFromBD()
        }

    }

    fun getUniqueIPs(): MutableList<String> {
        val retval: MutableList<String> = arrayListOf()
        try {
            val db = this.writableDatabase
            val result = db.rawQuery("SELECT DISTINCT url FROM DBDevice", null)
            while (result.moveToNext()) {
                retval.add(result.getString(result.getColumnIndex("url")))
            }
            result.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retval
    }

    fun deleteUsersBD() {
        try {
            val db = this.writableDatabase
            db.delete("DBUser", "id > 0", null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insertarUserBD(singUp: SignUpLoginModel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("userId", singUp.id)
            put("token", singUp.token)
            put("userName", singUp.userName)
            put("email", singUp.email)
            put("role", singUp.role)
        }

        val newRowId = db?.insert("DBUser", null, values)
    }

    fun getUserInfoFromBD(): UserInfoModel? {
        var userInfoModel: UserInfoModel?
        userInfoModel = try {
            val db = this.writableDatabase
            val result = db.rawQuery("SELECT * FROM DBUser", null)
            if (result.moveToFirst())
            //userInfoModel = UserInfoModel(res.getString(1), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(2))
                UserInfoModel(result.getString(result.getColumnIndex("email")), result.getString(result.getColumnIndex("role")), result.getString(result.getColumnIndex("token")), result.getString(result.getColumnIndex("userId")), result.getString(result.getColumnIndex("userName")), "")//res.getString(6))
            else UserInfoModel("", "", "", "", "", "")
        } catch (e: Exception) {
            e.printStackTrace()
            UserInfoModel("", "", "", "", "", "")
        }

        return userInfoModel
    }

    fun actualizarTokenBD(user: UserInfoModel, token: String, context: Activity) {
        try {
            val db = this.writableDatabase
            val userID: String = user.userId
            db.execSQL("UPDATE DBUser set token = '$token' WHERE userId = '$userID'")
            reiniciarInfoPersistente(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun actualizarUserName(user: UserInfoModel, userName: String, context: Activity) {
        try {
            val db = this.writableDatabase
            val userID: String = user.userId
            db.execSQL("UPDATE DBUser set userName = '$userName' WHERE userId = '$userID'")
            reiniciarInfoPersistente(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun actualizarDevice(device: UpdateDeviceModel, context: Activity) {
        try {
            val db = this.writableDatabase
            db.execSQL("UPDATE DBDevice set nombre = '${device.nombre}', descripcion = '${device.descripcion}' WHERE Id = '${device.id}'")
            reiniciarInfoPersistente(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertarDeviceBBDD(devices: List<DevicesModel>) {
        val db = this.writableDatabase
        // UserConfigManager(this).deleteDevicesFromBD() //Borramos lo que haya e insertamos de nuevo
        try {
            for (device in devices) {
                val values = ContentValues().apply {
                    put("id", device.id)
                    put("nombre", device.nombre)
                    put("descripcion", device.descripcion)
                    put("url", device.url)
                    put("tipo", device.tipo)
                    put("protocolo", device.puerto)
                }
                val newRowId = db?.insert("DBDevice", null, values)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getDevicesFromBD(): List<DevicesModel> {
        val retval: MutableList<DevicesModel> = arrayListOf()
        try {
            val db = this.writableDatabase
            val result = db.rawQuery("SELECT * FROM DBDevice", null)
            while (result.moveToNext()) {
                retval.add(DevicesModel(
                        result.getString(result.getColumnIndex("Id")),
                        result.getString(result.getColumnIndex("nombre")),
                        result.getString(result.getColumnIndex("descripcion")),
                        result.getString(result.getColumnIndex("url")),
                        result.getString(result.getColumnIndex("protocolo")),
                        result.getString(result.getColumnIndex("tipo"))))
            }
            result.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retval
    }

    fun deleteDevicesFromBD() {
        try {
            val db = this.writableDatabase
            db.delete("Routine_devices", "IdDevice > 0", null)
            db.delete("DBDevice", "id > 0", null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getDevicesByRoutineFromBD(idRoutine: Int): List<DevicesModel> {
        val retval: MutableList<DevicesModel> = arrayListOf()

        try {
            val db = this.writableDatabase
            val result = db.rawQuery("SELECT * FROM DBDevice d join Routine_devices rd on d.id = rd.IdDevice WHERE idRoutine = ?", arrayOf(idRoutine.toString()))
            while (result.moveToNext()) {
                retval.add(DevicesModel(
                        result.getString(result.getColumnIndex("Id")),
                        result.getString(result.getColumnIndex("nombre")),
                        result.getString(result.getColumnIndex("descripcion")),
                        result.getString(result.getColumnIndex("url")),
                        result.getString(result.getColumnIndex("protocolo")),
                        result.getString(result.getColumnIndex("tipo"))))
            }
            result.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retval
    }

    fun getDiasByRoutineFromBD(idRoutine: Int): List<String> {
        val retval: MutableList<String> = arrayListOf()
        try {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM Routine_day WHERE idRoutine = ?", arrayOf(idRoutine.toString()))
            while (res.moveToNext()) {
                retval.add(res.getString(res.getColumnIndex("day")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retval
    }

    fun insertarRoutinesBBDD(routines: List<RoutinesModel>) {
        val db = this.writableDatabase
        try {
            for (routine in routines) {
                val values = ContentValues().apply {
                    put("routineId", routine.id)
                    put("nombre", routine.nombre)
                    put("descripcion", routine.descripcion)
                    put("hour", routine.hora)
                    put("minute", routine.minuto)
                }
                val newRowId = db?.insert("DBRoutine", null, values)

                for(device in routine.devices) {
                    insertRoutineDevices(routine.id.toInt(), device.id.toInt())
                }

                for(dia in routine.dias) {
                    insertRoutineDay(routine.id.toInt(),dia)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getRoutinesFromBD(): List<RoutinesModel> {
        val retval: MutableList<RoutinesModel> = arrayListOf()
        try {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM DBRoutine", null)
            while (res.moveToNext()) {
                retval.add(RoutinesModel(
                        res.getString(res.getColumnIndex("routineId")),
                        res.getString(res.getColumnIndex("nombre")),
                        res.getString(res.getColumnIndex("descripcion")),
                        res.getInt(res.getColumnIndex("hour")),
                        res.getInt(res.getColumnIndex("minute")),
                        getDevicesByRoutineFromBD(res.getString(res.getColumnIndex("routineId")).toInt()),
                        getDiasByRoutineFromBD(res.getString(res.getColumnIndex("routineId")).toInt())))
            }
            res.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retval
    }

    fun deleteRoutinesFromBD() {
        try {
            val db = this.writableDatabase
            db.delete("Routine_devices", "IdRoutine > 0", null)
            db.delete("Routine_day", "IdRoutine > 0", null)
            db.delete("DBRoutine", "id > 0", null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insertRoutineDevices(idRoutine: Int, idDevice: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("IdRoutine", idRoutine)
            put("IdDevice", idDevice)
        }
        val newRowId = db?.insert("Routine_devices", null, values)
    }

    fun insertRoutineDay(idRoutine: Int, day: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("IdRoutine", idRoutine)
            put("day", day)
        }
        val newRowId = db?.insert("Routine_day", null, values)
    }

}