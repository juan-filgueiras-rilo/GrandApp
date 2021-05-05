package com.udc.grandapp.webServiceGrandServer

import com.udc.grandapp.model.UpdateProfileModel

class UpdateProfileService: GrandServer() {

    fun updateProfile(): UpdateProfileModel {
        return UpdateProfileModel("nombre")
    }
}