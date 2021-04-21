package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.udc.grandapp.R
import com.udc.grandapp.items.SettingsDevice
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
            switcher.setOnClickListener {
                Toast.makeText(context, "Ajuste seleccionado", Toast.LENGTH_LONG).show()
            }
        }
    }
}
