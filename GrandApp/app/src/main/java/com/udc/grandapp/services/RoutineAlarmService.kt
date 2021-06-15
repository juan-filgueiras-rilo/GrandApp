package com.udc.grandapp.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service;
import android.content.Context
import android.content.Intent;
import android.os.*
import android.widget.Toast

class RoutineAlarmService  : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        runService(startId)
        // If we get killed, after returning from here, restart
        return START_STICKY

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "Soy un servicio y me estoy muriendo. Vuelvo ahora.", Toast.LENGTH_SHORT).show()
        super.onDestroy()
        var broadcastIntent:Intent = Intent("com.udc.grandapp.services.RestartService")
        sendBroadcast(broadcastIntent)
    }

    private fun runService(startId: Int) {
        var context = this
        Toast.makeText(context, "Soy un servicio y estoy iniciándome", Toast.LENGTH_SHORT).show()
        var intent:Intent = Intent(context, EnableRoutinesReceive::class.java)
        var alarmIntent:PendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        var alarmManager:AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1*1000, 10*1000, alarmIntent)
    }
}
