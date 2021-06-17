package com.udc.grandapp.services

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.udc.grandapp.R
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.webServiceHandleDevice.HandleDeviceService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EnableRoutinesReceive  : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        var currentDateTime= LocalDateTime.now()
        var hour:String = currentDateTime.format(DateTimeFormatter.ofPattern("HH"))
        var minute:String = currentDateTime.format(DateTimeFormatter.ofPattern("mm"))
        var day:String = currentDateTime.format(DateTimeFormatter.ofPattern("EEEE"))


        val db = UserConfigManager(context).writableDatabase

        /*****************************PRUEBAS***********************************/
        /*val values = ContentValues().apply {
            put("userId", "1")
            put("token","1")
            put("userName", "nombre")
            put("email", "email")
            put("role", "role")
        }
        val values1 = ContentValues().apply {
            put("descripcion", "descripcion")
            put("userId","1")
            put("hour", "12")
            put("minute", "00")
            put("nombre", "hila")
            put("routineId", "1")
        }
        val values2 = ContentValues().apply {
            put("descripcion", "descripcion")
            put("userId","1")
            put("hour", "13")
            put("minute", "00")
            put("nombre", "hila")
            put("routineId", "2")
        }
        val values3 = ContentValues().apply {
            put("descripcion", "descripcion")
            put("userId","1")
            put("hour", "12")
            put("minute", "00")
            put("nombre", "hila")
            put("routineId", "3")
        }
        val values4 = ContentValues().apply {
            put("descripcion", "descripcion")
            put("userId","1")
            put("hour", "13")
            put("minute", "00")
            put("nombre", "hila")
            put("routineId", "4")
        }


        val values11 = ContentValues().apply {
            put("IdRoutine", "1")
            put("day","WEDNESDAY")
        }

        val values12 = ContentValues().apply {
            put("IdRoutine", "2")
            put("day","WEDNESDAY")
        }
        val values13 = ContentValues().apply {
            put("IdRoutine", "3")
            put("day","TUESDAY")
        }
        val values14 = ContentValues().apply {
            put("IdRoutine", "4")
            put("day","TUESDAY")
        }

        val values16 = ContentValues().apply {
            put("Id", "1")
            put("protocolo","puerto")
            put("tipo","tipo")
            put("url","url")
        }

        val values17 = ContentValues().apply {
            put("Id", "2")
            put("protocolo","puerto")
            put("tipo","tipo")
            put("url","url")
        }
        val values18 = ContentValues().apply {
            put("Id", "3")
            put("protocolo","puerto")
            put("tipo","tipo")
            put("url","url")
        }
        val values19 = ContentValues().apply {
            put("Id", "4")
            put("protocolo","puerto")
            put("tipo","tipo")
            put("url","url")
        }

        val values20 = ContentValues().apply {
            put("IdDevice", "1")
            put("IdRoutine","1")
        }

        val values21 = ContentValues().apply {
            put("IdDevice", "2")
            put("IdRoutine","2")
        }
        val values22 = ContentValues().apply {
            put("IdDevice", "3")
            put("IdRoutine","2")
        }
        val values23 = ContentValues().apply {
            put("IdDevice", "4")
            put("IdRoutine","3")
        }

        val newRowId = db?.insert("DBUser", null, values)

        val newRowId1 = db?.insert("DBRoutine", null, values1)
        val newRowId2 = db?.insert("DBRoutine", null, values2)
        val newRowId3 = db?.insert("DBRoutine", null, values3)
        val newRowId4 = db?.insert("DBRoutine", null, values4)
        val newRowId5 = db?.insert("Routine_day", null, values11)
        val newRowId6 = db?.insert("Routine_day", null, values12)
        val newRowId7 = db?.insert("Routine_day", null, values13)
        val newRowId8 = db?.insert("Routine_day", null, values14)
        val newRowId9 = db?.insert("DBDevice", null, values16)
        val newRowId10 = db?.insert("DBDevice", null, values17)
        val newRowId11 = db?.insert("DBDevice", null, values18)
        val newRowId12 = db?.insert("DBDevice", null, values19)
        val newRowId13 = db?.insert("Routine_devices", null, values20)
        val newRowId14 = db?.insert("Routine_devices", null, values21)
        val newRowId15 = db?.insert("Routine_devices", null, values22)
        val newRowId16 = db?.insert("Routine_devices", null, values23)

        day = "WEDNESDAY"
        hour = "12"
        minute = "00"*/

        var result = db!!.rawQuery("SELECT IdRoutine FROM Routine_day WHERE day = \"" + day + "\"", null)
        var toExecute: MutableList<String> = mutableListOf()
        if(result.moveToFirst()) {
            while (!result.isAfterLast) {
                if (result.getString(result.getColumnIndex("IdRoutine")) != null) {
                    var cursor = db!!.rawQuery(
                        "SELECT routineId FROM DBRoutine WHERE hour = \"" +
                                hour + "\"" + "AND minute = \"" + minute + "\"" + "AND routineId = \"" +
                                result.getString(result.getColumnIndex("IdRoutine")) + "\"", null
                    )
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast) {
                            if (cursor.getString(cursor.getColumnIndex("routineId")) != null) {
                                //println("Tengo la rutina " + cursor.getString(cursor.getColumnIndex("routineId")))
                                var cursor2 = db!!.rawQuery(
                                    "SELECT IdDevice FROM Routine_devices WHERE IdRoutine = \"" +
                                            cursor.getString(cursor.getColumnIndex("routineId")) + "\"", null
                                )
                                if (cursor2.moveToFirst()) {
                                    while (!cursor2.isAfterLast) {
                                        if (cursor2.getString(cursor2.getColumnIndex("IdDevice")) != null) {
                                            var cursor3 = db!!.rawQuery(
                                                "SELECT * FROM DBDevice WHERE Id = \"" +
                                                        cursor2.getString(cursor2.getColumnIndex("IdDevice")) + "\"", null
                                            )

                                            if (cursor3.moveToFirst()) {
                                                var j=0
                                                while (!cursor3.isAfterLast) {
                                                    if (cursor3.getString(cursor3.getColumnIndex("Id")) != null) {
                                                        println(cursor3.getString(cursor3.getColumnIndex("Id")))
                                                        println("veces" + j)
                                                        j++
                                                    val scope = CoroutineScope(Dispatchers.IO)
                                                        scope.launch {
                                                            val status = HandleDeviceService(cursor3.getString(cursor3.getColumnIndex("url")),
                                                                cursor3.getString(cursor3.getColumnIndex("puerto")).toInt(),
                                                                cursor3.getString(cursor3.getColumnIndex("tipo"))).queryDevice()
                                                            if(status == "on") {
                                                                HandleDeviceService(cursor3.getString(cursor3.getColumnIndex("url")),
                                                                    cursor3.getString(cursor3.getColumnIndex("puerto")).toInt(),
                                                                    cursor3.getString(cursor3.getColumnIndex("tipo"))).powerOffDevice()
                                                            } else {
                                                                HandleDeviceService(cursor3.getString(cursor3.getColumnIndex("url")),
                                                                    cursor3.getString(cursor3.getColumnIndex("puerto")).toInt(),
                                                                    cursor3.getString(cursor3.getColumnIndex("tipo"))).powerOnDevice()
                                                            }
                                                        }
                                                    }
                                                    cursor3.moveToNext()
                                                }
                                            }
                                            cursor3.close()
                                        }
                                        cursor2.moveToNext()
                                    }
                                }
                                cursor2.close()
                            }
                            cursor.moveToNext()
                        }
                    }
                    cursor.close()
                }
                result.moveToNext()
            }
        }
        result.close()
    }
}