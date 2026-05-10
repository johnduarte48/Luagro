package com.example.luagro.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luagro.R
import com.example.luagro.auth.LoginActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnLogout =
            findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()

        }

    }

}