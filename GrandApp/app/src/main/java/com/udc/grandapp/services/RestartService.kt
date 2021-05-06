package com.udc.grandapp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RestartService: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.startService(Intent(context, RoutineAlarmService::class.java))
    }
}