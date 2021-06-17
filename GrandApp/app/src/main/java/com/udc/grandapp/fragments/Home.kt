package com.udc.grandapp.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DeviceSummaryAdapter
import com.udc.grandapp.adapters.RoutinesSummaryAdapter
import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.utils.CommonMethods

class Home : Fragment() {

    private lateinit var rootView : View
    private lateinit var routineRecyclerView: RecyclerView
    private lateinit var deviceRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_principal, container, false)
        routineRecyclerView = rootView.findViewById<RelativeLayout>(R.id.routine_recycler).findViewById<RecyclerView>(R.id.recycler)
        routineRecyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, routineRecyclerView)

        deviceRecyclerView = rootView.findViewById<RelativeLayout>(R.id.device_recycler).findViewById<RecyclerView>(R.id.recycler)
        deviceRecyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, deviceRecyclerView)


        val deviceSummaryListExample: List<DevicesModel> = UserConfigManager(context as FragmentActivity).getDevicesFromBD()
        deviceRecyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DeviceSummaryAdapter(it, deviceSummaryListExample, it1) {
                //Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }
            }
        }

        val routineListExample: List<RoutinesModel> = UserConfigManager(context as FragmentActivity).getRoutinesFromBD()
        routineRecyclerView.adapter = context?.let {
            activity?.let { it1 ->
                RoutinesSummaryAdapter(it, routineListExample, it1, R.layout.custom_rutina) {
                    //Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }
            }
        }
        return rootView
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, routineRecyclerView)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, deviceRecyclerView)
    }


}