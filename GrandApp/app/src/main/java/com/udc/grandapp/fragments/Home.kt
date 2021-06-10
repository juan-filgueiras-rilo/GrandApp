package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DeviceSummaryAdapter
import com.udc.grandapp.adapters.RoutinesSummaryAdapter
import com.udc.grandapp.items.CustomerDeviceSummary
import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.manager.GetDevicesManager
import com.udc.grandapp.manager.GetRoutinesManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.utils.CommonMethods

class Home : Fragment() {

    private lateinit var rootView : View
    private lateinit var routineRecyclerView: RecyclerView
    private lateinit var deviceRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_principal, container, false)
        routineRecyclerView = rootView.findViewById<RelativeLayout>(R.id.routine_recycler).findViewById<RecyclerView>(R.id.recycler)
        routineRecyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, routineRecyclerView)

        deviceRecyclerView = rootView.findViewById<RelativeLayout>(R.id.device_recycler).findViewById<RecyclerView>(R.id.recycler)
        deviceRecyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, deviceRecyclerView)

        val deviceSummaryListExample: List<CustomerDeviceSummary> = listOf(CustomerDeviceSummary(1, "NombreDispositivo1", "descripcion1", "url1"),
            CustomerDeviceSummary(2, "NombreDispositivo2", "descripcion2", "url2"),
            CustomerDeviceSummary(3, "NombreDispositivo3", "descripcion3", "url3"))
        deviceRecyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DeviceSummaryAdapter(it, deviceSummaryListExample, it1) {
                Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
            }
            }
        }

        val routineListExample: List<CustomerRoutine> = listOf(CustomerRoutine(1, "NombreRutina1", "descripcion1", "url1"),
                CustomerRoutine(2, "NombreRutina2", "descripcion2", "url2"),
                CustomerRoutine(3, "NombreRutina3", "descripcion3", "url3"))
        routineRecyclerView.adapter = context?.let {
            activity?.let { it1 ->
                RoutinesSummaryAdapter(it, routineListExample, it1) {
                Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
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

    fun getDevices(){
        val mGetDevicesManager: GetDevicesManager = GetDevicesManager(context as Activity)
        val activity: Activity = context as Activity
        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: Any) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: List<DevicesModel> =  DevicesModel.Parse(modelResponse.json)
                    //TODO login?
                    startActivity(Intent(MainScreenActivity::class.simpleName))
                }
                else {
                    //Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                    MaterialAlertDialogBuilder(activity)
                            .setTitle("Error")
                            .setMessage(modelResponse.mensaje)
                            .setNeutralButton("OK") { dialog, which ->
                                // Respond to positive button press
                            }.show()


                }

            }

            override fun onErrorResponse(model: Any) {
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
        val mGetRoutinesManager: GetRoutinesManager = GetRoutinesManager(context as Activity)
        val activity: Activity = context as Activity
        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: Any) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: List<RoutinesModel> =  RoutinesModel.Parse(modelResponse.json)
                    //TODO login?
                    startActivity(Intent(MainScreenActivity::class.simpleName))
                }
                else {
                    //Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                            MaterialAlertDialogBuilder(activity)
                            .setTitle("Error")
                            .setMessage(modelResponse.mensaje)
                            .setNeutralButton("OK"){ dialog, which ->
                                // Respond to positive button press
                            }.show()
                }

            }

            override fun onErrorResponse(model: Any) {
                //Toast.makeText(context, "Error al obtener las rutinas (Diálogo)", Toast.LENGTH_LONG).show()
                MaterialAlertDialogBuilder(activity)
                        .setTitle("Error")
                        .setMessage("Error al obtener las rutinas")
                        .setNeutralButton("OK"){ dialog, which ->
                            // Respond to positive button press
                        }.show()
            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mGetRoutinesManager.realizarOperacion(responseManager, DatosOperacionGeneric())
    }
}