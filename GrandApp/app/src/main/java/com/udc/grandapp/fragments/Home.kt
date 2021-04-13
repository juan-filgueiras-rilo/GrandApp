package com.udc.grandapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DeviceSummaryAdapter
import com.udc.grandapp.adapters.RoutinesAdapter
import com.udc.grandapp.adapters.RoutinesSummaryAdapter
import com.udc.grandapp.items.CustomerDeviceSummary
import com.udc.grandapp.items.CustomerRoutine

class Home : Fragment() {

    private lateinit var rootView : View
    private lateinit var routineRecyclerView: RecyclerView
    private lateinit var deviceRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_principal, container, false)
        routineRecyclerView = rootView.findViewById<RelativeLayout>(R.id.routine_recycler).findViewById<RecyclerView>(R.id.recycler)
        routineRecyclerView.setHasFixedSize(true)
        routineRecyclerView.layoutManager = GridLayoutManager(context, 1)

        deviceRecyclerView = rootView.findViewById<RelativeLayout>(R.id.device_recycler).findViewById<RecyclerView>(R.id.recycler)
        deviceRecyclerView.setHasFixedSize(true)
        deviceRecyclerView.layoutManager = GridLayoutManager(context, 1)

        val deviceSummaryListExample: List<CustomerDeviceSummary> = listOf(CustomerDeviceSummary(1, "NombreDispositivo1", "descripcion1", "url1"),
            CustomerDeviceSummary(2, "NombreDispositivo2", "descripcion2", "url2"),
            CustomerDeviceSummary(3, "NombreDispositivo3", "descripcion3", "url3"))
        deviceRecyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DeviceSummaryAdapter(it, deviceSummaryListExample, it1) {
                Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
            }
            }
        }

        val routineListExample: List<CustomerRoutine> = listOf(CustomerRoutine(1, "NombreRutina1", "descripcion1", "url1"),
                CustomerRoutine(2, "NombreRutina2", "descripcion2", "url2"),
                CustomerRoutine(3, "NombreRutina3", "descripcion3", "url3"))
        routineRecyclerView.adapter = context?.let {
            activity?.let { it1 ->
                RoutinesSummaryAdapter(it, routineListExample, it1) {
                Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }
            }
        }

        return rootView
    }
}