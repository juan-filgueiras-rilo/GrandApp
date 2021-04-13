package com.udc.grandapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.udc.grandapp.R

class Profile : Fragment() {
    private lateinit var rootView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.profile, container, false)
        rootView.findViewById<Button>(R.id.buttonGuardarCambios).setOnClickListener(View.OnClickListener { Toast.makeText(context, "Guardar cambios", Toast.LENGTH_LONG).show() })
        return rootView
    }

}