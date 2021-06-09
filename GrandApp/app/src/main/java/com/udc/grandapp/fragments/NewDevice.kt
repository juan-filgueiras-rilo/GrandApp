package com.udc.grandapp.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Configuration
import android.net.nsd.NsdManager
import android.net.nsd.NsdManager.DiscoveryListener
import android.net.nsd.NsdManager.ResolveListener
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.fragment_searchdevices.*
import java.net.InetAddress


class NewDevice : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private lateinit var mNsdManager: NsdManager


    private val SERVICE_NAME = "Client Device"
    private val SERVICE_TYPE = " _services._dns-sd._udp"

    private var hostAddress: InetAddress? = null
    private var hostPort = 0

    var mResolveListener: ResolveListener = object : ResolveListener {
        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed $errorCode")
            Log.e(TAG, "serivce = $serviceInfo")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.d(TAG, "Resolve Succeeded. $serviceInfo")
            if (serviceInfo.serviceName == SERVICE_NAME) {
                Log.d(TAG, "Same IP.")
                return
            }

            // Obtain port and IP
            hostPort = serviceInfo.port
            hostAddress = serviceInfo.host
        }
    }

    var mDiscoveryListener: DiscoveryListener = object : DiscoveryListener {
        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Log.d(TAG, "Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            Log.d(TAG, "Service discovery success : $service")
            Log.d(TAG, "Host = " + service.serviceName)
            Log.d(TAG, "port = " + service.port.toString())
            if (service.serviceType != SERVICE_TYPE) {
                // Service type is the string containing the protocol and
                // transport layer for this service.
                Log.d(TAG, "Unknown Service Type: " + service.serviceType)
            } else if (service.serviceName == SERVICE_NAME) {
                // The name of the service tells the user what they'd be
                // connecting to. It could be "Bob's Chat App".
                Log.d(TAG, "Same machine: $SERVICE_NAME")
            } else {
                Log.d(TAG, "Diff Machine : " + service.serviceName)
                // connect to the service and obtain serviceInfo
                mNsdManager.resolveService(service, mResolveListener)
            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "service lost$service")
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            mNsdManager.stopServiceDiscovery(this)
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            mNsdManager.stopServiceDiscovery(this)
        }
    }

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
        mNsdManager = requireContext().getSystemService(Context.NSD_SERVICE) as NsdManager
        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener)
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

}
