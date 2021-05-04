package com.udc.grandapp.webServiceGrandServer

import java.util.*

open class GrandServer {

    private val namespace: String = "GrandApp"

    //Aquí irían los nombres de todos los métodos del web service
    val MetodoLogin: String = "login"


    /*protected open fun buildAuthHeader(user_name: String?, pass_word: String?): Element? {
        val h: Element = Element().createElement(namespace, "AuthHeader")
        val username: Element = Element().createElement(namespace, "UserName")
        username.addChild(Node.TEXT, user_name)
        h.addChild(Node.ELEMENT, username)
        val pass: Element = Element().createElement(namespace, "Password")
        pass.addChild(Node.TEXT, pass_word)
        h.addChild(Node.ELEMENT, pass)
        return h
    }

    protected open fun getPropertyAnyType(res: SoapObject, nombre: String?): String? {
        return comprobarAnyType(res.getProperty(nombre).toString())
    }

    private open fun comprobarAnyType(valor: String): String? {
        return if (valor.toUpperCase(Locale.getDefault()) == "ANYTYPE{}") "" else valor
    }

    protected open fun getNamespace(): String? {
        return namespace
    }

    open fun getPropertyAnyTypeEx(
        res: SoapObject?,
        nombre: String?,
        defaultValue: String?
    ): String? {
        var result = defaultValue
        try {
            if (res != null && nombre != null && !nombre.isEmpty()) {
                if (res.hasProperty(nombre)) {    //si no tenemos la propiedad, no necesitamos hacer nada...
                    val tmp: String = res.getProperty(nombre).toString()
                    if (tmp != null) {
                        result =
                            if (tmp.toUpperCase(Locale.getDefault()) == "ANYTYPE{}") defaultValue else tmp
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }*/


}