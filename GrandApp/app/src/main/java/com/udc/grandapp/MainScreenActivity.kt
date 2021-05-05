package com.udc.grandapp

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.udc.grandapp.adapters.FragmentPageChanger
import com.udc.grandapp.utils.CommonMethods

class MainScreenActivity : AppCompatActivity() {

    private lateinit var toolbar : Toolbar
    private lateinit var  tabLayout: TabLayout
    private lateinit var  viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.view_pager)

        initTabLayout()
    }

    fun initTabLayout(){
        setSupportActionBar(toolbar)

        val onTabSelectedListener: OnTabSelectedListener = object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                CommonMethods.clearExistFragments(this@MainScreenActivity)
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {
                CommonMethods.clearExistFragments(this@MainScreenActivity)
                viewPager.currentItem = tab.position
            }
        }

        viewPager.adapter = FragmentPageChanger(supportFragmentManager, this)
        tabLayout.setupWithViewPager(viewPager)
    }

}