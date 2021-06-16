package com.udc.grandapp.model

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DevicesModel() {
    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var url: String

    constructor(id: String, userName: String, descripcion: String, url: String) : this() {
        this.id = id
        this.nombre = userName
        this.descripcion = descripcion
        this.url = url
    }

    companion object {
        fun Parse(json: String): List<DevicesModel> {
            val retval: MutableList<DevicesModel> = arrayListOf()

            val array: JSONArray = JSONArray(json)
            for (i in 0 until array.length()) {
                val url: String = try {
                    array.getJSONObject(i).get("url").toString()
                } catch (e: JSONException) {
                    ""
                }
                retval.add(DevicesModel(
                        array.getJSONObject(i).get("id").toString(),
                        array.getJSONObject(i).get("name").toString(),
                        array.getJSONObject(i).get("description").toString(),
                        url
                ))
            }

            return retval
        }
    }
}
