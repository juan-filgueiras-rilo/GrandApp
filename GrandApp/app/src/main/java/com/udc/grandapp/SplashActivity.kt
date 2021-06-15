package com.udc.grandapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.udc.grandapp.manager.LoginManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosLogin
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.SignUpLoginModel
import com.udc.grandapp.model.UserInfoModel


class SplashActivity : AppCompatActivity() {

        private val SPLASH_DISPLAY_LENGTH: Long = 3000

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.splash_activity)
                UserConfigManager(this).getUserInfoFromBD()
                //comprobarUsuarioLogueado()
                startTime()


        }

        fun startTime(){
                object: CountDownTimer(SPLASH_DISPLAY_LENGTH,1000){
                        override fun onTick(millisUntilFinished: Long) {
                               // TODO("Not yet implemented")
                        }

                        override fun onFinish() {
                                comprobarUsuarioLogueado()
                        }
                }.start()
        }

        fun comprobarUsuarioLogueado(){
                val user: UserInfoModel? = UserConfigManager(this).getUserInfoFromBD()
                if (user == null || (user.userId == "" && user.token == ""))
                        startActivity(Intent(this, MainActivity::class.java))
                else {
                        actualizarToken(user)
                }
        }

        fun actualizarToken(user: UserInfoModel) {
            val activity: Activity = this
            val loginManager = LoginManager(activity)

            class ResponseManager() : IResponseManagerGeneric {
                override fun onSuccesResponse(model: GenericModel) {
                    val modelResponse: GenericModel = model
                    if (modelResponse.error == "0") {
                        val login: SignUpLoginModel = SignUpLoginModel.Parse(modelResponse.json)
                        UserConfigManager(activity).actualizarTokenBD(user, login.token, activity)
                        startActivity(Intent(activity, MainScreenActivity::class.java))
                    } else
                        startActivity(Intent(activity, MainActivity::class.java))
                }

                override fun onErrorResponse(model: String) {}
            }

            val responseManager: IResponseManagerGeneric = ResponseManager()
            loginManager.realizarOperacion(responseManager, DatosLogin(user.email, user.pwd))
        }

    }
