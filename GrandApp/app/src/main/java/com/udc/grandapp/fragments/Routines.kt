package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.adapters.RoutinesAdapter
import com.udc.grandapp.items.CustomerRoutine
import com.udc.grandapp.manager.GetRoutinesManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosOperacionGeneric
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.fragment_routines.*

class Routines : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_routines, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        var lista: List<CustomerRoutine> = listOf(
            CustomerRoutine(1, "Rutina 1", "Descripción de la rutina 1", "url1"),
            CustomerRoutine(2, "Rutina 2", "Descripción de la rutina 2", "url2"),
            CustomerRoutine(3, "Rutina 3", "Descripción de la rutina 3", "url3"))
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                RoutinesAdapter(it, lista, it1) {
                Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
            }
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titulo.text = activity?.resources?.getString(R.string.mis_rutinas)
        addRoutine.setOnClickListener {
            val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.fragmentRoutines, AddRoutine())
            ft?.addToBackStack("Routines")
            ft?.commit()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun getRoutines(){
        val mGetRoutinesManager: GetRoutinesManager = GetRoutinesManager(context as Activity)

        class ResponseManager() : IResponseManagerGeneric {
            override fun onSuccesResponse(model: Any) {
                val modelResponse: GenericModel = model as GenericModel
                if (modelResponse.error == "0") {
                    val devices: List<RoutinesModel> =  RoutinesModel.Parse(modelResponse.json)
                    //TODO login?
                    startActivity(Intent(MainScreenActivity::class.simpleName))
                }
                else Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()

            }

            override fun onErrorResponse(model: Any) {
                Toast.makeText(context, "Error al obtener las rutinas (Diálogo)", Toast.LENGTH_LONG).show()
            }
        }

        val responseManager: IResponseManagerGeneric = ResponseManager()
        mGetRoutinesManager.realizarOperacion(responseManager, DatosOperacionGeneric())
    }
}