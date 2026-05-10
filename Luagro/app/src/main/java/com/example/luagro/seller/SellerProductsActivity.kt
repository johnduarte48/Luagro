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
import com.example.luagro.model.Product

class SellerProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_products)

        val recyclerProducts =
            findViewById<RecyclerView>(R.id.recyclerSellerProducts)

        val btnAddProduct =
            findViewById<Button>(R.id.btnAddProduct)

        val btnDelProduct =
            findViewById<Button>(R.id.btnDelProduct)

        recyclerProducts.layoutManager =
            LinearLayoutManager(this)

        val products = listOf(

            Product(
                "Tomate orgánico",
                "$12.000",
                R.drawable.fondo_login
            ),

            Product(
                "Lechuga hidropónica",
                "$8.000",
                R.drawable.fondo_login
            ),

            Product(
                "Papa criolla",
                "$6.500",
                R.drawable.fondo_login
            )

        )

        recyclerProducts.adapter =
            ProductAdapter(products)

        btnAddProduct.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddProductActivity::class.java
                )
            )

        }

        btnDelProduct.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddProductActivity::class.java
                )
            )

        }

    }

}