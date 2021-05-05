package com.udc.grandapp.manager.listeners

interface IResponseManagerGeneric {
    fun onSuccesResponse(model: Any)
    fun onErrorResponse(model: Any)
}