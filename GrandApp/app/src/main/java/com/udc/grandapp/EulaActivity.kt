package com.udc.grandapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.material.snackbar.Snackbar
import com.udc.grandapp.R
import kotlinx.android.synthetic.main.activity_eula.*
import java.io.InputStream


class EulaActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = applicationContext
            .getSharedPreferences("com.udc.grandapp", Context.MODE_PRIVATE)
        setContentView(R.layout.activity_eula)
        initializeUI()
    }

    private fun initializeUI() {
        eula_cancel.setOnClickListener { cancelEULA() }
        eula_confirm.setOnClickListener {
            confirmEULA()
        }
        setEula()
    }

    private fun setEula() {
        val inputStream: InputStream = resources.openRawResource(R.raw.eula)
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes)
        val htmlAsSpanned: Spanned =
            HtmlCompat.fromHtml(String(bytes), HtmlCompat.FROM_HTML_MODE_LEGACY)
        eula_content.text = htmlAsSpanned
    }

    private fun cancelEULA() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "You must accept the EULA to continue",
            Snackbar.LENGTH_LONG
        ).show()
    }

    // NOTE: Here you would call your api to save the status
    private fun confirmEULA() {
        preferences.edit().putBoolean("eulaAccepted", true).apply()
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }
}