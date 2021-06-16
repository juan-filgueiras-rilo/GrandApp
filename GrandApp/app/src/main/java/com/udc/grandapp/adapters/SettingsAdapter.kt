package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udc.grandapp.R
import com.udc.grandapp.items.SettingsDevice
import com.udc.grandapp.manager.configuration.SharedPreferenceManager
import kotlinx.android.synthetic.main.custom_lista_ajustes.view.*

class SettingsAdapter(context : Context, val items: List<SettingsDevice>, fragmentManager : FragmentManager, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>(){
    private var mContext : Context = context
    private var mItems : List<SettingsDevice> = items
    private var mFragmentManager : FragmentManager = fragmentManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View = LayoutInflater.from(mContext).inflate(R.layout.custom_lista_ajustes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mFragmentManager)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SettingsDevice, listener: (ClipData.Item) -> Unit, fragmentManager: FragmentManager) = with(itemView) {
            nombreDispositivo.text = item.ajuste
            switcher.isChecked = item.check
            switcher.setOnClickListener {
                if (item.id == 1.toLong()){
                    if (switcher.isChecked) {
                        actualizarSP(true, item)
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                    }else {
                        actualizarSP(false, item)
                        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                    }
                }
            }
        }

        fun actualizarSP(checked: Boolean, item : SettingsDevice){
            val listType = object : TypeToken<List<SettingsDevice>>() { }.type
            var settings = Gson().fromJson<List<SettingsDevice>>(SharedPreferenceManager.self().getValorParametro(SharedPreferenceManager.getSettings(), "0"), listType)
            for (setting in settings){
                if (setting.id == item.id)
                    setting.check = checked
            }
            SharedPreferenceManager.self().setValorParametro(SharedPreferenceManager.getSettings(), Gson().toJson(settings))

        }
    }
}
