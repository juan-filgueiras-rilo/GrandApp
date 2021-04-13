package com.udc.grandapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.edit_rutina.*

class AddRoutine : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.edit_rutina, container, false)
        rootView.findViewById<Button>(R.id.guardarRutina).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.cancelarRutina).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.addDispositivoButton).visibility = View.VISIBLE
        rootView.findViewById<TextView>(R.id.addDispositivoText).visibility = View.VISIBLE
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        guardarRutina.setOnClickListener {
            Toast.makeText(context, "Guardar Rutina", Toast.LENGTH_LONG).show()
            CommonMethods.create().clearExistFragments(context as FragmentActivity)
        }
        cancelarRutina.setOnClickListener {
            Toast.makeText(context, "Cancelar", Toast.LENGTH_LONG).show()
            CommonMethods.create().clearExistFragments(context as FragmentActivity)
        }
        addDispositivoButton.setOnClickListener {
            Toast.makeText(context, "Añadir dispositivo", Toast.LENGTH_LONG).show()
            val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.crearRutina, DeviceList())
            ft?.addToBackStack("Add Routines")
            ft?.commit()

        }
    }
}