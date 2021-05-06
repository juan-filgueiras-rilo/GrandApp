package com.udc.grandapp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class EnableRoutinesReceive  : BroadcastReceiver() {

    var list = listOf<String>("Somos IoT Rangers y sabemos hacer servicios :D","Te estoy vigilando","Estoy recopilando información de tu gato para anuncios","0.2 semanales, porfa")
    var index = 0
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, list.get(index), Toast.LENGTH_SHORT).show()
        if (index == 3) index = 0 else index += 1
    }
}