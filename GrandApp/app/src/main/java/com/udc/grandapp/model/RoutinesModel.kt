package com.udc.grandapp.model

import org.json.JSONArray
import org.json.JSONException

class RoutinesModel() {
        lateinit var id: String
        lateinit var nombre: String
        lateinit var descripcion: String
        var hora: Int = 0
        var minuto: Int = 0
        lateinit var dias: String
        lateinit var devices: List<DevicesModel>

        constructor(
                id: String,
                nombre: String,
                descripcion: String,
                hora: Int,
                minuto: Int,
                devices: List<DevicesModel>) : this() {
                this.id = id
                this.nombre = nombre
                this.descripcion = descripcion
                this.hora = hora
                this.minuto = minuto
                this.devices = devices
        }

        companion object{
                fun Parse(json: String): List<RoutinesModel>{
                        val retval: MutableList<RoutinesModel> = arrayListOf()

                        val array: JSONArray = JSONArray(json)
                        for (i in 0 until array.length()) {
                                val url: String = try {
                                        array.getJSONObject(i).get("url").toString()
                                } catch (e: JSONException) {
                                        ""
                                }
                                retval.add(RoutinesModel(
                                        array.getJSONObject(i).get("id").toString(),
                                        array.getJSONObject(i).get("name").toString(),
                                        array.getJSONObject(i).get("description").toString(),
                                        array.getJSONObject(i).get("hour").toString().toInt(),
                                        array.getJSONObject(i).get("minute").toString().toInt(),
                                        listOf()))
                        }

                        return retval

                        //return  listOf(RoutinesModel("1", "Rutina Bacana", "La rutina mas guapa", 16, 0, listOf() ))
                }
        }
}
