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
import com.udc.grandapp.manager.LoginManager
import com.udc.grandapp.manager.SignUpManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosLogin
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.SignUpLoginModel
import com.udc.grandapp.services.RoutineAlarmService

class Login : AppCompatActivity(), View.OnClickListener {

    private lateinit var identificarse : MaterialButton
    private lateinit var registrarse : TextView
    private lateinit var identificarseGoogle: MaterialButton
    private lateinit var nombre: TextInputEditText
    private lateinit var pwd: TextInputEditText
    private lateinit var valuePwd: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_login)

        identificarse = findViewById(R.id.identificarseLogin)
        identificarse.setOnClickListener(this)

        registrarse = findViewById(R.id.registrarseLogin)
        registrarse.setOnClickListener(this)

        identificarseGoogle = findViewById(R.id.identificarseGoogle)
        identificarseGoogle.setOnClickListener(this)

        nombre = findViewById(R.id.editText_nombre_login)

        pwd = findViewById(R.id.pwd_login)
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

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.identificarseLogin -> login()
            R.id.registrarseLogin -> startActivity(Intent(this, SignUp::class.java))
            R.id.identificarseGoogle -> Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show()
        }
    }

    fun login(){
        if (nombre.text != null && !nombre.text.toString().isEmpty() && !valuePwd.isEmpty()) {
            val loginManager: LoginManager = LoginManager(this)

            class ResponseManager() : IResponseManagerGeneric {
                override fun onSuccesResponse(model: Any) {
                    val modelResponse: GenericModel = model as GenericModel
                    if (modelResponse.error == "0") {
                        val login: SignUpLoginModel =  SignUpLoginModel.Parse(modelResponse.json)
                        //TODO login?
                        startActivity(Intent(MainScreenActivity::class.simpleName))
                    }
                    else Toast.makeText(applicationContext, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                }

                override fun onErrorResponse(model: Any) {
                    Toast.makeText(applicationContext, "Error al loguearse (Diálogo)", Toast.LENGTH_LONG).show()
                }
            }

            val responseManager: IResponseManagerGeneric = ResponseManager()
            loginManager.realizarOperacion(responseManager, DatosLogin(nombre.text.toString(), valuePwd))
        }
    }
}