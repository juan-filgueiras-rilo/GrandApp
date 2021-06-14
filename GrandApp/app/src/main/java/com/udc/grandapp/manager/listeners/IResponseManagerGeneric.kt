package com.udc.grandapp.manager.listeners

import com.udc.grandapp.model.GenericModel

interface IResponseManagerGeneric {
    fun onSuccesResponse(model: GenericModel)
    fun onErrorResponse(model: String)
}