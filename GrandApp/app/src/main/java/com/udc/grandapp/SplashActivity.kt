package com.udc.grandapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity
import com.udc.grandapp.fragments.login.Login
import com.udc.grandapp.fragments.login.SignUp

class SplashActivity : AppCompatActivity() , View.OnClickListener {

        private lateinit var identificarse : MaterialButton
        private lateinit var registrarse : MaterialButton
        private lateinit var sinIniciarSesion: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.splash)

                identificarse = findViewById(R.id.identificarseSplash)
                identificarse.setOnClickListener(this)

                registrarse = findViewById(R.id.registrarseSplash)
                registrarse.setOnClickListener(this)

                sinIniciarSesion = findViewById(R.id.sinIniciarSesion)
                sinIniciarSesion.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
                when (v!!.id) {
                        R.id.identificarseSplash -> startActivity(Intent(this, Login::class.java))
                        R.id.registrarseSplash -> startActivity(Intent(this, SignUp::class.java))
                        R.id.sinIniciarSesion -> startActivity(Intent(this, MainScreenActivity::class.java))
                }
        }


}