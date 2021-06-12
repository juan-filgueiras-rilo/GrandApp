package com.udc.grandapp.mqtt

import android.os.AsyncTask
import com.udc.grandapp.fragments.NewDevice
import org.eclipse.paho.client.mqttv3.MqttException
import java.util.concurrent.Flow.Subscriber

class MqttReceiver(caller: NewDevice): AsyncTask<Void, Void, Void>() {
    private var mCaller: NewDevice = caller

    override fun doInBackground(vararg params: Void?): Void? {
        try {
            var client = MqttConnector("receiver")
            client.setTopic("/home/living-room/temperature")
            client.connect()
            client.setCallback(mCaller)
            client.subscribe()
        } catch (e: MqttException) {
        }
        return null
    }
}