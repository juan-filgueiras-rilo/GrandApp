package com.udc.grandapp.model

import org.json.JSONArray
import org.json.JSONObject

class DevicesModel() {
    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String

    constructor(id: String, userName: String, descripcion: String) : this() {
        this.id = id
        this.nombre = userName
        this.descripcion = descripcion
    }

    companion object {
        fun Parse(json: String): List<DevicesModel> {
            val retval: MutableList<DevicesModel> = arrayListOf()

            val array: JSONArray = JSONArray(json)
            for (i in 0 until array.length()) {
                retval.add(DevicesModel(
                        array.getJSONObject(i).get("id").toString(),
                        array.getJSONObject(i).get("name").toString(),
                        array.getJSONObject(i).get("description").toString()
                ))
            }

            return retval
        }
    }
}
