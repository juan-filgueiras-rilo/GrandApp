package com.udc.grandapp.mqtt

import android.provider.Telephony.Carriers
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import kotlin.jvm.Throws


class MqttConnector(clientId: String?) {
    companion object {
        private const val BROKER_URL = "tcp://m20.cloudmqtt.com:12483"
        private const val USERNAME = "abcdefgh"
        private const val PASSWORD = "aB1CDEfGHiLM"
    }

    private var mOptions: MqttConnectOptions = MqttConnectOptions().also {
        it.isCleanSession = true
        it.userName = USERNAME
        it.password = Carriers.PASSWORD.toCharArray()
    }
    private var mClient: MqttClient = MqttClient(BROKER_URL, clientId, MemoryPersistence())
    private var mTopic: String? = null

    @Throws(MqttException::class)
    fun connect() {
        mClient!!.connect(mOptions)
    }

    @Throws(MqttException::class)
    fun disconnect() {
        mClient!!.disconnect()
    }

    fun setTopic(topic: String) {
        mTopic = topic
    }

    fun setCallback(callback: MqttCallback) {
        mClient.setCallback(callback)
    }

    @Throws(MqttException::class)
    fun publish(message: ByteArray?) {
        val mqttMessage = MqttMessage()
        mqttMessage.payload = message
        mqttMessage.qos = 0
        mClient!!.publish(mTopic, mqttMessage)
    }

    @Throws(MqttException::class)
    fun subscribe() {
        mClient!!.subscribe(mTopic)
    }
}
