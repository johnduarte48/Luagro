package com.example.luagro.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luagro.R
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val etRecoveryEmail =
            findViewById<EditText>(R.id.etRecoveryEmail)

        val btnRecovery =
            findViewById<Button>(R.id.btnRecovery)

        btnRecovery.setOnClickListener {

            val email =
                etRecoveryEmail.text.toString().trim()

            if (email.isEmpty()) {

                Toast.makeText(
                    this,
                    "Ingresa tu correo",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    client.auth.resetPasswordForEmail(email)

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Correo de recuperación enviado",
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(
                            Intent(
                                this@ForgotPasswordActivity,
                                LoginActivity::class.java
                            )
                        )

                        finish()
                    }

                } catch (e: Exception) {

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}