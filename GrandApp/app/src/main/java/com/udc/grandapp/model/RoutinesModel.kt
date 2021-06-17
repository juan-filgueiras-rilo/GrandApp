package com.udc.grandapp.model

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RoutinesModel() {
        lateinit var id: String
        lateinit var nombre: String
        lateinit var descripcion: String
        var hora: Int = 0
        var minuto: Int = 0
        lateinit var devices: List<DevicesModel>
        lateinit var dias: List<String>

        constructor(
                id: String,
                nombre: String,
                descripcion: String,
                hora: Int,
                minuto: Int,
                devices: List<DevicesModel>,
                dias: List<String>) : this() {
                this.id = id
                this.nombre = nombre
                this.descripcion = descripcion
                this.hora = hora
                this.minuto = minuto
                this.devices = devices
                this.dias = dias
        }

        companion object{
                fun Parse(json: String): List<RoutinesModel>{
                        val retval: MutableList<RoutinesModel> = arrayListOf()

                        val array: JSONArray = JSONArray(json)
                        for (i in 0 until array.length()) {
                                retval.add(RoutinesModel(
                                        array.getJSONObject(i).get("id").toString(),
                                        array.getJSONObject(i).get("name").toString(),
                                        array.getJSONObject(i).get("description").toString(),
                                        array.getJSONObject(i).get("hour").toString().toInt(),
                                        array.getJSONObject(i).get("minute").toString().toInt(),
                                        getDispositivos(array.getJSONObject(i)),
                                        getDias(array.getJSONObject(i))))

                        }

                        return retval
                }
                fun ParseList(json: String): List<RoutinesModel>{
                        return Parse("[$json]")
                }
                fun getDispositivos(routine : JSONObject): MutableList<DevicesModel> {
                        val retval: MutableList<DevicesModel> = arrayListOf()
                                retval.addAll(DevicesModel.ParseIds(routine.get("deviceList").toString()))
                        return retval
                }

                fun getDias(routine : JSONObject): MutableList<String> {
                        val retval: MutableList<String> = arrayListOf()
                        val array: JSONArray = (routine.get("dias") as JSONArray)
                        for (i in 0 until array.length()) {
                                retval.add(array.get(i).toString())
                        }
                        return retval
                }
        }
}
