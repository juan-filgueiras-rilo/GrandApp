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
import com.udc.grandapp.adapters.DeviceNewRoutinesAdapter
import com.udc.grandapp.adapters.DeviceSummariesAdapter
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.adapters.DevicesToRoutineRoutinesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.items.CustomerDeviceSummary
import kotlinx.android.synthetic.main.custom_lista.*
import kotlinx.android.synthetic.main.fragment_devices.titulo
import kotlinx.android.synthetic.main.ver_rutina.*

class DevicesToRoutineCreateRoutine : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_w_recycler, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        var deviceSummaryListExample: List<CustomerDeviceSummary> = listOf(CustomerDeviceSummary(1, "NombreDispositivo1", "descripcion1", "url1"),
                CustomerDeviceSummary(2, "NombreDispositivo2", "descripcion2", "url2"),
                CustomerDeviceSummary(3, "NombreDispositivo3", "descripcion3", "url3"))
        recyclerView.adapter = context?.let { DevicesToRoutineRoutinesAdapter(it, deviceSummaryListExample, parentFragmentManager) {
            Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG)
        } }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titulo.text = "Mis Dispositivos";
    }
}