package com.example.luagro.buyer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.luagro.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.adapter.CategoryAdapter
import com.example.luagro.adapter.ProductAdapter
import com.example.luagro.model.Category
import com.example.luagro.model.Product

class BuyerMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_main)

        val recyclerCategories =
            findViewById<RecyclerView>(R.id.recyclerCategories)

        val recyclerProducts =
            findViewById<RecyclerView>(R.id.recyclerProducts)

        recyclerCategories.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recyclerProducts.layoutManager =
            GridLayoutManager(this, 2)

        val categories = listOf(

            Category("Frutas", R.drawable.ic_category),
            Category("Verduras", R.drawable.ic_category),
            Category("Orgánicos", R.drawable.ic_category),
            Category("Granos", R.drawable.ic_category)

        )

        val products = listOf(

            Product(
                "Tomate orgánico",
                "$4.200/kg",
                R.drawable.fondo_login
            ),

            Product(
                "Hojas y tallos",
                "$2.300/kg",
                R.drawable.fondo_login
            ),

            Product(
                "Miel de abeja",
                "$18.500",
                R.drawable.fondo_login
            ),

            Product(
                "Zanahoria orgánica",
                "$3.100/kg",
                R.drawable.fondo_login
            )

        )

        recyclerCategories.adapter =
            CategoryAdapter(categories)

        recyclerProducts.adapter =
            ProductAdapter(products)

    }

}