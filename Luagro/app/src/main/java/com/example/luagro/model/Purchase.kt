package com.example.luagro.model

import kotlinx.serialization.Serializable

@Serializable
data class Purchase(

    val id_carrito: String,
    val fecha_creacion: String? = null,
    val estado: String,
    val id_usuario: String

)