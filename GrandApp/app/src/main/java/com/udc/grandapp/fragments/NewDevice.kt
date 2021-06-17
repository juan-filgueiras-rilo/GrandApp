package com.udc.grandapp.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.system.Os.socket
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.utils.CommonMethods
import es.udc.grandapp.ssdpconnect.client.SsdpClient
import es.udc.grandapp.ssdpconnect.client.response.SsdpResponse
import es.udc.grandapp.ssdpconnect.model.*
import kotlinx.android.synthetic.main.fragment_searchdevices.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress


class NewDevice : Fragment() {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private val mDeviceFound = MutableLiveData<CustomerDevice>()
    private val mSpinnerActive = MutableLiveData<Boolean>()
    var mDevicesList: ArrayList<CustomerDevice> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_searchdevices, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, mDevicesList, it1, R.layout.custom_nuevodispositivo, {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }, this)
            }
        }
        mDeviceFound.observe(viewLifecycleOwner, Observer {
            mDevicesList.add(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
        mSpinnerActive.observe(viewLifecycleOwner, Observer {
            if (it)
                layoutProgressBar.visibility = View.VISIBLE
            else
                layoutProgressBar.visibility = View.GONE
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refrescar.setOnClickListener {
            findDevicesCoroutine(mSpinnerActive, mDeviceFound)
        }
        findDevicesCoroutine(mSpinnerActive, mDeviceFound)
    }


    private fun findDevicesCoroutine(spinnerActive: MutableLiveData<Boolean>, deviceFound: MutableLiveData<CustomerDevice>) {
        viewLifecycleOwner.lifecycleScope.launch {
            spinnerActive.postValue(true)
            mDevicesList.clear()
            val client = SsdpClient.create()
            val options = DiscoveryOptions.builder().port(1982).build()
            val all = SsdpRequest.builder().discoveryOptions(options).serviceType("wifi_bulb").build()

            client.discoverServices(all, object : DiscoveryListener {
                override fun onFailed(ex: Exception, response: SsdpResponse) {
                    Log.e("TAG", "ERROR ${response}")
                }

                override fun onServiceDiscovered(service: SsdpService) {
                    val id = service.serialNumber.split("x")[1].toLong(radix = 16)
                    val puerto = service.location.split(":")[2].replace(",", "").toLong()
                    val currentIPs = UserConfigManager(context as FragmentActivity).getUniqueIPs()
                    if (!currentIPs.contains(service.remoteIp.hostAddress)) {
                        deviceFound.postValue(CustomerDevice(id, "yeelight", "", service.remoteIp.hostAddress, puerto, "yeelight"))
                    }
                    Log.e("TAG", "Found service: $service")
                }

                override fun onServiceAnnouncement(announcement: SsdpServiceAnnouncement) {
                    Log.e("TAG", "Service announced something: $announcement")
                }
            })
            delay(5 * 1000)
            client.stopDiscovery()
            spinnerActive.postValue(false)
        }
    }

    private fun findRoombaCoroutine(spinnerActive: MutableLiveData<Boolean>, deviceFound: MutableLiveData<CustomerDevice>) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            spinnerActive.postValue(true)
            mDevicesList.clear()
            val client = SsdpClient.create()
            val options = DiscoveryOptions.builder().port(1900).build()
            val all = SsdpRequest.builder().discoveryOptions(options).build()

            client.discoverServices(all, object : DiscoveryListener {
                override fun onFailed(ex: Exception, response: SsdpResponse) {
                    Log.e("TAG", "ERROR ${response}")
                }

                override fun onServiceDiscovered(service: SsdpService) {
                    Log.e("TAG", "Found service: $service")
                    val id = service.serialNumber.split("x")[1].toLong(radix = 16)
                    val puerto = service.location.split(":")[2].replace(",", "").toLong()
                    val currentIPs = UserConfigManager(context as FragmentActivity).getUniqueIPs()
                    if (!currentIPs.contains(service.remoteIp.hostAddress)) {
                        deviceFound.postValue(CustomerDevice(id, "roomba", "", service.remoteIp.hostAddress, puerto, "yeelight"))
                    }
                }

                override fun onServiceAnnouncement(announcement: SsdpServiceAnnouncement) {
                    Log.e("TAG", "Service announced something: $announcement")
                }
            })
            delay(5 * 1000)
            client.stopDiscovery()
        }
    }
}
