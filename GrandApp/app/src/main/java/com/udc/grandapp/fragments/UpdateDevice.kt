package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.adapters.ViewRoutineAdapter
import com.udc.grandapp.items.RoutinesDevice
import com.udc.grandapp.manager.GetDevicesManager
import com.udc.grandapp.manager.UpdateDeviceManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.UpdateDeviceModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.fragment_editdevice.*

class UpdateDevice  : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_editdevice, container, false)
        rootView.findViewById<Button>(R.id.aceptar).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.cancelar).visibility = View.VISIBLE
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        val listaRutinas: List<RoutinesDevice> = listOf(RoutinesDevice(1, "Rutina 1", "Descripción"),
                                                        RoutinesDevice(2, "Rutina 2", "Descripción"))
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                ViewRoutineAdapter(it, listaRutinas, it1, 2) { Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText.hint = "Bombilla 1"
        aceptar.setOnClickListener {
            updateDevices()
            CommonMethods.clearExistFragments(context as FragmentActivity)
        }
        cancelar.setOnClickListener {
            CommonMethods.clearExistFragments(context as FragmentActivity)
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun updateDevices(){
        val mUpdateDeviceManager: UpdateDeviceManager = UpdateDeviceManager(context as Activity)

        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: Any) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: UpdateDeviceModel =  UpdateDeviceModel.Parse(modelResponse.json)
                    //TODO login?
                    startActivity(Intent(MainScreenActivity::class.simpleName))
                }
                else Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()

            }

            override fun onErrorResponse(model: Any) {
                Toast.makeText(context, "Error al actualizar los dispositivos (Diálogo)", Toast.LENGTH_LONG).show()
            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mUpdateDeviceManager.realizarOperacion(responseManager, DatosOperacionGeneric())
    }
}
