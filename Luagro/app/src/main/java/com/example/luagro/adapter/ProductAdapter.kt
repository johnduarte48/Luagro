package com.example.luagro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.buyer.ProductDetailActivity
import com.example.luagro.model.Product
import coil.load

class ProductAdapter(
    private val productList: List<Product>,
    private val showDeleteButton: Boolean = false,
    private val onDeleteClick: ((Product) -> Unit)? = null,
    private val onEditClick: ((Product) -> Unit)? = null
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
        val txtProductName: TextView = view.findViewById(R.id.txtProductName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val btnView: Button = view.findViewById(R.id.btnView)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)

        val btnEdit: Button = view.findViewById(R.id.btnEdit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {

        val product = productList[position]

        holder.txtProductName.text =
            product.nombre

        holder.txtPrice.text =
            "$ ${product.precio}"


        if (!product.imagen_url.isNullOrEmpty()) {

            holder.imgProduct.load(
                product.imagen_url
            ) {

                crossfade(true)

                placeholder(
                    R.drawable.tomate
                )

                error(
                    R.drawable.tomate
                )
            }

        } else {

            holder.imgProduct.setImageResource(
                R.drawable.tomate
            )
        }

        holder.btnDelete.visibility =
            if (showDeleteButton)
                View.VISIBLE
            else
                View.GONE

        holder.btnEdit.visibility =
            if (showDeleteButton)
                View.VISIBLE
            else
                View.GONE

        holder.btnView.setOnClickListener {

            val context =
                holder.itemView.context

//            context.startActivity(
//                Intent(
//                    context,
//                    ProductDetailActivity::class.java
//                )
//            )
            val intent =
                Intent(
                    context,
                    ProductDetailActivity::class.java
                )

            intent.putExtra(
                "id_producto",
                product.id_producto
            )

            context.startActivity(intent)
        }

        holder.btnDelete.visibility =
            if (showDeleteButton)
                View.VISIBLE
            else
                View.GONE

        holder.btnDelete.setOnClickListener {

            onDeleteClick?.invoke(product)

        }

        holder.btnEdit.setOnClickListener {

            onEditClick?.invoke(product)

        }
    }

    override fun getItemCount(): Int {

        return productList.size

    }
}