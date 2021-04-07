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
import com.udc.grandapp.adapters.EditDevicesAdapter
import com.udc.grandapp.items.RoutinesDevice
import kotlinx.android.synthetic.main.fragment_editdevice.*

class EditDevices  : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_editdevice, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        var listaRutinas: List<RoutinesDevice> = listOf(RoutinesDevice(1, "Rutina 1", "Descripción"),
                                                        RoutinesDevice(2, "Rutina 2", "Descripción"))
        recyclerView.adapter = context?.let { EditDevicesAdapter(it, listaRutinas, parentFragmentManager) {
            Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG)
        } }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText.hint = "Bombilla 1"
        aceptar.setOnClickListener {
            var fr = parentFragmentManager?.beginTransaction()
            fr?.replace(R.id.fragmentDevices, Devices())
            fr?.commit()
        }
        cancelar.setOnClickListener {
            var fr = parentFragmentManager?.beginTransaction()
            fr?.replace(R.id.fragmentDevices, Devices())
            fr?.commit()
        }

    }
}
