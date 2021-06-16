package com.udc.grandapp.manager.configuration

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager() {
    var mContext: Context? = null

    companion object {
        private val SETTINGS: String = "SETTINGS"
        var prefs: SharedPreferenceManager? = null
        fun self(): SharedPreferenceManager{
            if (prefs == null)
                prefs = SharedPreferenceManager()
            return prefs as SharedPreferenceManager
        }

        fun getSettings(): String{
            return SETTINGS
        }
    }

    fun reset(context: Context){
        mContext = context
    }

    fun getValorParametro(parametro: String, valorDefecto: String): String{
        var result: String = valorDefecto

        try {
            val valorResult: String? = getValorParametroPrivate(parametro)
            if (valorResult == null)
                result = valorDefecto
            else
                result = valorResult
        }catch (e: Exception){
            result = valorDefecto
        }
        return result
    }

    fun getValorParametroPrivate (parametro: String): String? {
        var result: String? = null
        try {
            val sharedPrefs: SharedPreferences = mContext!!.getSharedPreferences(parametro, Context.MODE_PRIVATE)
            result = sharedPrefs.getString(parametro, null)

        }catch (e: Exception){
            result = null
        }

        return result
    }

    fun setValorParametro(parametro: String, valor: String) {
        try {
            if (!parametro.isEmpty()) {
                val sharedPrefs: SharedPreferences = mContext!!.getSharedPreferences(parametro, Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPrefs.edit()
                editor.putString(parametro, valor)
                editor.apply()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    /*Ejemplos de uso
    Recuperar valor : SharedPreferenceManager.self().getValorParametro(SharedPreferenceManager.KEY_TOKEN, "Default_value")
    Escribir valor: SharedPreferenceManager.self().setValorParametro(SharedPreferenceManager.KEY_TOKEN, "Valor")
     */

}