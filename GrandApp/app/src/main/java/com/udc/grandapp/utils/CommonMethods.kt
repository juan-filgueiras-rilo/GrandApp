package com.udc.grandapp.utils

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class CommonMethods {

    companion object Factory {
        fun create(): CommonMethods = CommonMethods()
    }

    fun clearExistFragments(context : FragmentActivity){
        if (context.supportFragmentManager.backStackEntryCount > 0)
            context.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}