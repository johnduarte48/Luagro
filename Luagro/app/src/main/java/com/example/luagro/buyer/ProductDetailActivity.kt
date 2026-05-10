package com.example.luagro.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luagro.R

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val btnAddCart = findViewById<Button>(R.id.btnAddCart)

        btnAddCart.setOnClickListener {

            startActivity(
                Intent(this, PaymentActivity::class.java)
            )

        }

    }
}