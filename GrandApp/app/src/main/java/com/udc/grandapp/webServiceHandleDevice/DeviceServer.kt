package com.udc.grandapp.webServiceHandleDevice

import android.content.ContentValues.TAG
import android.util.Log
import com.udc.grandapp.manager.GenericManager
import com.udc.grandapp.model.GenericModel
import okhttp3.*
import java.io.*
import java.net.Socket

open class DeviceServer(url: String, port: Int) {

    private val url: String = url // Esta es la URL del dispositivo a la que conectarse
    private val port: Int = port
    fun doPostRequest(body: String): String? {
        val socket = Socket(url, port)
        val output = PrintWriter(socket.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        output.println(body)
        val line = input.readLine()
        if(line != null) {
            output.flush()
            socket.close()
        }
        return line
    }
}