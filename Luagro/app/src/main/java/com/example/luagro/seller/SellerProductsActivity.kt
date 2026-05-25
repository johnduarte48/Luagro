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
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast

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

        /*val products = listOf(

            Product(
                nombre = "Tomate orgánico",
                descripcion = "Producto fresco",
                precio = 12000.0,
                stock = 20,
                unidad_medida = "kg",
                origen = "Boyacá",
                id_categoria = "6458c3bb-bdf7-4b22-8af8-6c5b885c8fff",
                id_vendedor = "demo"
            )

        )

        recyclerProducts.adapter =
            ProductAdapter(products)*/

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val vendedorId =
                    client.auth.currentUserOrNull()?.id

                if (vendedorId != null) {

                    val productos =
                        client
                            .from("productos")
                            .select {

                                filter {

                                    eq(
                                        "id_vendedor",
                                        vendedorId
                                    )
                                }
                            }
                            .decodeList<Product>()

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            this@SellerProductsActivity,
                            "Productos encontrados: ${productos.size}",
                            Toast.LENGTH_LONG
                        ).show()

                        recyclerProducts.adapter =
                            ProductAdapter(
                                productList = productos,
                                showDeleteButton = true,

                                onDeleteClick = { producto ->

                                    CoroutineScope(Dispatchers.IO).launch {

                                        try {

                                            client
                                                .from("productos")
                                                .delete {

                                                    filter {

                                                        eq(
                                                            "id_producto",
                                                            producto.id_producto!!
                                                        )
                                                    }
                                                }

                                            withContext(Dispatchers.Main) {

                                                Toast.makeText(
                                                    this@SellerProductsActivity,
                                                    "Producto eliminado",
                                                    Toast.LENGTH_LONG
                                                ).show()

                                                recreate()
                                            }

                                        } catch (e: Exception) {

                                            withContext(Dispatchers.Main) {

                                                Toast.makeText(
                                                    this@SellerProductsActivity,
                                                    e.message,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    }
                                },

                                onEditClick = { producto ->

                                    val intent =
                                        Intent(
                                            this@SellerProductsActivity,
                                            AddProductActivity::class.java
                                        )

                                    intent.putExtra(
                                        "id_producto",
                                        producto.id_producto
                                    )

                                    startActivity(intent)
                                }
                            )
                    }



                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    android.widget.Toast.makeText(
                        this@SellerProductsActivity,
                        //e.message,
                        e.toString(),
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


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