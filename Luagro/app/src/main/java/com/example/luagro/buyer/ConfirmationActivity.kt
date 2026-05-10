package com.example.luagro.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luagro.R

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val btnBackHome =
            findViewById<Button>(R.id.btnBackHome)

        btnBackHome.setOnClickListener {

            startActivity(
                Intent(this, BuyerMainActivity::class.java)
            )

            finish()

        }

    }
}