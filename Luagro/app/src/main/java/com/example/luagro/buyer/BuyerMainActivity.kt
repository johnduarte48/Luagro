package com.example.luagro.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
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
import com.example.luagro.auth.LoginActivity
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast
import android.util.Log
import com.example.luagro.model.CategoryDb
import com.example.luagro.seller.OrderDetailActivity

class BuyerMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_main)

        val recyclerCategories =
            findViewById<RecyclerView>(R.id.recyclerCategories)

        val recyclerProducts =
            findViewById<RecyclerView>(R.id.recyclerProducts)

        val btnLogout =
            findViewById<ImageButton>(R.id.btnLogout)

        val btnCart =
            findViewById<Button>(R.id.btnCart)

        val btnMyOrders =
            findViewById<Button>(R.id.btnMyOrders)



        recyclerCategories.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        recyclerProducts.layoutManager =
            GridLayoutManager(this, 2)

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val productos =
                    client
                        .from("productos")
                        .select()
                        .decodeList<Product>()

                val categorias =
                    client
                        .from("categorias")
                        .select()
                        .decodeList<CategoryDb>()



                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@BuyerMainActivity,
                        "Productos: ${productos.size}",
                        Toast.LENGTH_LONG
                    ).show()

                    Log.d(
                        "BUYER",
                        "Productos encontrados: ${productos.size}"
                    )

                    Log.d(
                        "BUYER",
                        "Productos recibidos: ${productos.size}"
                    )

                    recyclerCategories.adapter =
                        CategoryAdapter(categorias)

                    recyclerProducts.adapter =
                        ProductAdapter(productos)

                    val adapter = ProductAdapter(productos)

                    recyclerProducts.adapter = adapter

                    android.util.Log.d(
                        "BUYER",
                        "Adapter count: ${adapter.itemCount}"
                    )



                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    android.widget.Toast.makeText(
                        this@BuyerMainActivity,
                        e.message,
                        android.widget.Toast.LENGTH_LONG
                    ).show()

                }
            }
        }

//        val categories = listOf(
//
//            Category("Frutas", R.drawable.ic_category),
//            Category("Verduras", R.drawable.ic_category),
//            Category("Orgánicos", R.drawable.ic_category),
//            Category("Granos", R.drawable.ic_category)
//
//        )

//        val products = listOf(
//
//            Product(
//                nombre = "Tomate orgánico",
//                descripcion = "Producto fresco",
//                precio = 12000.0,
//                stock = 20,
//                unidad_medida = "kg",
//                origen = "Boyacá",
//                id_categoria = "6458c3bb-bdf7-4b22-8af8-6c5b885c8fff",
//                id_vendedor = "demo"
//            )
//
//        )

//        recyclerCategories.adapter =
//            CategoryAdapter(categories)

//        recyclerProducts.adapter =
//            ProductAdapter(products)

        btnLogout.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                client.auth.signOut()

                runOnUiThread {

                    startActivity(
                        Intent(
                            this@BuyerMainActivity,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }
            }
        }

        btnCart.setOnClickListener {

            startActivity(

                Intent(
                    this,
                    CartActivity::class.java
                )
            )
        }

        btnMyOrders.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    OrderDetailActivity::class.java
                )
            )
        }

    }

}