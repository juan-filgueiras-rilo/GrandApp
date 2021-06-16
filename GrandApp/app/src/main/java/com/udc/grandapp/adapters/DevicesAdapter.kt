package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.R
import com.udc.grandapp.fragments.UpdateDevice
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.manager.CreateDeviceManager
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosCreateDevice
import com.udc.grandapp.model.CreateDeviceModel
import com.udc.grandapp.model.DevicesModel
import com.udc.grandapp.model.GenericModel
import kotlinx.android.synthetic.main.custom_dispositivo.view.*
import kotlinx.android.synthetic.main.custom_dispositivorutinaview.view.*
import kotlinx.android.synthetic.main.custom_dispositivosrutina.view.*
import kotlinx.android.synthetic.main.custom_lista.view.*
import kotlinx.android.synthetic.main.custom_lista.view.descripcion
import kotlinx.android.synthetic.main.custom_nuevodispositivo.view.*


class DevicesAdapter(context: Context, val items: List<CustomerDevice>, activity: FragmentActivity, layout: Int, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {

    private var mContext: Context = context
    private var mItems: List<CustomerDevice> = items
    private var mActivity: FragmentActivity = activity
    private var mLayout: Int = layout


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(mLayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mLayout, mActivity, mItems, this)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun refresh(position: Int) {
        mItems.drop(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mItems.size);
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerDevice, listener: (ClipData.Item) -> Unit, mLayout: Int, activity: FragmentActivity, items: List<CustomerDevice>, adapter: DevicesAdapter) = with(itemView) {
            when (mLayout) {
                R.layout.custom_dispositivo -> {
                    nombreProducto_cd.text = item.nombre
                    bombilla_cd.setBackgroundColor(context.resources.getColor(R.color.green))
                    consulta.setOnClickListener {
                        val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                        ft.replace(R.id.fragmentDevices, UpdateDevice())
                        ft.addToBackStack("DevicesAdapter")
                        ft.commit()
                    }
                    encender.setOnClickListener {
                        Toast.makeText(context, "Encender/Apagar", Toast.LENGTH_LONG).show()
                    }
                    eliminar.setOnClickListener {
                        MaterialAlertDialogBuilder(context)
                                .setTitle(resources.getString(R.string.titlealert))
                                .setMessage(resources.getString(R.string.supporting_textDevice))
                                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                                    // Respond to negative button press
                                }
                                .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                                    // Respond to positive button press
                                }
                                .show()
                    }
                }
                R.layout.custom_dispositivosrutina -> {
                    nombreDisp.setText(item.nombre)
                    descripciondisp.setText(item.descripcion)
                    eliminardisp.setOnClickListener {
                        //TODO eliminar dispositivo de lista
                    }
                    modificardisp.setOnClickListener {
                        val ft: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                        ft.replace(R.id.crearRutina, UpdateDevice())
                        ft.addToBackStack("DevicesAdapter")
                        ft.commit()
                    }

                }
                R.layout.custom_lista -> {
                    nombreDispositivo.setText(item.nombre)
                    descripcion.setText(item.descripcion)
                    r_customlista.setOnClickListener {
                        //CommonMethods.clearExistFragments(context as FragmentActivity)
                        // Al seleccionar un dispositivo deberíamos poder seguir configurando la rutina
                        (context as FragmentActivity).supportFragmentManager.popBackStack()
                    }
                }

                R.layout.custom_dispositivorutinaview -> {
                    nombreDispo.setText(item.nombre)
                    descripciondispo.setText(item.descripcion)
                }

                R.layout.custom_nuevodispositivo -> {
                    nombreDispositivoEnlaceText.text = item.nombre
                    enlazar.setOnClickListener {
                        activity.runOnUiThread {
                            val inflater = (context as FragmentActivity).layoutInflater;
                            val mView = inflater.inflate(R.layout.add_device_dialog, null);
                            MaterialAlertDialogBuilder(activity)
                                    .setTitle(resources.getString(R.string.guardar_dispositivo_dialog_title))
                                    .setMessage(resources.getString(R.string.guardar_dispositivo_dialog_desc))
                                    .setView(mView)
                                    .setPositiveButton(resources.getString(R.string.guardar)) { dialog, which ->
                                       //if (UserConfigManager.getUserInfoPersistente(activity)!!.userId != null) {
                                           val createDeviceManager = CreateDeviceManager(activity)
                                           val responseManager: IResponseManagerGeneric = object : IResponseManagerGeneric {
                                               override fun onSuccesResponse(model: GenericModel) {
                                                   val modelResponse: GenericModel = model
                                                   Log.e(TAG, modelResponse.toString())
                                                   if (modelResponse.error == "0") {
                                                       val addDevice: CreateDeviceModel = CreateDeviceModel.Parse(modelResponse.json)
                                                       insertarDeviceBD(context, addDevice)
                                                       adapter.refresh(adapterPosition)
                                                   } else Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                                               }

                                               override fun onErrorResponse(model: String) {
                                                   activity.runOnUiThread {
                                                       MaterialAlertDialogBuilder(activity)
                                                           .setTitle(resources.getString(R.string.error))
                                                           .setMessage(model)
                                                           .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                                                               // Respond to positive button press
                                                           }.show()
                                                   }
                                               }
                                           }
                                           /**/
                                           createDeviceManager.realizarOperacion(responseManager, DatosCreateDevice(mView.findViewById<EditText>(R.id.add_name).text.toString(), mView.findViewById<EditText>(R.id.add_description).text.toString(), item.descripcion, "1"/*UserConfigManager.getUserInfoPersistente(activity)!!.userId*/))
                                       //}
                                    }
                                    .setNegativeButton(resources.getString(R.string.cancelar)) { dialog, which ->
                                        Toast.makeText(context, R.string.cancelar, Toast.LENGTH_LONG).show()
                                        // Respond to positive button press
                                    }
                                    .create().show()
                        }
                    }
                    rechazar.setOnClickListener {
                        adapter.refresh(adapterPosition)
                    }
                }
            }
        }
        fun insertarDeviceBD(context: Context, device: CreateDeviceModel){
            val db = UserConfigManager(context).writableDatabase

            val values = ContentValues().apply {
                put("id", device.id)
                put("nombre", device.nombre)
                put("descripcion", device.descripcion)
                put("url", device.url)
            }

            val newRowId = db?.insert("DBDevice", null, values)

            UserConfigManager.reiniciarInfoPersistente(context)
        }
    }
}
