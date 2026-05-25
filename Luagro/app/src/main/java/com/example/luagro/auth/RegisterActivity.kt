package com.example.luagro.auth

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.luagro.R
import com.example.luagro.model.Usuario
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword =
            findViewById<EditText>(R.id.etConfirmPassword)

        val etPhone =
            findViewById<EditText>(R.id.etPhone)

        val spinnerRole =
            findViewById<Spinner>(R.id.spinnerRole)

        val btnRegister =
            findViewById<Button>(R.id.btnRegister)

        val roles = listOf(
            "comprador",
            "vendedor"
        )

        spinnerRole.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            roles
        )

        btnRegister.setOnClickListener {

            val nombre = etName.text.toString().trim()

            val correo = etEmail.text.toString().trim()

            val passwordText =
                etPassword.text.toString().trim()

            val confirmar =
                etConfirmPassword.text.toString().trim()

            val telefono =
                etPhone.text.toString().trim()

            val rol =
                spinnerRole.selectedItem.toString()

            if (
                nombre.isEmpty() ||
                correo.isEmpty() ||
                passwordText.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (passwordText != confirmar) {

                Toast.makeText(
                    this,
                    "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {

                try {

//                    val result =
//                        client.auth.signUpWith(Email) {
//
//                            email = correo
//                            password = passwordText
//                        }
//
//                    val userId =
//                        result?.id
                    client.auth.signUpWith(Email) {

                        email = correo
                        password = passwordText
                    }

                    val userId =
                        client.auth.currentUserOrNull()?.id

                    if (userId != null) {

                        val roleId = when (rol) {

                            "comprador" ->
                                "ada7775f-54fe-40bb-a011-11387be97489"

                            "vendedor" ->
                                "d03eb7ad-1a40-4545-8e8d-bacba0c18ede"

                            "administrador" ->
                                "f3558c6d-e3b3-45cc-b77d-2e8db308108b"

                            else -> ""
                        }

                        val usuario = Usuario(

                            id = userId,

                            nombre = nombre,

                            apellido = "",

                            correo = correo,

                            telefono = telefono,

                            foto_perfil = null,

                            estado = true,

                            id_rol = roleId
                        )

                        client
                            .from("profiles")
                            .insert(usuario)

                        runOnUiThread {

                            Toast.makeText(
                                this@RegisterActivity,
                                "Registro exitoso",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(
                                    this@RegisterActivity,
                                    LoginActivity::class.java
                                )
                            )

                            finish()
                        }
                    }

                } catch (e: Exception) {

                    runOnUiThread {

                        Toast.makeText(
                            this@RegisterActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}