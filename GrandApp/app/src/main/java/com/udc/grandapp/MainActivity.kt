package com.udc.grandapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.fragments.login.Login
import com.udc.grandapp.fragments.login.SignUp
import com.udc.grandapp.items.SettingsDevice
import com.udc.grandapp.manager.LoginManager
import com.udc.grandapp.manager.SignUpManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosLogin
import com.udc.grandapp.manager.transferObjects.DatosSingUp
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.SignUpLoginModel
import com.udc.grandapp.model.UserInfoModel

class MainActivity : AppCompatActivity() , View.OnClickListener {

        private lateinit var identificarse : MaterialButton
        private lateinit var registrarse : MaterialButton
        private lateinit var singInWithGoogle: SignInButton
        private lateinit var mGoogleSignInClient: GoogleSignInClient

        companion object{
                var mGoogleSignInClientStatic: GoogleSignInClient? = null
        }

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                checkEula()
                setContentView(R.layout.inicio)

                identificarse = findViewById(R.id.identificarseSplash)
                identificarse.setOnClickListener(this)

                registrarse = findViewById(R.id.registrarseSplash)
                registrarse.setOnClickListener(this)

                singInWithGoogle = findViewById(R.id.google_login_btn)
                singInWithGoogle.setOnClickListener(this)
                singInWithGoogle.setSize(SignInButton.SIZE_STANDARD)

                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("443007729231-i06rgo2nm6ve3rh1bhvaqjvlki0u2i6b.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
                mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        }

        override fun onClick(v: View?) {
                when (v!!.id) {
                        R.id.identificarseSplash -> startActivity(Intent(this, Login::class.java))
                        R.id.registrarseSplash -> startActivity(Intent(this, SignUp::class.java))
                        R.id.google_login_btn -> signWithGoogle()
                }
        }

        private fun checkEula() {
                val preferences = applicationContext
                        .getSharedPreferences("com.udc.grandapp", Context.MODE_PRIVATE)
                val eulaAccepted = preferences.getBoolean("eulaAccepted", false)
                if (!eulaAccepted) {
                        val intent = Intent(this, EulaActivity::class.java)
                        val bundle = Bundle()
                        val eula = R.raw.eula
                        bundle.putInt("eula", eula)
                        intent.putExtras(bundle)
                        startActivityForResult(intent, 1)
                }
        }

        private fun signWithGoogle(){
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, 1)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (requestCode == 1) {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        val task: Task<GoogleSignInAccount> =
                                GoogleSignIn.getSignedInAccountFromIntent(data)
                        handleSignInResult(task)
                }
        }

        private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
                try {
                        val account = completedTask.getResult(ApiException::class.java)
                        // Signed in successfully
                        val googleId = account?.id ?: ""
                        Log.i("Google ID",googleId)

                        val googleFirstName = account?.givenName ?: ""
                        Log.i("Google First Name", googleFirstName)

                        val googleLastName = account?.familyName ?: ""
                        Log.i("Google Last Name", googleLastName)

                        val googleEmail = account?.email ?: ""
                        Log.i("Google Email", googleEmail)

                        val googleProfilePicURL = account?.photoUrl.toString()
                        Log.i("Google Profile Pic URL", googleProfilePicURL)

                        val googleIdToken = account?.idToken ?: ""
                        Log.i("Google ID Token", googleIdToken)

                        trySingUp("$googleFirstName $googleLastName", googleEmail)
                        mGoogleSignInClientStatic = mGoogleSignInClient
                        // Signed in successfully, show authenticated UI.
                        Log.e("UPDATE", "UPDATEYU")
                } catch (e: ApiException) {
                        // The ApiException status code indicates the detailed failure reason.
                        // Please refer to the GoogleSignInStatusCodes class reference for more information.
                        //Log.w(FragmentActivity.TAG, "signInResult:failed code=" + e.statusCode)
                        //updateUI(null)
                        MainActivity.mGoogleSignInClientStatic = null
                        e.printStackTrace()
                        Log.e("UPDATE", "NO UPDATEYU")
                }
        }

        fun trySingUp(nombre: String, email: String){
                val mSingUpManager: SignUpManager = SignUpManager(this)
                val activity: Activity = this
                class ResponseManager() : IResponseManagerGeneric {
                        override fun onSuccesResponse(model: GenericModel) {
                                if (model.error == "0") {
                                        val singUp: SignUpLoginModel =  SignUpLoginModel.Parse(model.json)
                                        UserConfigManager(activity).insertarUserBD(singUp, singUp.email.toString(), activity)
                                        val intent: Intent = Intent(activity, MainScreenActivity::class.java)
                                        activity.startActivity(intent)
                                }else tryLogin(email)
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
                mSingUpManager.realizarOperacion(responseManager, DatosSingUp(nombre, email, email))
        }

        fun tryLogin(email:String){
                val activity: Activity = this
                val loginManager = LoginManager(activity)

                class ResponseManager() : IResponseManagerGeneric {
                        override fun onSuccesResponse(model: GenericModel) {
                                val modelResponse: GenericModel = model
                                if (modelResponse.error == "0") {
                                        val login: SignUpLoginModel = SignUpLoginModel.Parse(modelResponse.json)
                                        UserConfigManager(activity).insertarUserBD(login)
                                        UserConfigManager.reiniciarInfoPersistente(activity)
                                        startActivity(Intent(activity, MainScreenActivity::class.java))
                                }else Toast.makeText(applicationContext, model.mensaje, Toast.LENGTH_LONG).show()
                        }

                        override fun onErrorResponse(model: String) {}
                }

                val responseManager: IResponseManagerGeneric = ResponseManager()
                loginManager.realizarOperacion(responseManager, DatosLogin(email, email))
        }



}