package com.udc.grandapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import kotlinx.android.synthetic.main.fragment_devices.*


class Devices : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_devices, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        val listaExample: List<CustomerDevice> = listOf(CustomerDevice(1, "NombreProducto1", "loadURL"),
                CustomerDevice(2, "NombreProducto2", "loadURL"),
                CustomerDevice(3, "NombreProducto3", "loadURL"))

        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, listaExample, it1, R.layout.custom_dispositivo) {
                Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
            }
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titulo.text = activity?.resources?.getString(R.string.mis_dispositivos)
        addDevice.setOnClickListener {
            Toast.makeText(context, "Nuevo dispositivo", Toast.LENGTH_LONG).show()
            val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.fragmentDevices, NewDevice())
            ft?.addToBackStack("Devices")
            ft?.commit()
        }
    }
}