package com.udc.grandapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.fragments.Home
import com.udc.grandapp.fragments.login.Login
import com.udc.grandapp.fragments.login.SignUp
import com.udc.grandapp.manager.LoginManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosLogin
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.SignUpLoginModel
import com.udc.grandapp.model.UserInfoModel

class MainActivity : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_ini)
                comprobarUsuarioLogueado()
        }

        fun comprobarUsuarioLogueado(){
                val user: UserInfoModel? = UserConfigManager(this).getUserInfoFromBD()
                if (user == null || (user.userId == "" && user.token == ""))
                        startActivity(Intent(this, SplashActivity::class.java))
                else {
                        actualizarToken(user)
                }
        }

        fun actualizarToken(user: UserInfoModel){
                val activity: Activity = this
                val loginManager = LoginManager(activity)
                class ResponseManager() : IResponseManagerGeneric {
                        override fun onSuccesResponse(model: GenericModel) {
                                val modelResponse: GenericModel = model as GenericModel
                                if (modelResponse.error == "0") {
                                        val login: SignUpLoginModel =  SignUpLoginModel.Parse(modelResponse.json)
                                        UserConfigManager(activity).actualizarTokenBD(user, login.token)
                                        startActivity(Intent(activity, MainScreenActivity::class.java))
                                }else
                                        startActivity(Intent(activity, SplashActivity::class.java))
                        }

                        override fun onErrorResponse(model: String) {}
                }

                val responseManager: IResponseManagerGeneric = ResponseManager()
                loginManager.realizarOperacion(responseManager, DatosLogin(user.email, user.pwd))
        }



}