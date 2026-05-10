package com.example.luagro.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luagro.R

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnRecovery =
            findViewById<Button>(R.id.btnRecovery)

        btnRecovery.setOnClickListener {

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()

        }

    }
}