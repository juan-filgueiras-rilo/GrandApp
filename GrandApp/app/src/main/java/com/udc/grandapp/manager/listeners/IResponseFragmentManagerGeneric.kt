package com.udc.grandapp.manager.listeners

import com.udc.grandapp.items.CustomerDevice
import com.udc.grandapp.model.GenericModel

interface IResponseFragmentManagerGeneric {
    fun onSuccesResponse(device: CustomerDevice)
    fun onErrorResponse(model: String)
}