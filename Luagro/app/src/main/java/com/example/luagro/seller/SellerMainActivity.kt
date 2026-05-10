package com.example.luagro.seller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.adapter.ProductAdapter
import com.example.luagro.buyer.ProfileActivity
import com.example.luagro.model.Product

class SellerMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_main)

        val btnProducts =
            findViewById<Button>(R.id.btnProducts)

        val btnOrders =
            findViewById<Button>(R.id.btnOrders)

        val btnAddProduct =
            findViewById<Button>(R.id.btnAddProduct)

        val btnProfile =
            findViewById<Button>(R.id.btnProfile)

        btnProducts.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SellerProductsActivity::class.java
                )
            )

        }

        btnOrders.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    OrderDetailActivity::class.java
                )
            )

        }

        btnAddProduct.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddProductActivity::class.java
                )
            )

        }

        btnProfile.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )

        }

    }

}