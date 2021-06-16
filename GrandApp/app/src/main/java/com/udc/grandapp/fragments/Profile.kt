package com.udc.grandapp.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.udc.grandapp.EulaActivity
import com.udc.grandapp.R
import com.udc.grandapp.MainActivity
import com.udc.grandapp.manager.configuration.UserConfigManager
import com.udc.grandapp.utils.CommonMethods
import kotlinx.android.synthetic.main.profile.*

class Profile : Fragment() {
    private lateinit var rootView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.profile, container, false)
        var editText : EditText = rootView.findViewById<EditText>(R.id.editTextNombre)
        editText.setText(UserConfigManager.infoPersistente!!.userName)
        rootView.findViewById<Button>(R.id.buttonGuardarCambios).setOnClickListener(View.OnClickListener {
            if (!editText.text.toString().equals(UserConfigManager.infoPersistente!!.userName)){
                Toast.makeText(context, "Cambios guardados", Toast.LENGTH_LONG).show()
                UserConfigManager(activity as Context).actualizarUserName(UserConfigManager.infoPersistente!!, editText.text!!.toString(), activity as Activity)
            }else
                Toast.makeText(context, "No hay cambios para guardar", Toast.LENGTH_LONG).show()
        })
        rootView.findViewById<EditText>(R.id.editTextNombre).setText(UserConfigManager.infoPersistente!!.userName)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagCerrarSesion.setOnClickListener {
            val db = UserConfigManager(context as FragmentActivity).writableDatabase

            val newRowId = db?.delete("DBUser", null, null)
            val newRowId2 = db?.delete("DBDevice", null, null)
            val newRowId3 = db?.delete("DBRoutine", null, null)
            UserConfigManager.reiniciarInfoPersistente(context as FragmentActivity)
            CommonMethods.clearExistFragments(context as FragmentActivity)
            val intent: Intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)


        }
        tagAcuerdoUsuario.setOnClickListener {
            val intent = Intent(this.activity, EulaActivity::class.java)
            val bundle = Bundle()
            val eula = R.raw.eula
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            startActivityForResult(intent, 1)
        }
    }

}