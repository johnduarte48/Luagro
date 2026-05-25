package com.example.luagro.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.adapter.CartAdapter
import com.example.luagro.model.Cart
import com.example.luagro.model.CartItem
import com.example.luagro.model.Product
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        Toast.makeText(
            this,
            "Paso 1",
            Toast.LENGTH_SHORT
        ).show()

        setContentView(R.layout.activity_cart)

        Toast.makeText(
            this,
            "Paso 2",
            Toast.LENGTH_SHORT
        ).show()

        val recyclerCart =
            findViewById<RecyclerView>(R.id.recyclerCart)

        val txtTotal =
            findViewById<TextView>(R.id.txtTotal)

        val btnCheckout =
            findViewById<Button>(R.id.btnCheckout)

        Toast.makeText(
            this,
            "Paso 3",
            Toast.LENGTH_SHORT
        ).show()

        recyclerCart.layoutManager =
            LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val userId =
                    client.auth.currentUserOrNull()?.id
                        ?: throw Exception("Usuario no autenticado")

                val carrito =
                    client
                        .from("carritos")
                        .select {

                            filter {

                                eq(
                                    "id_usuario",
                                    userId
                                )

                                eq(
                                    "estado",
                                    "ACTIVO"
                                )
                            }
                        }
                        .decodeSingle<Cart>()

                val detalles =
                    client
                        .from("carrito_detalle")
                        .select {

                            filter {

                                eq(
                                    "id_carrito",
                                    carrito.id_carrito!!
                                )
                            }
                        }
                        .decodeList<CartItem>()

                val listaCompleta =
                    mutableListOf<Pair<CartItem, Product>>()

                var total = 0.0

                detalles.forEach { detalle ->

                    val producto =
                        client
                            .from("productos")
                            .select {

                                filter {

                                    eq(
                                        "id_producto",
                                        detalle.id_producto
                                    )
                                }
                            }
                            .decodeSingle<Product>()

                    listaCompleta.add(
                        Pair(
                            detalle,
                            producto
                        )
                    )

                    total += detalle.subtotal
                }

                withContext(Dispatchers.Main) {

                    recyclerCart.adapter =
                        CartAdapter(listaCompleta)

                    txtTotal.text =
                        "Total: $ $total"
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@CartActivity,
                        e.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        btnCheckout.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PaymentActivity::class.java
                )
            )

        }
    }
}