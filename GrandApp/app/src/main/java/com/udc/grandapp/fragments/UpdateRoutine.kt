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
import kotlinx.android.synthetic.main.crear_rutina.*

class UpdateRoutine : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.ver_rutina, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        guardarRutina.setOnClickListener {
            Toast.makeText(context, "Guardar rutina", Toast.LENGTH_LONG).show()
        }
        cancelarRutina.setOnClickListener {
            Toast.makeText(context, "Cancelar rutina", Toast.LENGTH_LONG).show()
        }
        addDispositivoToRutina.setOnClickListener {
            Toast.makeText(context, "Añadir dispositivo", Toast.LENGTH_LONG).show()
        }
    }
}