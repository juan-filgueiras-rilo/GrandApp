package com.udc.grandapp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlin.random.Random

class EnableRoutinesReceive  : BroadcastReceiver() {

    var list = listOf<String>("Somos IoT Rangers y sabemos hacer servicios :D",
        "Te estoy vigilando",
        "Estoy recopilando información de tu gato para anuncios",
        "0.2 puntos semanales, porfa")
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, list[Random.nextInt(0,4)], Toast.LENGTH_SHORT).show()
    }
}