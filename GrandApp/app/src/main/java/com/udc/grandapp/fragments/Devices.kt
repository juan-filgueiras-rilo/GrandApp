package com.udc.grandapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice

class Devices : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_devices, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        var listaExample: List<CustomerDevice> = listOf(CustomerDevice(1, "NombreProducto1", "loadURL"),
                                                CustomerDevice(2, "NombreProducto2", "loadURL"),
                                                CustomerDevice(3, "NombreProducto3", "loadURL"))
        recyclerView.adapter = context?.let { DevicesAdapter(it, listaExample) {
            Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG)
        } }
        return rootView
    }
}