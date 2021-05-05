package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.GenericModel

class DeleteRoutineService: GrandServer() {

    fun deleteRoutine(): GenericModel {
        return GenericModel("0", "")
    }
}