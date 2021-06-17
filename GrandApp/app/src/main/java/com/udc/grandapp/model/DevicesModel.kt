package com.udc.grandapp.model

import android.util.JsonReader
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DevicesModel() {
    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var url: String
    lateinit var puerto: String
    lateinit var tipo: String

    constructor(id: String, userName: String, descripcion: String, url: String, puerto: String, tipo: String) : this() {
        this.id = id
        this.nombre = userName
        this.descripcion = descripcion
        this.url = url
        this.puerto = puerto
        this.tipo = tipo
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
                        url,
                        array.getJSONObject(i).get("puerto").toString(),
                        array.getJSONObject(i).get("tipo").toString(),
                ))
            }

            return retval
        }

        fun ParseOne(json: String): DevicesModel {
            val json = JSONObject(json)
            val url: String = try {
                json.get("url").toString()
            } catch (e: JSONException) {
                ""
            }

            return DevicesModel(
                    json.get("id").toString(),
                    json.get("name").toString(),
                    json.get("description").toString(),
                    url,
                    json.get("puerto").toString(),
                    json.get("tipo").toString(),
            )
        }

        fun ParseIds(json:String): List<DevicesModel> {
            val array = JSONArray(json)
            val retVal = arrayListOf<DevicesModel>()
            for (i in 0 until array.length()) {
                val obj = JSONObject(array.get(i).toString())
                retVal.add(DevicesModel(obj.get("id").toString(), "", "", "", "", ""))
            }
            return retVal
        }
    }
}
