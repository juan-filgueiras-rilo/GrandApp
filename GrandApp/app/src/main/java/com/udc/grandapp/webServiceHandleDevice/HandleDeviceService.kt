package com.udc.grandapp.webServiceHandleDevice

class HandleDeviceService(url: String, port: Int, private val type: String) : DeviceServer(url, port) {
    fun queryDevice() {
        try {
            var body = ""
            if (type == "yeelight") {
                body = "{\"id\":1,\"method\":\"get_prop\",\"params\":[\"power\", \"bright\"]}\r\n"
            }
            doPostRequest(body)

        } catch (e:Exception){
            e.printStackTrace()
        }
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
