package com.udc.grandapp.services

import android.content.BroadcastReceiver
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
        var day:String = currentDateTime.format(DateTimeFormatter.ofPattern("e"))


        val db = UserConfigManager(context).writableDatabase

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
            put("rutineId", "1")
        }
        val values2 = ContentValues().apply {
            put("descripcion", "descripcion")
            put("userId","1")
            put("hour", "13")
            put("minute", "00")
            put("nombre", "hila")
            put("rutineId", "1")
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

        UserConfigManager.reiniciarInfoPersistente(context)

*/
        var result = db!!.rawQuery("SELECT Id FROM Routine_day WHERE day = " + day.toUpperCase(), null)
        var toExecute: MutableList<String> = mutableListOf()
        if(result.moveToFirst()) {
            while (!result.isAfterLast) {
                var cursor = db!!.rawQuery("SELECT routineId FROM DBRoutine WHERE hour = " + hour + "AND minute = " + minute, null)
                if (cursor.getString(0) != "") {
                    toExecute.add(cursor.getString(0))
                }
            }
        }
        if (!toExecute.isEmpty()) {
            for (i in toExecute) {
                //TODO ejecutar rutina con id i
            }
        }

    }
}