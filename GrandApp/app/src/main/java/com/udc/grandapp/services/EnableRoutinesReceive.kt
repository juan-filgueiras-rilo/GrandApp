package com.udc.grandapp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

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
    }
}