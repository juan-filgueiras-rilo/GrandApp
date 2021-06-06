package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.manager.UpdateDeviceManager
import com.udc.grandapp.manager.UpdateRoutineManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.UpdateDeviceModel
import com.udc.grandapp.model.UpdateRoutineModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.edit_rutina.*

class UpdateRoutine(layout: Int) : Fragment() {

    private var layout : Int = layout
    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.edit_rutina, container, false)
        rootView.findViewById<Button>(R.id.guardarRutina).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.cancelarRutina).visibility = View.VISIBLE
        rootView.findViewById<Button>(R.id.addDispositivoButton).visibility = View.VISIBLE
        rootView.findViewById<TextView>(R.id.addDispositivoText).visibility = View.VISIBLE
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(layout) {
            1 -> {
                guardarRutina.setOnClickListener {
                    updateRoutines()
                    CommonMethods.clearExistFragments(context as FragmentActivity)
                }
                cancelarRutina.setOnClickListener {
                    Toast.makeText(context, "Cancelar", Toast.LENGTH_LONG).show()
                    CommonMethods.clearExistFragments(context as FragmentActivity)
                }
                addDispositivoButton.setOnClickListener {
                    val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                    ft?.replace(R.id.crearRutina, DeviceList())
                    ft?.addToBackStack("Update Routine")
                    ft?.commit()
                }
            }
            2 -> {
                guardarRutina.setOnClickListener {
                    updateRoutines()
                    (context as FragmentActivity).supportFragmentManager.popBackStack()
                }
                cancelarRutina.setOnClickListener {
                    Toast.makeText(context, "Cancelar", Toast.LENGTH_LONG).show()
                    (context as FragmentActivity).supportFragmentManager.popBackStack()
                }
                addDispositivoButton.setOnClickListener {
                    val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                    ft?.replace(R.id.crearRutina, DeviceList())
                    ft?.addToBackStack("Update Routine")
                    ft?.commit()
                }

            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun updateRoutines(){
        val mUpdateRoutineManager: UpdateRoutineManager = UpdateRoutineManager(context as Activity)
        val activity: Activity = context as Activity
        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: Any) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: UpdateRoutineModel =  UpdateRoutineModel.Parse(modelResponse.json)
                    //TODO login?
                    startActivity(Intent(MainScreenActivity::class.simpleName))
                }
                else {
                    //Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                    activity.runOnUiThread {
                        MaterialAlertDialogBuilder(activity)
                                .setTitle(resources.getString(R.string.error))
                                .setMessage(modelResponse.mensaje)
                                .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                                    // Respond to positive button press
                                }.show()
                    }
                }

            }

            override fun onErrorResponse(model: Any) {
                //Toast.makeText(context, "Error al actualizar los dispositivos (Diálogo)", Toast.LENGTH_LONG).show()
                activity.runOnUiThread { MaterialAlertDialogBuilder(activity)
                        .setTitle(resources.getString(R.string.error))
                        .setMessage(resources.getString(R.string.supporting_textRoutineUpdate))
                        .setNeutralButton(resources.getString(R.string.ok)){ dialog, which ->
                            // Respond to positive button press
                        }.show() }
            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mUpdateRoutineManager.realizarOperacion(responseManager, DatosOperacionGeneric())
    }
}