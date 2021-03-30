package com.udc.grandapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.udc.grandapp.login.Login
import com.udc.grandapp.login.SignUp

class SplashActivity : AppCompatActivity() , View.OnClickListener {

        private lateinit var identificarse : Button
        private lateinit var registrarse : Button
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