package com.udc.grandapp.fragments.login


import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.manager.SignUpManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.SignUpLoginModel


class SignUp : AppCompatActivity(), View.OnClickListener {

    private lateinit var crearCuenta : MaterialButton
    private lateinit var identificarse: TextView
    private lateinit var nombre: TextInputEditText
    private lateinit var nombreContenedor: TextInputLayout
    private lateinit var email: TextInputEditText
    private lateinit var emailContenedor: TextInputLayout
    private lateinit var pwd: TextInputEditText
    private lateinit var pwdContenedor: TextInputLayout
    private lateinit var pwd1: TextInputEditText
    private lateinit var pwd1Contenedor: TextInputLayout
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
        nombreContenedor = findViewById(R.id.nombre_usercontenedor)
        nombre.setOnFocusChangeListener{ view: View, b: Boolean ->
            nombreContenedor.isErrorEnabled = false
        }

        email = findViewById(R.id.email)
        emailContenedor = findViewById(R.id.emailcontenedor)
        email.setOnFocusChangeListener{ view: View, b: Boolean ->
            emailContenedor.isErrorEnabled = false
        }

        pwd = findViewById(R.id.pwd)
        pwdContenedor = findViewById(R.id.pwdcontenedor)
        pwd.setOnFocusChangeListener{ view: View, b: Boolean ->
            pwdContenedor.isErrorEnabled = false
        }

        pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                valuePwd = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        pwd1 = findViewById(R.id.pwd1)
        pwd1Contenedor = findViewById(R.id.pwd1contenedor)
        pwd1.setOnFocusChangeListener{ view: View, b: Boolean ->
            pwd1Contenedor.isErrorEnabled = false
        }
        pwd1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                valuePwd1 = s.toString()
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
                if (!validarLogin()){
                    singUp()
                    //startActivity(Intent(this, MainScreenActivity::class.java))
                }/*else
                    Toast.makeText(applicationContext, "Rellena todos los campos del formulario", Toast.LENGTH_LONG).show()*/
            }
        }
    }

    fun validarLogin(): Boolean{
        var error: Boolean = false
        if (nombre.text == null || nombre.text!!.isEmpty()){
            error = true
            this.nombreContenedor.error = resources.getString(R.string.vacio)
        }

        if (email.text == null || email.text!!.isEmpty()){
            error = true
            this.emailContenedor.error = resources.getString(R.string.vacio)
        } else if (!email.text!!.contains("@")) {
            error = true
            this.emailContenedor.error = resources.getString(R.string.mailerror)
        }

        if (pwd.text == null || pwd.text!!.isEmpty() || pwd1.text == null || pwd1.text!!.isEmpty() || !pwd.text.toString()!!.equals(pwd1.text.toString())){
            error = true
            if (pwd.text == null || pwd.text!!.isEmpty()) {
                this.pwdContenedor.error = resources.getString(R.string.vacio)

            }else{
                this.pwd1Contenedor.error = resources.getString(R.string.pwd1error)
            }
        }
        return error
    }

    fun singUp(){
        if (nombre.text != null && !nombre.text.toString().isEmpty() && email.text != null && !email.text.toString().isEmpty() && !valuePwd.isEmpty() && !valuePwd1.isEmpty() && valuePwd1.equals(valuePwd1)) {
            val mSingUpManager: SignUpManager = SignUpManager(this)
            val activity: Activity = this
            class ResponseManager() : IResponseManagerGeneric {
                override fun onSuccesResponse(model: GenericModel) {
                    if (model.error == "0") {
                        val singUp: SignUpLoginModel =  SignUpLoginModel.Parse(model.json)
                        UserConfigManager(activity).insertarUserBD(singUp, valuePwd, activity)
                        val intent: Intent = Intent(activity, MainScreenActivity::class.java)
                        activity.startActivity(intent)
                    }
                    else Toast.makeText(applicationContext, model.mensaje, Toast.LENGTH_LONG).show()
                }

                override fun onErrorResponse(model: String) {
                    //Toast.makeText(applicationContext, "Error al loguearse (Diálogo)", Toast.LENGTH_LONG).show()
                    activity.runOnUiThread { MaterialAlertDialogBuilder(activity)
                        .setTitle(resources.getString(R.string.error))
                        .setMessage(resources.getString(R.string.supporting_textManager))
                        .setNeutralButton(resources.getString(R.string.ok)){ dialog, which ->
                            // Respond to positive button press
                        }.show() }

                }
            }

            val responseManager: IResponseManagerGeneric = ResponseManager()
            mSingUpManager.realizarOperacion(responseManager, DatosSingUp(nombre.text.toString(), email.text.toString(), valuePwd))
        }
    }



}