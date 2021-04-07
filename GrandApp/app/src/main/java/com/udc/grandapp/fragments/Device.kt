package com.udc.grandapp.fragments

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_editdevice.*


class Device : Fragment() {

    private lateinit var rootView : View
    private lateinit var routineRecyclerView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_editdevice, container, false)
        routineRecyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        routineRecyclerView.setHasFixedSize(true)
        routineRecyclerView.layoutManager = GridLayoutManager(context, 1)

        var routineListExample: List<CustomerRoutine> = listOf(
            CustomerRoutine(1, "NombreRutina1", "descripcion1", "url1"),
            CustomerRoutine(2, "NombreRutina2", "descripcion2", "url2"),
            CustomerRoutine(3, "NombreRutina3", "descripcion3", "url3")
        )
        routineRecyclerView.adapter = context?.let { RoutinesAdapter(it, routineListExample, parentFragmentManager) {
            Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG)
        } }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText.hint = "Bombilla 1"
        aceptar.setOnClickListener {
            var fr = parentFragmentManager?.beginTransaction()
            fr?.replace(R.id.mainFragment, Resume())
            fr?.commit()
        }
        cancelar.setOnClickListener {
            var fr = parentFragmentManager?.beginTransaction()
            fr?.replace(R.id.mainFragment, Resume())
            fr?.commit()
        }

    }
}