package com.example.luagro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.model.Cart

class OrderAdapter(
    private val orders: List<Cart>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val txtOrderId =
            view.findViewById<TextView>(R.id.txtOrderId)

        val txtOrderStatus =
            view.findViewById<TextView>(R.id.txtOrderStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_order,
                    parent,
                    false
                )

        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int
    ) {

        val order =
            orders[position]

        holder.txtOrderId.text =
            order.id_carrito

        holder.txtOrderStatus.text =
            order.estado
    }

    override fun getItemCount() =
        orders.size
}