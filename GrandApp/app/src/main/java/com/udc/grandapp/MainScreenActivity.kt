package com.udc.grandapp

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.udc.grandapp.adapters.FragmentPageChanger
import com.udc.grandapp.fragments.Devices
import com.udc.grandapp.fragments.Routines
import com.udc.grandapp.manager.GetDevicesManager
import com.udc.grandapp.manager.GetRoutinesManager
import com.udc.grandapp.manager.configuration.SharedPreferenceManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.utils.CommonMethods


class MainScreenActivity : AppCompatActivity() {

    private lateinit var toolbar : Toolbar
    private lateinit var  tabLayout: TabLayout
    private lateinit var  viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferenceManager.self().reset(applicationContext)

        //llamar a los getRutinas y getDevice
        getDevices()
        getRoutines()
        //Guardarlos en la SQLite en el onSuccess de los managers

        //En el onCreateView se recuperan de la SQLite y se muestran

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.view_pager)

        initTabLayout()
        /*Intent(this, RoutineAlarmService::class.java).also { intent ->
            startService(intent) //TODO DESCOMENTAR ESTO
        }*/
        supportFragmentManager.addOnBackStackChangedListener {
            val f: Fragment? = supportFragmentManager.findFragmentById(R.id.fragmentDevices)
            if (f != null) {
                updateTitleAndDrawer(f)
            }
        }

    }

    private fun updateTitleAndDrawer(fragment: Fragment) {
        val fragClassName = fragment.javaClass.name
        if (fragClassName == Devices::class.java.getName()) {
            (fragClassName as Devices).reload()
            //set selected item position, etc
        }
        else if (fragClassName == Routines::class.java.getName()) {
            (fragClassName as Routines).reload()
            //set selected item position, etc
        }/* else if (fragClassName == C::class.java.getName()) {
            title = "C"
            //set selected item position, etc
        }*/
    }
    private fun initTabLayout(){
        setSupportActionBar(toolbar)

        val onTabSelectedListener: OnTabSelectedListener = object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                CommonMethods.clearExistFragments(this@MainScreenActivity)
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                CommonMethods.clearExistFragments(this@MainScreenActivity)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                CommonMethods.clearExistFragments(this@MainScreenActivity)
                viewPager.currentItem = tab.position
            }
        }

        viewPager.adapter = FragmentPageChanger(supportFragmentManager, this)
        tabLayout.addOnTabSelectedListener(onTabSelectedListener)
        tabLayout.setupWithViewPager(viewPager)

    }

    fun getDevices(){
        val mGetDevicesManager = GetDevicesManager(this)
        val activity: Activity = this
        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: GenericModel) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: List<DevicesModel> =  DevicesModel.Parse(modelResponse.json)
                    UserConfigManager(activity).deleteDevicesFromBD()
                    UserConfigManager(activity).insertarDeviceBBDD(devices)
                }
                else {
                    //Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                    activity.runOnUiThread {
                        MaterialAlertDialogBuilder(activity)
                                .setTitle("Error")
                                .setMessage(modelResponse.mensaje)
                                .setNeutralButton("OK") { dialog, which ->
                                    // Respond to positive button press
                                }.show()
                    }
                }
            }

            override fun onErrorResponse(model: String) {
                //Toast.makeText(context, "Error al obtener los dispositivos (Diálogo)", Toast.LENGTH_LONG).show()
                MaterialAlertDialogBuilder(activity)
                        .setTitle("Error")
                        .setMessage("Al obtener los dispositivos")
                        .setNeutralButton("OK") { dialog, which ->
                            // Respond to positive button press
                        }.show()
            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mGetDevicesManager.realizarOperacion(responseManager, DatosOperacionGeneric())

    }

    fun getRoutines(){
        val mGetRoutinesManager: GetRoutinesManager = GetRoutinesManager(this)
        val activity: Activity = this
        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: GenericModel) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val routines: List<RoutinesModel> =  RoutinesModel.Parse(modelResponse.json)
                    UserConfigManager(activity).deleteRoutinesFromBD()
                    UserConfigManager(activity).insertarRoutinesBBDD(routines)
                }
                else {
                    //Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                    activity.runOnUiThread {
                        MaterialAlertDialogBuilder(activity)
                            .setTitle("Error")
                            .setMessage(modelResponse.mensaje)
                            .setNeutralButton("OK"){ dialog, which ->
                                // Respond to positive button press
                            }.show()
                    }
                }
            }

            override fun onErrorResponse(model: String) {
                //Toast.makeText(context, "Error al obtener las rutinas (Diálogo)", Toast.LENGTH_LONG).show()
                activity.runOnUiThread {
                    MaterialAlertDialogBuilder(activity)
                            .setTitle("Error")
                            .setMessage("Error al obtener las rutinas")
                            .setNeutralButton("OK"){ dialog, which ->
                                // Respond to positive button press
                            }.show()
                }
            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mGetRoutinesManager.realizarOperacion(responseManager, DatosOperacionGeneric())
    }
}