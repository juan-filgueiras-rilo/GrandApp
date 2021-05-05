package com.udc.grandapp.fragments.login


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.manager.SignUpManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosSingUp


class SignUp : AppCompatActivity(), View.OnClickListener {

    private lateinit var crearCuenta : MaterialButton
    private lateinit var identificarse: TextView
    private lateinit var nombre: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var pwd: TextInputEditText
    private lateinit var pwd1: TextInputEditText
    private lateinit var valuePwd: String
    private lateinit var valuePwd1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_signup)

        identificarse = findViewById(R.id.identificarseSignUp)
        identificarse.setOnClickListener(this)

        crearCuenta = findViewById(R.id.crearCuenta)
        crearCuenta.setOnClickListener(this)

        nombre = findViewById(R.id.nombre_user)
        email = findViewById(R.id.email)

        pwd = findViewById(R.id.pwd)
        pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                valuePwd = s.toString()
                //pwd.setText("*".repeat(s!!.length))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        pwd1 = findViewById(R.id.pwd1)
        pwd1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                valuePwd1 = s.toString()
                //pwd1.setText("*".repeat(s!!.length))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.identificarseSignUp -> startActivity(Intent(this, Login::class.java))
            R.id.crearCuenta -> {
                singUp()
            }
        }
    }

    fun singUp(){
        if (nombre.text != null && !nombre.text.toString().isEmpty() && email.text != null && !email.text.toString().isEmpty() && !valuePwd.isEmpty() && !valuePwd1.isEmpty() && valuePwd1.equals(valuePwd1)) {
            val mSingUpManager: SignUpManager = SignUpManager(this)

            class ResponseManager() : IResponseManagerGeneric {
                override fun onSuccesResponse(model: Any) {
                        startActivity(Intent(MainScreenActivity::class.simpleName))
                }

                override fun onErrorResponse(model: Any) {
                    Toast.makeText(applicationContext, "Error al loguearse (Diálogo)", Toast.LENGTH_LONG).show()
                }
            }

            val responseManager: IResponseManagerGeneric = ResponseManager()
            mSingUpManager.realizarOperacion(responseManager, DatosSingUp(nombre.text.toString(), email.text.toString(), valuePwd))
        }
    }
}