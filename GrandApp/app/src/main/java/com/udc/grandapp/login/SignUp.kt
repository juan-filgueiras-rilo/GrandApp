package com.udc.grandapp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R


class SignUp : AppCompatActivity(), View.OnClickListener {

    private lateinit var crearCuenta : MaterialButton
    private lateinit var identificarse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_signup)

        identificarse = findViewById(R.id.identificarseSignUp)
        identificarse.setOnClickListener(this)

        crearCuenta = findViewById(R.id.crearCuenta)
        crearCuenta.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.identificarseSignUp -> startActivity(Intent(this, Login::class.java))
            R.id.crearCuenta -> startActivity(Intent(this, MainScreenActivity::class.java))
        }
    }
}