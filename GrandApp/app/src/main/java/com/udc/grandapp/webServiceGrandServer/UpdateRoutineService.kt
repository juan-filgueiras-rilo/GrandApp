package com.udc.grandapp.webServiceGrandServer

 import com.udc.grandapp.model.UpdateRoutineModel

class UpdateRoutineService: GrandServer() {

    fun updateProfile(): UpdateRoutineModel {
        return UpdateRoutineModel("nombre")
    }
}