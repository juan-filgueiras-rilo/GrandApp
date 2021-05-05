package com.udc.grandapp.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R

class Login : AppCompatActivity(), View.OnClickListener {

    private lateinit var identificarse : MaterialButton
    private lateinit var registrarse : TextView
    private lateinit var identificarseGoogle: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_login)

        identificarse = findViewById(R.id.identificarseLogin)
        identificarse.setOnClickListener(this)

        registrarse = findViewById(R.id.registrarseLogin)
        registrarse.setOnClickListener(this)

        identificarseGoogle = findViewById(R.id.identificarseGoogle)
        identificarseGoogle.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.identificarseLogin -> startActivity(Intent(this, MainScreenActivity::class.java))
            R.id.registrarseLogin -> startActivity(Intent(this, SignUp::class.java))
            R.id.identificarseGoogle -> Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show()
        }
    }
}