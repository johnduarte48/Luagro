package com.example.luagro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.model.Category
import com.example.luagro.model.CategoryDb

class CategoryAdapter(
    private val categories: List<CategoryDb>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val txtCategory =
            view.findViewById<TextView>(R.id.txtCategory)

        val imgCategory =
            view.findViewById<ImageView>(R.id.imgCategory)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_category,
                    parent,
                    false
                )

        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {

        val category =
            categories[position]

        holder.txtCategory.text =
            category.nombre

        holder.imgCategory.setImageResource(
            R.drawable.ic_category
        )
    }

    override fun getItemCount(): Int =
        categories.size
}