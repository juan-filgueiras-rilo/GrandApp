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
import com.udc.grandapp.manager.LoginManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosLogin
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.SignUpLoginModel

class Login : AppCompatActivity(), View.OnClickListener {

    private lateinit var identificarse : MaterialButton
    private lateinit var registrarse : TextView
    private lateinit var identificarseGoogle: MaterialButton
    private lateinit var email: TextInputEditText
    private lateinit var emailContenedor: TextInputLayout
    private lateinit var pwd: TextInputEditText
    private lateinit var pwdContenedor: TextInputLayout
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

        email = findViewById(R.id.editText_nombre_login)
        emailContenedor = findViewById(R.id.editText_nombre_loginContenedor)
        email.setOnFocusChangeListener{ view: View, b: Boolean ->
            emailContenedor.isErrorEnabled = false
        }

        pwd = findViewById(R.id.pwd_login)
        pwdContenedor = findViewById(R.id.pwd_loginContenedor)
        pwd.setOnFocusChangeListener{ view: View, b: Boolean ->
            pwdContenedor.isErrorEnabled = false
        }
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
            R.id.registrarseLogin -> startActivity(Intent(this, SignUp::class.java))
            R.id.identificarseGoogle -> Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show()
            R.id.identificarseLogin -> {
                if (!validarLogin()){
                    login() }
            }
        }
    }

    fun validarLogin(): Boolean{
        var error: Boolean = false
        if (email.text == null || email.text!!.isEmpty()){
            error = true
            this.emailContenedor.error = resources.getString(R.string.vacio)
        } else if (!email.text!!.contains("@")) {
            error = true
            this.emailContenedor.error = resources.getString(R.string.mailerror)
        }

        if (pwd.text == null || pwd.text!!.isEmpty()){
            error = true
            this.pwdContenedor.error = resources.getString(R.string.vacio)
        }
        return error
    }

    fun login(){
        if (email.text != null && !email.text.toString().isEmpty() && !valuePwd.isEmpty()) {
            val loginManager: LoginManager = LoginManager(this)
            val activity: Activity = this
            class ResponseManager() : IResponseManagerGeneric {
                override fun onSuccesResponse(model: Any) {
                    val modelResponse: GenericModel = model as GenericModel
                    if (modelResponse.error == "0") {
                        val login: SignUpLoginModel =  SignUpLoginModel.Parse(modelResponse.json)
                        insertarUserBD(login)
                        val intent: Intent = Intent(activity, MainScreenActivity::class.java)
                        activity.startActivity(intent)
                    }
                    else Toast.makeText(applicationContext, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                }

                override fun onErrorResponse(model: Any) {
                    //Toast.makeText(applicationContext, "Error al loguearse (Diálogo)", Toast.LENGTH_LONG).show()
                    activity.runOnUiThread { MaterialAlertDialogBuilder(activity)
                            .setTitle(resources.getString(R.string.error))
                            .setMessage(resources.getString(R.string.supporting_textlogin))
                            .setNeutralButton(resources.getString(R.string.ok)){ dialog, which ->
                                // Respond to positive button press
                            }.show() }
                }
            }

            val responseManager: IResponseManagerGeneric = ResponseManager()
            loginManager.realizarOperacion(responseManager, DatosLogin(email.text.toString(), valuePwd))
        }
    }

    fun insertarUserBD(singUp: SignUpLoginModel){
        val db = UserConfigManager(this).writableDatabase

        val values = ContentValues().apply {
            put("userId", singUp.id)
            put("token",singUp.token)
            put("userName", singUp.userName)
            put("email", singUp.email)
            put("role", singUp.role)
        }

        val newRowId = db?.insert("DBUser", null, values)

        UserConfigManager.reiniciarInfoPersistente(this)
    }

}