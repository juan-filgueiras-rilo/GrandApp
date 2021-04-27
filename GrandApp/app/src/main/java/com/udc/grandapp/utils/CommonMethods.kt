package com.udc.grandapp.utils

import android.app.Activity
import android.content.res.Configuration
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommonMethods {

    companion object Factory {
        fun create(): CommonMethods = CommonMethods()
    }

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
}