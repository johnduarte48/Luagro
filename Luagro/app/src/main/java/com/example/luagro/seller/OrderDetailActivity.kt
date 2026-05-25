package com.example.luagro.seller

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.adapter.OrderAdapter
import com.example.luagro.model.Cart
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_order_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerOrders =
            findViewById<RecyclerView>(R.id.recyclerOrders)

        recyclerOrders.layoutManager =
            LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val userId =
                    client.auth.currentUserOrNull()?.id
                        ?: throw Exception("Usuario no autenticado")

                val pedidos =
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
                                    "PAGADO"
                                )
                            }
                        }
                        .decodeList<Cart>()

                withContext(Dispatchers.Main) {

                    recyclerOrders.adapter =
                        OrderAdapter(pedidos)

                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@OrderDetailActivity,
                        e.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {

        finish()

        return true
    }
}