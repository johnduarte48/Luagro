package com.example.luagro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.model.CartItem
import com.example.luagro.model.Product

class CartAdapter(
    private val items: List<Pair<CartItem, Product>>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val txtName =
            view.findViewById<TextView>(R.id.txtCartName)

        val txtQuantity =
            view.findViewById<TextView>(R.id.txtCartQuantity)

        val txtSubtotal =
            view.findViewById<TextView>(R.id.txtCartSubtotal)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_cart,
                    parent,
                    false
                )

        return CartViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {

        val item =
            items[position]

        holder.txtName.text =
            item.second.nombre

        holder.txtQuantity.text =
            "Cantidad: ${item.first.cantidad}"

        holder.txtSubtotal.text =
            "$ ${item.first.subtotal}"
    }

    override fun getItemCount() =
        items.size
}