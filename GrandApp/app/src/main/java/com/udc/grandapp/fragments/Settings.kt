package com.udc.grandapp.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udc.grandapp.R
import com.udc.grandapp.adapters.SettingsAdapter
import com.udc.grandapp.items.SettingsDevice
import com.udc.grandapp.manager.configuration.SharedPreferenceManager
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.custom_lista_ajustes.view.*
import kotlinx.android.synthetic.main.fragment_devices.*

class Settings() : Fragment() {

    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    var listaSettings: List<SettingsDevice> = listOf(SettingsDevice(1, "Modo Oscuro", "Cambiar el tema de la aplicación a Modo Oscuro", false))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharedPreferenceManager.self().getValorParametro(SharedPreferenceManager.getSettings(), "0") == "0"){
            insertarListInSP() //esto solo se hace la primera vez
        }else{
            val listType = object : TypeToken<List<SettingsDevice>>() { }.type
            listaSettings = Gson().fromJson(SharedPreferenceManager.self().getValorParametro(SharedPreferenceManager.getSettings(), "0"), listType)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_w_recycler, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titulo.text = "Ajustes"
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter = context?.let { SettingsAdapter(it, listaSettings, parentFragmentManager) {
            Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG)
        } }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }

    fun insertarListInSP(){
        SharedPreferenceManager.self().setValorParametro(SharedPreferenceManager.getSettings(), Gson().toJson(listaSettings))
    }

}