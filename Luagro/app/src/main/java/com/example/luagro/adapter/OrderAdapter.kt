package com.example.luagro.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.seller.OrderInfoActivity


class OrderAdapter :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) :
        RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)

        return OrderViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int
    ) {
        holder.itemView.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                OrderInfoActivity::class.java
            )

            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {

        return 10

    }

}