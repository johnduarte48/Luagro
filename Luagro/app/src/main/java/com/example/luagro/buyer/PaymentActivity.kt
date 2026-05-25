package com.example.luagro.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luagro.R
import com.example.luagro.model.Cart
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val btnPay = findViewById<Button>(R.id.btnPay)

        btnPay.setOnClickListener {

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

                                    eq("id_usuario", userId)
                                    eq("estado", "ACTIVO")
                                }
                            }
                            .decodeSingle<Cart>()

                    client
                        .from("carritos")
                        .update(
                            {
                                set("estado", "PAGADO")
                            }
                        ) {

                            filter {

                                eq(
                                    "id_carrito",
                                    carrito.id_carrito!!
                                )
                            }
                        }

                    val nuevoCarrito =
                        Cart(
                            id_carrito = UUID.randomUUID().toString(),
                            estado = "ACTIVO",
                            id_usuario = userId
                        )

                    client
                        .from("carritos")
                        .insert(nuevoCarrito)

                    runOnUiThread {

                        startActivity(
                            Intent(
                                this@PaymentActivity,
                                ConfirmationActivity::class.java
                            )
                        )

                        finish()
                    }

                } catch (e: Exception) {

                    runOnUiThread {

                        android.widget.Toast.makeText(
                            this@PaymentActivity,
                            e.message,
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }
}