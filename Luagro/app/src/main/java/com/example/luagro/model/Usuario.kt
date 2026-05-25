package com.example.luagro.model

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(

    val id: String,
    val nombre: String? = null,
    val apellido: String? = null,
    val correo: String? = null,
    val telefono: String? = null,
    val foto_perfil: String? = null,
    val estado: Boolean = true,
    val id_rol: String? = null
)