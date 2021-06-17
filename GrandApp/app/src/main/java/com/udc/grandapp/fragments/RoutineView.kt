package com.udc.grandapp.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import ca.antonious.materialdaypicker.MaterialDayPicker
import com.udc.grandapp.R
import com.udc.grandapp.adapters.DevicesAdapter
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.model.RoutinesModel
import com.udc.grandapp.utils.CommonMethods

class RoutineView(item: RoutinesModel) : Fragment() {

    private var routine : RoutinesModel = item
    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.edit_rutina, container, false)
        rootView.findViewById<LinearLayout>(R.id.dosBotones).visibility = View.GONE
        rootView.findViewById<LinearLayout>(R.id.addDispositivo).visibility = View.GONE
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)

        rootView.findViewById<TextView>(R.id.editTextNombre).text = routine.nombre
        rootView.findViewById<TextView>(R.id.editTextDescripcion).text = routine.descripcion
        rootView.findViewById<TimePicker>(R.id.datePicker1).hour = routine.hora
        rootView.findViewById<TimePicker>(R.id.datePicker1).minute = routine.minuto

        for (dia in routine.dias) {
            rootView.findViewById<MaterialDayPicker>(R.id.dayPicker).selectDay(getWeekDay(dia))
        }

        val listaDispositivos: MutableList<CustomerDevice> = arrayListOf()
        for (dispositivo in routine.devices) {
            listaDispositivos.add(CustomerDevice(
                1,
                dispositivo.nombre,
                dispositivo.descripcion,
                dispositivo.url,
                1,
                dispositivo.tipo))
        }
        recyclerView.adapter = context?.let {
            activity?.let { it1 ->
                DevicesAdapter(it, listaDispositivos as ArrayList<CustomerDevice>, it1, R.layout.custom_dispositivorutinaview, {
                    Toast.makeText(context, "${it.text} Clicked", Toast.LENGTH_LONG).show()
                }, this)
            }
        }

        return rootView
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        CommonMethods.recyclerViewGridCount(context as FragmentActivity, recyclerView)
    }


    fun getWeekDay(dia : String) : MaterialDayPicker.Weekday {
        if (dia == (MaterialDayPicker.Weekday.SUNDAY.name)) {
            return MaterialDayPicker.Weekday.SUNDAY
        } else if (dia == (MaterialDayPicker.Weekday.MONDAY.name)) {
            return MaterialDayPicker.Weekday.MONDAY
        } else if (dia == MaterialDayPicker.Weekday.TUESDAY.name) {
            return MaterialDayPicker.Weekday.TUESDAY
        } else if (dia == (MaterialDayPicker.Weekday.WEDNESDAY.name)) {
            return MaterialDayPicker.Weekday.WEDNESDAY
        } else if (dia == (MaterialDayPicker.Weekday.THURSDAY.name)) {
            return MaterialDayPicker.Weekday.THURSDAY
        } else if (dia == (MaterialDayPicker.Weekday.FRIDAY.name)) {
            return MaterialDayPicker.Weekday.FRIDAY
        } else if (dia == (MaterialDayPicker.Weekday.SATURDAY.name)) {
            return MaterialDayPicker.Weekday.SATURDAY
        }
        return MaterialDayPicker.Weekday.SATURDAY;
    }
}