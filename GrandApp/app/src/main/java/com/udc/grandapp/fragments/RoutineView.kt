package com.udc.grandapp.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.edit_rutina.*

class RoutineView : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.edit_rutina, container, false)
        rootView.findViewById<LinearLayout>(R.id.dosBotones).visibility = View.GONE
        rootView.findViewById<LinearLayout>(R.id.addDispositivo).visibility = View.GONE
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        val listaExample: List<CustomerDevice> = listOf(CustomerDevice(1,"NombreProducto1", "loadURL"),
                CustomerDevice(2, "NombreProducto2", "loadURL"),
                CustomerDevice(3, "NombreProducto3", "loadURL")
        )

        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, listaExample, it1, R.layout.custom_dispositivorutinaview) {
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