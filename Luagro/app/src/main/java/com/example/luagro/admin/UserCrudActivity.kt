package com.example.luagro.admin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luagro.R
import com.example.luagro.model.Usuario
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserCrudActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_crud)

        val edtNombre =
            findViewById<EditText>(R.id.edtNombre)

        val edtApellido =
            findViewById<EditText>(R.id.edtApellido)

        val edtTelefono =
            findViewById<EditText>(R.id.edtTelefono)

        val btnGuardar =
            findViewById<Button>(R.id.btnGuardar)

        val btnEliminar =
            findViewById<Button>(R.id.btnEliminar)

        val idUsuario =
            intent.getStringExtra("id_usuario")
                ?: return

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val usuario =
                    client
                        .from("profiles")
                        .select {

                            filter {
                                eq("id", idUsuario)
                            }
                        }
                        .decodeSingle<Usuario>()

                withContext(Dispatchers.Main) {

                    edtNombre.setText(usuario.nombre)
                    edtApellido.setText(usuario.apellido)
                    edtTelefono.setText(usuario.telefono ?: "")
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@UserCrudActivity,
                        e.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        btnGuardar.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    client
                        .from("profiles")
                        .update({

                            set(
                                "nombre",
                                edtNombre.text.toString()
                            )

                            set(
                                "apellido",
                                edtApellido.text.toString()
                            )

                            set(
                                "telefono",
                                edtTelefono.text.toString()
                            )

                        }) {

                            filter {
                                eq("id", idUsuario)
                            }
                        }

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            this@UserCrudActivity,
                            "Usuario actualizado",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }

                } catch (e: Exception) {

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            this@UserCrudActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        btnEliminar.setOnClickListener {

            Toast.makeText(
                this@UserCrudActivity,
                "Botón eliminar pulsado",
                Toast.LENGTH_LONG
            ).show()

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    client
                        .from("profiles")
                        .update({

                            set(
                                "estado",
                                false
                            )

                        }) {

                            filter {
                                eq("id", idUsuario)
                            }
                        }

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            this@UserCrudActivity,
                            "Usuario deshabilitado",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }

                } catch (e: Exception) {

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            this@UserCrudActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}