package com.udc.grandapp.webServiceGrandServer;

import com.udc.grandapp.model.CreateRoutineModel

public class CreateRoutineService(): GrandServer() {

        fun createRoutine(): CreateRoutineModel {
            return CreateRoutineModel("nombre")
        }
}