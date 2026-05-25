package com.example.luagro.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(

    val id_producto: String? = null,
    val nombre: String,
    val descripcion: String? = null,
    val precio: Double,
    val stock: Int? = null,
    val unidad_medida: String? = null,
    val origen: String? = null,
    val estado: Boolean = true,
    val id_categoria: String,
    val id_vendedor: String,
    val imagen_url: String? = null
)