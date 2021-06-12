package com.udc.grandapp.fragments

import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.mqtt.MqttReceiver
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.fragment_searchdevices.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage


class NewDevice : Fragment(), MqttCallback {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private lateinit var mDevice: TextView
    private lateinit var mHandler: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_searchdevices, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        var listaExample: List<CustomerDevice> = listOf(CustomerDevice(1, "Bombilla", "loadURL"))
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, listaExample, it1, R.layout.custom_nuevodispositivo) {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }
            }
        }
        mDevice = rootView.findViewById<TextView>(R.id.addDispositivoText)
        mHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                mDevice.setText(msg.obj.toString())
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refrescar.setOnClickListener {
            Toast.makeText(context, "Refrescar", Toast.LENGTH_LONG).show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    override fun messageArrived(topic: String?, mqttMessage: MqttMessage?) {
        val message = mHandler.obtainMessage(0, mqttMessage)
        message.sendToTarget()
        Log.e(TAG, "$topic $mqttMessage")
    }

    override fun connectionLost(throwable: Throwable?) {
        Toast.makeText(context, "Connection lost with the broker", Toast.LENGTH_SHORT)
    }

    override fun onResume() {
        super.onResume()
        MqttReceiver(this).execute()
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        // noop
    }

}
