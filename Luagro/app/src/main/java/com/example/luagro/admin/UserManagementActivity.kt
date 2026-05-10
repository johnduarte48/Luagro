package com.example.luagro.admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.adapter.UserAdapter

class UserManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        val recyclerUsers =
            findViewById<RecyclerView>(R.id.recyclerUsers)

        recyclerUsers.layoutManager =
            LinearLayoutManager(this)

        recyclerUsers.adapter =
            UserAdapter()

    }

}