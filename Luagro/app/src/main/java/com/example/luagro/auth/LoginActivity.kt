package com.example.luagro.auth

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.luagro.R
import com.example.luagro.admin.AdminDashboardActivity
import com.example.luagro.buyer.BuyerMainActivity
import com.example.luagro.model.Usuario
import com.example.luagro.seller.SellerMainActivity
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {

    private fun showBiometricPrompt() {

        val biometricManager =
            BiometricManager.from(this)

        when (
            biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            )
        ) {

            BiometricManager.BIOMETRIC_SUCCESS -> {

                val executor =
                    ContextCompat.getMainExecutor(this)

                val biometricPrompt =
                    BiometricPrompt(
                        this,
                        executor,
                        object :
                            BiometricPrompt.AuthenticationCallback() {

                            override fun onAuthenticationSucceeded(
                                result: BiometricPrompt.AuthenticationResult
                            ) {

                                super.onAuthenticationSucceeded(result)

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Huella validada",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(
                                        this@LoginActivity,
                                        BuyerMainActivity::class.java
                                    )
                                )

                                finish()
                            }
                        }
                    )

                val promptInfo =
                    BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Acceso biométrico")
                        .setSubtitle("Ingrese con su huella")
                        .setNegativeButtonText("Cancelar")
                        .build()

                biometricPrompt.authenticate(
                    promptInfo
                )
            }

            else -> {

                android.widget.Toast.makeText(
                    this,
                    "El dispositivo no tiene biometría disponible",
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val txtForgot =
            findViewById<TextView>(R.id.txtForgot)

        val txtRegister =
            findViewById<TextView>(R.id.txtRegister)

        val btnFingerprint =
            findViewById<Button>(R.id.btnFingerprint)

        btnLogin.setOnClickListener {

            val correo =
                etEmail.text.toString().trim()

            val password =
                etPassword.text.toString().trim()

            if (
                correo.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    client.auth.signInWith(Email) {

                        this.email = correo
                        this.password = password
                    }

                    val userId =
                        client.auth.currentUserOrNull()?.id

                    if (userId != null) {

                        val usuario =
                            client
                                .from("profiles")
                                .select(
                                    Columns.list(
                                        "id",
                                        "nombre",
                                        "apellido",
                                        "correo",
                                        "telefono",
                                        "foto_perfil",
                                        "estado",
                                        "id_rol"
                                    )
                                ) {

                                    filter {
                                        eq("id", userId)
                                    }
                                }
                                .decodeSingle<Usuario>()

                        withContext(Dispatchers.Main) {

                            when (usuario.id_rol) {

                                "ada7775f-54fe-40bb-a011-11387be97489" -> {

                                    startActivity(
                                        Intent(
                                            this@LoginActivity,
                                            BuyerMainActivity::class.java
                                        )
                                    )
                                }

                                "d03eb7ad-1a40-4545-8e8d-bacba0c18ede" -> {

                                    startActivity(
                                        Intent(
                                            this@LoginActivity,
                                            SellerMainActivity::class.java
                                        )
                                    )
                                }

                                "f3558c6d-e3b3-45cc-b77d-2e8db308108b" -> {

                                    startActivity(
                                        Intent(
                                            this@LoginActivity,
                                            AdminDashboardActivity::class.java
                                        )
                                    )
                                }

                                else -> {

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Rol no reconocido",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            finish()
                        }
                    }

                } catch (e: Exception) {

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            this@LoginActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        txtForgot.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ForgotPasswordActivity::class.java
                )
            )
        }

        txtRegister.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }


        btnFingerprint.setOnClickListener {

            showBiometricPrompt()

        }
    }
}