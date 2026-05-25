package com.example.luagro.seller

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.luagro.R
import com.example.luagro.model.Product
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.net.Uri
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import io.github.jan.supabase.storage.storage
import java.util.UUID

class AddProductActivity : AppCompatActivity() {

    private var productId: String? = null
    private var selectedImageUri: Uri? = null
    private lateinit var imgProduct: ImageView

    private val imagePicker =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {

                selectedImageUri = uri
                imgProduct.setImageURI(uri)

            }
        }


    private suspend fun uploadImageToSupabase(): String? {

        if (selectedImageUri == null) {
            return null
        }

        val inputStream =
            contentResolver.openInputStream(
                selectedImageUri!!
            ) ?: return null

        val bytes =
            inputStream.readBytes()

        val fileName =
            "${UUID.randomUUID()}.jpg"

        client
            .storage
            .from("products")
            .upload(
                path = fileName,
                data = bytes,
                upsert = true
            )

        return client
            .storage
            .from("products")
            .publicUrl(fileName)
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_product)

        productId =
            intent.getStringExtra(
                "id_producto"
            )

        val etNombre =
            findViewById<EditText>(R.id.etNombre)

        val etPrecio =
            findViewById<EditText>(R.id.etPrecio)

        val etDescripcion =
            findViewById<EditText>(R.id.etDescripcion)

        val etOrigen =
            findViewById<EditText>(R.id.etOrigen)

        val etStock =
            findViewById<EditText>(R.id.etStock)

        val spinnerCategory =
            findViewById<Spinner>(R.id.spinnerCategory)

        val btnSave =
            findViewById<Button>(R.id.btnSaveProduct)

        val cardImage =
            findViewById<com.google.android.material.card.MaterialCardView>(
                R.id.cardImage
            )

        imgProduct = findViewById<ImageView>(R.id.imgProducto)


        val categories = listOf(
            "Frutas",
            "Verduras",
            "Organicos",
            "Granos",
            "Otros"
        )

        spinnerCategory.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
            )

        if (productId != null) {

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    val producto =
                        client
                            .from("productos")
                            .select {

                                filter {

                                    eq(
                                        "id_producto",
                                        productId!!
                                    )
                                }
                            }
                            .decodeSingle<Product>()

                    runOnUiThread {

                        etNombre.setText(
                            producto.nombre
                        )

                        etPrecio.setText(
                            producto.precio.toString()
                        )

                        etDescripcion.setText(
                            producto.descripcion ?: ""
                        )

                        etOrigen.setText(
                            producto.origen ?: ""
                        )

                        etStock.setText(
                            producto.stock?.toString() ?: ""
                        )
                    }

                } catch (e: Exception) {

                    runOnUiThread {

                        Toast.makeText(
                            this@AddProductActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        btnSave.setOnClickListener {

            val nombre =
                etNombre.text.toString().trim()

            val precio =
                etPrecio.text.toString().toDoubleOrNull()

            val descripcion =
                etDescripcion.text.toString().trim()

            val origen =
                etOrigen.text.toString().trim()

            val stock =
                etStock.text.toString().toIntOrNull()

            if (
                nombre.isEmpty() ||
                precio == null
            ) {

                Toast.makeText(
                    this,
                    "Complete nombre y precio",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    val categoriaId =
                        when (
                            spinnerCategory.selectedItem.toString()
                        ) {

                            "Frutas" ->
                                "6458c3bb-bdf7-4b22-8af8-6c5b885c8fff"

                            "Verduras" ->
                                "1851334f-029c-44e4-9667-1707c5d01cea"

                            "Organicos" ->
                                "bd8157d6-9ce0-46f7-91bc-b90b3efb21de"

                            "Granos" ->
                                "7a62392c-0665-4c66-bf58-be59ef6c9010"

                            else ->
                                "7b8b3433-7214-4457-b715-6b961cdc69bb"
                        }

                    val vendedorId =
                        client.auth.currentUserOrNull()?.id
                            ?: throw Exception("Usuario no autenticado")

                    val imageUrl =
                        uploadImageToSupabase()

                    val producto = Product(

                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio,
                        stock = stock,
                        origen = origen,
                        unidad_medida = "kg",
                        estado = true,
                        id_categoria = categoriaId,
                        id_vendedor = vendedorId,
                        imagen_url = imageUrl
                    )

//                    client
//                        .from("productos")
//                        .insert(producto)

                    if (productId == null) {

                        client
                            .from("productos")
                            .insert(producto)

                    } else {

                        client
                            .from("productos")
                            .update(producto) {

                                filter {

                                    eq(
                                        "id_producto",
                                        productId!!
                                    )
                                }
                            }
                    }

                    runOnUiThread {

                        Toast.makeText(
                            this@AddProductActivity,
                            "Producto guardado",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }

                } catch (e: Exception) {

                    runOnUiThread {

                        Toast.makeText(
                            this@AddProductActivity,
//                            e.message,
                            e.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    android.util.Log.e(
                        "UPLOAD_ERROR",
                        e.stackTraceToString()
                    )
                }
            }
        }

        cardImage.setOnClickListener {

            imagePicker.launch("image/*")

        }
    }
}