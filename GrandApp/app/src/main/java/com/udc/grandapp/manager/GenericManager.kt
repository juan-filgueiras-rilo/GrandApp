package com.udc.grandapp.manager

import android.app.Activity
import android.app.Dialog
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.UserInfoModel
import com.udc.grandapp.utils.CommonMethods
import java.lang.Exception

open class GenericManager(activity: Activity) {
    var error: String = ""
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
        data class DatosThreaded(
                var mActivivity: Activity,
                var mCallBack: IResponseManagerGeneric,
                var mDatosOperaction: DatosOperacionGeneric,
                var mResultado: Any
        )
    }

    fun onPreExecute(datos:DatosThreaded){
        try {
            Toast.makeText(datos.mActivivity, "PopUp Cargando", Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            e.printStackTrace()
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

    fun onPostExecute(datos:DatosThreaded){
        try {
            try {
                Toast.makeText(datos.mActivivity, "Cerrar diálogo cargando", Toast.LENGTH_LONG).show()
            }catch (e:Exception){
                e.printStackTrace()
            }

            if (!CommonMethods.isNullOrEmptyObject(datos.mResultado) || !CommonMethods.isNullOrEmptyObject(datos.mCallBack as Any)){
                datos.mCallBack.onSuccesResponse(datos.mResultado)
            }else if (!CommonMethods.isNullOrEmptyObject(error as Any) && !CommonMethods.isNullOrEmptyObject(datos.mCallBack as Any)){
                datos.mCallBack.onErrorResponse(datos.mResultado)
            }
        }catch (e:Exception){
            Toast.makeText(datos.mActivivity, "Error procesando la petición", Toast.LENGTH_LONG).show()
            if (!CommonMethods.isNullOrEmptyObject(datos.mCallBack as Any))
                datos.mCallBack.onErrorResponse("Error procesnado la petición" as Any)
        }

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