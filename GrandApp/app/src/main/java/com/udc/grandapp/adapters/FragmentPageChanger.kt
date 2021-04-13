package com.udc.grandapp.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.udc.grandapp.R
import com.udc.grandapp.fragments.*

class FragmentPageChanger(val fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentTittleList = arrayOf(context.resources.getString(R.string.home), context.resources.getString(R.string.devices), context.resources.getString(R.string.routines), context.resources.getString(R.string.profile), context.resources.getString(R.string.settings))

    override fun getCount(): Int {
        return mFragmentTittleList.size
    }

    override fun getItem(position: Int): Fragment {
        when (position){
            0 -> return Home()
            1 -> return Devices()
            2 -> return Routines()
            3 -> return Profile()
            4 -> return Settings()
            else -> return Blanck()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTittleList[position]
    }
}