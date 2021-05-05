package com.udc.grandapp.utils

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CommonMethods {

    companion object Factory {
        fun clearExistFragments(context: FragmentActivity){
            if (context.supportFragmentManager.backStackEntryCount > 0)
                context.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        fun recyclerViewGridCount(context: FragmentActivity, recyclerView: RecyclerView){
            if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                recyclerView.layoutManager = GridLayoutManager(context, 1)
            else
                recyclerView.layoutManager = GridLayoutManager(context, 2)
        }

        fun comprobarConexion(context: FragmentActivity): Boolean{
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return  (activeNetwork?.isConnectedOrConnecting == true)
        }

        fun isNullOrEmptyObject(obj: Any): Boolean {
            return (obj.equals(""))
        }
    }
}