package com.udc.grandapp.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.ViewRoutineAdapter
import com.udc.grandapp.items.RoutinesDevice
import com.udc.grandapp.utils.CommonMethods

class DeviceView : Fragment() {
    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_editdevice, container, false)
        rootView.findViewById<LinearLayout>(R.id.dosBotones).visibility = View.GONE
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        val listaRutinas: List<RoutinesDevice> = listOf(RoutinesDevice(1, "Rutina 1", "Descripción"),
                RoutinesDevice(2, "Rutina 2", "Descripción"))
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                ViewRoutineAdapter(it, listaRutinas, it1, 1) {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }
            }
        }

        return rootView
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }
}