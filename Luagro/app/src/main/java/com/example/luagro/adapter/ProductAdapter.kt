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

class ProductAdapter(
    private val productList: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
        val txtProductName: TextView = view.findViewById(R.id.txtProductName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val btnView: Button = view.findViewById(R.id.btnView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductViewHolder(view)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val product = productList[position]

        holder.txtProductName.text = product.name
        holder.txtPrice.text = product.price
        holder.imgProduct.setImageResource(product.image)

        holder.btnView.setOnClickListener {

            val context = holder.itemView.context

            context.startActivity(
                Intent(context, ProductDetailActivity::class.java)
            )

        }

    }

    override fun getItemCount(): Int {

        return productList.size

    }
}