package com.udc.grandapp.model

import org.json.JSONObject

data class CreateRoutineModel(
        var id: String,
        var name: String,
        var description: String,
        var dispositivo: String,
        var userId: String,
        var hour: String,
        var minute: String,
        var days: String

        ){

        companion object{
                fun Parse(json: String): CreateRoutineModel {
                        return CreateRoutineModel(
                                JSONObject(json)["id"].toString(),
                                JSONObject(json)["nombre"].toString(),
                                JSONObject(json)["description"].toString(),
                                JSONObject(json)["dispositivo"].toString(),
                                JSONObject(json)["userId"].toString(),
                                JSONObject(json)["hour"].toString(),
                                JSONObject(json)["minute"].toString(),
                                JSONObject(json)["days"].toString()
                        )
                }
        }
}
