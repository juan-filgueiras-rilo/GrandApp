package com.udc.grandapp.services

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.udc.grandapp.manager.configuration.UserConfigManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EnableRoutinesReceive  : BroadcastReceiver() {

    /*var list = listOf<String>("Somos IoT Rangers y sabemos hacer servicios :D",
        "Te estoy vigilando",
        "Estoy recopilando información de tu gato para anuncios",
        "0.2 puntos semanales, porfa")*/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        //Toast.makeText(context, list[Random.nextInt(0,4)], Toast.LENGTH_SHORT).show()
        var currentDateTime= LocalDateTime.now()
        var hour:String = currentDateTime.format(DateTimeFormatter.ofPattern("HH"))
        var minute:String = currentDateTime.format(DateTimeFormatter.ofPattern("mm"))
        var day:String = currentDateTime.format(DateTimeFormatter.ofPattern("EEEE"))


        val db = UserConfigManager(context).writableDatabase

        val values = ContentValues().apply {
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
            put("routineId", "2")
        }
        val values2 = ContentValues().apply {
            put("descripcion", "descripcion")
            put("userId","1")
            put("hour", "13")
            put("minute", "00")
            put("nombre", "hila")
            put("routineId", "1")
        }

        val values3 = ContentValues().apply {
            put("IdRoutine", "1")
            put("day","Tuesday")
        }

        val values4 = ContentValues().apply {
            put("IdRoutine", "1")
            put("day","Wednesday")
        }

        val newRowId = db?.insert("DBUser", null, values)

        val newRowId2 = db?.insert("DBRoutine", null, values1)
        val newRowId3 = db?.insert("DBRoutine", null, values2)
        val newRowId4 = db?.insert("Routine_day", null, values3)
        val newRowId5 = db?.insert("Routine_day", null, values4)

        var result = db!!.rawQuery("SELECT IdRoutine FROM Routine_day WHERE day = \"" + day + "\"", null)
        var toExecute: MutableList<String> = mutableListOf()
        if(result.moveToFirst()) {
            while (!result.isAfterLast) {
                //println(result.getString(result.getColumnIndex("IdRoutine")))
                    hour = "12"
                minute = "00"
                var cursor = db!!.rawQuery("SELECT routineId FROM DBRoutine WHERE hour = \"" +
                        hour + "\"" + "AND minute = \"" + minute  + "\"" +"AND routineId = \"" +
                        result.getString(result.getColumnIndex("IdRoutine")) +"\"", null)
                if(cursor.moveToFirst()) {
                    while (!cursor.isAfterLast) {
                        if (cursor.getString(cursor.getColumnIndex("routineId")) != null) {
                            //TODO ejecutar rutina con id i
                            println("Tengo la rutina " + cursor.getString(cursor.getColumnIndex("routineId")))
                        }

                    }
                    cursor.close()
                    result.close()
                }
            }

        }

    }
}