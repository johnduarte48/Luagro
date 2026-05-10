package com.example.luagro.auth

import android.os.Bundle
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luagro.R
import com.example.luagro.admin.AdminDashboardActivity
import com.example.luagro.buyer.BuyerMainActivity
import com.example.luagro.seller.SellerMainActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtForgot = findViewById<TextView>(R.id.txtForgot)
        val txtRegister = findViewById<TextView>(R.id.txtRegister)
        val spinnerRole =
            findViewById<Spinner>(R.id.spinnerRole)

        val roles = listOf(
            "Comprador",
            "Vendedor",
            "Administrador"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            roles
        )

        spinnerRole.adapter = adapter


        // Login - Enlace a perfiles de comprador - vendedor - administrador

        btnLogin.setOnClickListener {

            when (spinnerRole.selectedItem.toString()) {

                "Comprador" -> {

                    startActivity(
                        Intent(
                            this,
                            BuyerMainActivity::class.java
                        )
                    )

                }

                "Vendedor" -> {

                    startActivity(
                        Intent(
                            this,
                            SellerMainActivity::class.java
                        )
                    )

                }

                "Administrador" -> {

                    startActivity(
                        Intent(
                            this,
                            AdminDashboardActivity::class.java
                        )
                    )

                }

            }

        }

        // Recuperar contraseña - Enlace a ForgotPasswordActivity

        txtForgot.setOnClickListener {

            startActivity(
                Intent(this, ForgotPasswordActivity::class.java)
            )

        }

        // Registro - Enlace a RegisterActivity

        txtRegister.setOnClickListener {

            startActivity(
                Intent(this, RegisterActivity::class.java)
            )

        }

    }
}