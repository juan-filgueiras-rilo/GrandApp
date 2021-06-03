package com.udc.grandapp.manager

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.R
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.UserInfoModel
import com.udc.grandapp.utils.CommonMethods
import java.lang.Exception

open class GenericManager(activity: Activity) {
    lateinit var mWorker: Thread
    var mDialogPopup: Dialog? = null
    var mInfoBD: UserInfoModel? = UserConfigManager.getUserInfoPersistente(activity)
    var mActivity: Activity = activity


    init {
        if (!(CommonMethods.comprobarConexion(activity as FragmentActivity)))
            Toast.makeText(activity, "Error con la conexión a internet", Toast.LENGTH_LONG).show()
    }

    open fun infoBd():UserInfoModel? {
        return mInfoBD
    }

    companion object {
        var error: String = ""
        data class DatosThreaded(
                var mActivity: Activity,
                var mCallBack: IResponseManagerGeneric,
                var mDatosOperaction: DatosOperacionGeneric,
                var mResultado: Any
        )

        fun onPreExecute(datos:DatosThreaded){
            try {
                //Toast.makeText(datos.mActivity, "PopUp Cargando", Toast.LENGTH_LONG).show()
                val progressBar = datos.mActivity.window.decorView.findViewById<ProgressBar>(R.id.progressBar)
                progressBar.visibility = View.VISIBLE
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        fun onPostExecute(datos:DatosThreaded){
            try {
                try {
                    //Toast.makeText(datos.mActivity, "Cerrar diálogo cargando", Toast.LENGTH_LONG).show()
                    val progressBar = datos.mActivity.window.decorView.findViewById<ProgressBar>(R.id.progressBar)
                    progressBar.visibility = View.GONE
                }catch (e:Exception){
                    e.printStackTrace()
                }

                if (!CommonMethods.isNullOrEmptyObject(datos.mResultado) || !CommonMethods.isNullOrEmptyObject(datos.mCallBack as Any)){
                    datos.mCallBack.onSuccesResponse(datos.mResultado)
                }else if (!CommonMethods.isNullOrEmptyObject(error as Any) && !CommonMethods.isNullOrEmptyObject(datos.mCallBack as Any)){
                    datos.mCallBack.onErrorResponse(datos.mResultado)
                }
            }catch (e:Exception){
                //Toast.makeText(datos.mActivity, "Error procesando la petición", Toast.LENGTH_LONG).show()

                if (!CommonMethods.isNullOrEmptyObject(datos.mCallBack as Any))
                    datos.mCallBack.onErrorResponse("Error procesando la petición" as Any)
            }

        }
    }

    fun realizarOperacion(response: IResponseManagerGeneric, datosOperacion: DatosOperacionGeneric){
        try {
            var datos: DatosThreaded = DatosThreaded(mActivity, response, datosOperacion, Object())

            mActivity.runOnUiThread(Runnable {
                try {
                   onPreExecute(datos)
                }catch (e:Exception){

                }
            })


            mWorker = Thread(Runnable {
                try {
                    try {
                        onWorkerExecute(datos)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    mActivity.runOnUiThread(Runnable { onPostExecute(datos) })
                }catch (e:Exception){
                    e.printStackTrace()
                }

            })
            mWorker.start()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    open fun onWorkerExecute(datos:DatosThreaded){
        //redefinir siempre en las subclases
    }


    fun cancelar(){
        try {
            if (mWorker.isAlive){
                mWorker.interrupt()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}