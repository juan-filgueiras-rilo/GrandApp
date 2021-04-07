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
import com.udc.grandapp.adapters.RoutinesAdapter
import com.udc.grandapp.items.CustomerRoutine
import kotlinx.android.synthetic.main.fragment_routines.*

class Routines : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_routines, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        var lista: List<CustomerRoutine> = listOf(
            CustomerRoutine(1, "Rutina 1", "Descripción de la rutina 1", "url1"),
            CustomerRoutine(2, "Rutina 2", "Descripción de la rutina 2", "url2"),
            CustomerRoutine(3, "Rutina 3", "Descripción de la rutina 3", "url3"))
        recyclerView.adapter = context?.let { RoutinesAdapter(it, lista, parentFragmentManager) {
            Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
        } }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titulo.text = "Mis Rutinas";
        addRoutine.setOnClickListener {
            var fr = parentFragmentManager?.beginTransaction()
            fr?.replace(R.id.fragmentRoutines, NewRoutine())
            fr?.commit()
        }
    }
}