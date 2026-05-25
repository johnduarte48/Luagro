package com.example.luagro.model

import kotlinx.serialization.Serializable

@Serializable
data class CartDetail(

    val id_detalle: String? = null,
    val cantidad: Int,
    val precio_unitario: Double,
    val subtotal: Double,
    val id_carrito: String,
    val id_producto: String
)