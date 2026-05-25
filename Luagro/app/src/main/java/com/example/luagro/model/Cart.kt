package com.example.luagro.model

import kotlinx.serialization.Serializable

@Serializable
data class Cart(

    val id_carrito: String? = null,
    val fecha_creacion: String? = null,
    val estado: String = "ABIERTO",
    val id_usuario: String
)