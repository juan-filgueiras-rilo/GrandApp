package com.udc.grandapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.items.CustomerDeviceSummary
import com.udc.grandapp.utils.CommonMethods
import es.udc.grandapp.ssdpconnect.client.SsdpClient
import es.udc.grandapp.ssdpconnect.client.response.SsdpResponse
import es.udc.grandapp.ssdpconnect.model.*
import kotlinx.android.synthetic.main.fragment_searchdevices.*
import java.lang.Exception

class NewDevice : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_searchdevices, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
        val mutableChange = MutableLiveData<CustomerDevice>()
        var devicesList: ArrayList<CustomerDevice> = ArrayList()
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, devicesList, it1, R.layout.custom_nuevodispositivo) {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }
            }
        }
        val client = SsdpClient.create()
        val options = DiscoveryOptions.builder().port(1982).build()
        val all = SsdpRequest.builder().discoveryOptions(options).serviceType("wifi_bulb").build()
        client.discoverServices(all, object : DiscoveryListener {
            override fun onFailed(ex: Exception, response: SsdpResponse) {
                Log.e("TAG", "ERROR ${response}")
            }

            override fun onServiceDiscovered(service: SsdpService) {
                mutableChange.postValue(CustomerDevice(service.serialNumber.split("x")[1].toLong(radix = 16), service.serviceType, service.remoteIp.hostAddress))
                //recyclerView.adapter?.notifyDataSetChanged()
                Log.e("TAG", "Found service: $service")
            }

            override fun onServiceAnnouncement(announcement: SsdpServiceAnnouncement) {
                Log.e("TAG","Service announced something: $announcement")
            }
        })
        mutableChange.observe(viewLifecycleOwner, Observer {
            devicesList.add(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refrescar.setOnClickListener {
            Toast.makeText(context, "Refrescar", Toast.LENGTH_LONG).show()
        }
    }



}
