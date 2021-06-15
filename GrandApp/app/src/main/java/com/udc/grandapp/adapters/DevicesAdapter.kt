package com.udc.grandapp.adapters

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.udc.grandapp.MainScreenActivity
import com.udc.grandapp.R
import com.udc.grandapp.fragments.UpdateDevice
import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.manager.CreateDeviceManager
import com.udc.grandapp.manager.listeners.IResponseManagerGeneric
import com.udc.grandapp.manager.transferObjects.DatosCreateDevice
import com.udc.grandapp.model.CreateDeviceModel
import com.udc.grandapp.model.GenericModel
import com.udc.grandapp.model.SignUpLoginModel
import kotlinx.android.synthetic.main.add_device_dialog.view.*
import kotlinx.android.synthetic.main.custom_dispositivo.view.*
import kotlinx.android.synthetic.main.custom_enlazar.view.*
import kotlinx.android.synthetic.main.custom_lista.view.*


class DevicesAdapter(context : Context, val items: List<CustomerDevice>, activity : FragmentActivity, layout: Int, val listener: (ClipData.Item) -> Unit) : RecyclerView.Adapter<DevicesAdapter.ViewHolder>(){

    private var mContext : Context = context
    private var mItems : List<CustomerDevice> = items
    private var mActivity : FragmentActivity = activity
    private var mLayout : Int = layout


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(mLayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener, mLayout, mActivity)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CustomerDevice, listener: (ClipData.Item) -> Unit, mLayout: Int, activity: FragmentActivity) = with(itemView) {
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
                R.layout.custom_lista -> {
                    nombreDispositivo.setText(item.nombre)
                    descripcion.setText(item.url)
                    r_customlista.setOnClickListener{
                        //CommonMethods.clearExistFragments(context as FragmentActivity)
                        // Al seleccionar un dispositivo deberíamos poder seguir configurando la rutina
                        (context as FragmentActivity).supportFragmentManager.popBackStack()
                    }
                }
                R.layout.custom_nuevodispositivo -> {
                    nombreDispositivoEnlaceText.text = item.nombre
                    enlazar.setOnClickListener {
                        activity.runOnUiThread {
                            MaterialAlertDialogBuilder(activity)
                                .setTitle(resources.getString(R.string.guardar_dispositivo_dialog_title))
                                .setMessage(resources.getString(R.string.guardar_dispositivo_dialog_desc))
                                .setView(R.layout.add_device_dialog)
                                .setPositiveButton(resources.getString(R.string.guardar)) { dialog, which ->
                                    val createDeviceManager = CreateDeviceManager(activity)
                                    val responseManager: IResponseManagerGeneric = object : IResponseManagerGeneric {
                                        override fun onSuccesResponse(model: Any) {
                                            val modelResponse: GenericModel = model as GenericModel
                                            if (modelResponse.error == "0") {
                                                //val addDevice: CreateDeviceModel = CreateDeviceModel.Parse(modelResponse.json)
                                            } else Toast.makeText(context, modelResponse.mensaje, Toast.LENGTH_LONG).show()
                                        }

                                        override fun onErrorResponse(model: Any) {
                                            activity.runOnUiThread {
                                                MaterialAlertDialogBuilder(activity)
                                                        .setTitle(resources.getString(R.string.error))
                                                        .setMessage(resources.getString(R.string.supporting_textlogin))
                                                        .setNeutralButton(resources.getString(R.string.ok)) { dialog, which ->
                                                            // Respond to positive button press
                                                        }.show()
                                            }
                                        }
                                    }
                                    val inflater = (context as FragmentActivity).layoutInflater;
                                    val mView = inflater.inflate(R.layout.add_device_dialog, null);
                                    val addName = (mView.findViewById(R.id.add_name) as EditText);
                                    val addDescription = (mView.findViewById(R.id.add_description) as EditText);
                                    //TODO userId
                                    createDeviceManager.realizarOperacion(responseManager, DatosCreateDevice(addName.text.toString(), addDescription.text.toString(), item.url, 1))
                                }
                                .setNegativeButton(resources.getString(R.string.cancelar)) { dialog, which ->
                                    Toast.makeText(context, "Operación cancelada", Toast.LENGTH_LONG).show()
                                    // Respond to positive button press
                                }
                                .show()
                        }
                    }
                }
            }
        }
    }
}