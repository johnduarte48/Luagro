package com.example.luagro.buyer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.luagro.R
import com.example.luagro.model.Product
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.luagro.model.CategoryDb
import com.example.luagro.model.Usuario
import io.github.jan.supabase.gotrue.auth
import com.example.luagro.model.Cart
import com.example.luagro.model.CartDetail

class ProductDetailActivity : AppCompatActivity() {

    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_product_detail
        )

        val imgProduct =
            findViewById<ImageView>(R.id.imgProduct)

        val txtName =
            findViewById<TextView>(R.id.txtProductName)

        val txtPrice =
            findViewById<TextView>(R.id.txtPrice)

        val txtDescription =
            findViewById<TextView>(R.id.txtDescription)

        val txtQuantity =
            findViewById<TextView>(R.id.txtQuantity)

        val btnIncrease =
            findViewById<Button>(R.id.btnIncrease)

        val btnDecrease =
            findViewById<Button>(R.id.btnDecrease)

        val btnAddCart =
            findViewById<Button>(R.id.btnAddCart)

        val txtOrigen =
            findViewById<TextView>(R.id.txtOrigen)

        val txtStock =
            findViewById<TextView>(R.id.txtStock)

        val txtCategoria =
            findViewById<TextView>(R.id.txtCategoria)

        val txtVendedor =
            findViewById<TextView>(R.id.txtVendedor)

        val userId =
            client.auth.currentUserOrNull()?.id

        val idProducto =
            intent.getStringExtra(
                "id_producto"
            )



        btnIncrease.setOnClickListener {

            quantity++

            txtQuantity.text =
                quantity.toString()
        }

        btnDecrease.setOnClickListener {

            if (quantity > 1) {

                quantity--

                txtQuantity.text =
                    quantity.toString()
            }
        }


        CoroutineScope(Dispatchers.IO).launch {

            try {

                val producto =
                    client
                        .from("productos")
                        .select {

                            filter {

                                eq(
                                    "id_producto",
                                    idProducto!!
                                )
                            }
                        }
                        .decodeSingle<Product>()

                val categoria =
                    client
                        .from("categorias")
                        .select {

                            filter {

                                eq(
                                    "id_categoria",
                                    producto.id_categoria
                                )
                            }
                        }
                        .decodeSingle<CategoryDb>()

                val vendedor =
                    client
                        .from("profiles")
                        .select {

                            filter {

                                eq(
                                    "id",
                                    producto.id_vendedor
                                )
                            }
                        }
                        .decodeSingle<Usuario>()

                withContext(Dispatchers.Main) {

                    txtName.text =
                        producto.nombre

                    txtPrice.text =
                        "$ ${producto.precio}"

                    txtDescription.text =
                        producto.descripcion ?: ""

                    txtOrigen.text =
                        producto.origen ?: "No especificado"

                    txtStock.text =
                        producto.stock?.toString() ?: "0"

                    txtCategoria.text =
                        categoria.nombre

                    txtVendedor.text =
                        buildString {

                            append(vendedor.nombre ?: "")

                            if (!vendedor.apellido.isNullOrBlank()) {

                                append(" ")
                                append(vendedor.apellido)
                            }
                        }

                    if (
                        !producto.imagen_url.isNullOrEmpty()
                    ) {

                        imgProduct.load(
                            producto.imagen_url
                        )
                    }
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    android.widget.Toast.makeText(
                        this@ProductDetailActivity,
                        e.message,
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        btnAddCart.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    val userId =
                        client.auth.currentUserOrNull()?.id
                            ?: throw Exception("Usuario no autenticado")

                    val producto =
                        client
                            .from("productos")
                            .select {

                                filter {

                                    eq(
                                        "id_producto",
                                        idProducto!!
                                    )
                                }
                            }
                            .decodeSingle<Product>()

                    var carritoId: String

                    try {

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

                        carritoId =
                            carrito.id_carrito!!

                    } catch (e: Exception) {

                        val nuevoCarrito =
                            Cart(
                                estado = "ACTIVO",
                                id_usuario = userId
                            )

                        client
                            .from("carritos")
                            .insert(nuevoCarrito)

                        val carritoCreado =
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

                        carritoId =
                            carritoCreado.id_carrito!!
                    }

                    val detalle =
                        CartDetail(

                            cantidad = quantity,

                            precio_unitario =
                                producto.precio,

                            subtotal =
                                producto.precio * quantity,

                            id_carrito =
                                carritoId,

                            id_producto =
                                producto.id_producto!!
                        )

                    client
                        .from("carrito_detalle")
                        .insert(detalle)

                    withContext(Dispatchers.Main) {

                        android.widget.Toast.makeText(
                            this@ProductDetailActivity,
                            "Producto agregado al carrito",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {

                    withContext(Dispatchers.Main) {

                        android.widget.Toast.makeText(
                            this@ProductDetailActivity,
                            e.message,
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}