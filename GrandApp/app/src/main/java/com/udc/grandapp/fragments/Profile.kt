package com.udc.grandapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.udc.grandapp.EulaActivity
import com.udc.grandapp.R
import kotlinx.android.synthetic.main.profile.*

class Profile : Fragment() {
    private lateinit var rootView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.profile, container, false)
        rootView.findViewById<Button>(R.id.buttonGuardarCambios).setOnClickListener(View.OnClickListener { Toast.makeText(context, "Guardar cambios", Toast.LENGTH_LONG).show() })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagAcuerdoUsuario.setOnClickListener {
            val intent = Intent(this.activity, EulaActivity::class.java)
            val bundle = Bundle()
            val eula = R.raw.eula
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            startActivityForResult(intent, 1)
        }
    }

}