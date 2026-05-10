package com.example.luagro.seller

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.adapter.OrderAdapter

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerOrders =
            findViewById<RecyclerView>(R.id.recyclerOrders)

        recyclerOrders.layoutManager =
            LinearLayoutManager(this)

        recyclerOrders.adapter =
            OrderAdapter()

    }

    override fun onSupportNavigateUp(): Boolean {

        finish()

        return true

    }

}