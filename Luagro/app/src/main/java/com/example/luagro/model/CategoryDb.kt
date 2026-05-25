package com.example.luagro.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDb(

    val id_categoria: String,
    val nombre: String,
    val descripcion: String? = null
)