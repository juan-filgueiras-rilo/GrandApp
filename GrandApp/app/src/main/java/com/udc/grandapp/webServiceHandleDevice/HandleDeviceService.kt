package com.udc.grandapp.webServiceHandleDevice

import org.json.JSONArray
import org.json.JSONObject

class HandleDeviceService(url: String, port: Int, private val type: String) : DeviceServer(url, port) {
    fun queryDevice(): String {
        try {
            var body = ""
            if (type == "yeelight") {
                body = "{\"id\":1,\"method\":\"get_prop\",\"params\":[\"power\", \"bright\"]}\r\n"
            }
            val result = doPostRequest(body)
            return (JSONObject(result).get("result") as JSONArray).get(0) as String
        } catch (e:Exception){
            e.printStackTrace()
        }
        return "error"
    }

    fun powerOnDevice() {
        try {
            var body = ""
            if (type == "yeelight") {
                body = "{\"id\": 1, \"method\": \"set_power\", \"params\":[\"on\",\"smooth\",500]}\r\n"
            }
            doPostRequest(body)

        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun powerOffDevice() {
        try {
            var body = ""
            if (type == "yeelight") {
                body = "{\"id\": 1, \"method\": \"set_power\", \"params\":[\"off\",\"smooth\",500]}\r\n"
            }
            doPostRequest(body)

        } catch (e:Exception){
            e.printStackTrace()
        }
    }


}
