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
import com.example.luagro.model.Usuario
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        val recyclerUsers =
            findViewById<RecyclerView>(R.id.recyclerUsers)

        recyclerUsers.layoutManager =
            LinearLayoutManager(this)

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val usuarios =
                    client
                        .from("profiles")
                        .select {

                            filter {
                                eq("estado", true)
                            }
                        }
                        .decodeList<Usuario>()

                withContext(Dispatchers.Main) {

                    recyclerUsers.adapter =
                        UserAdapter(usuarios)
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    android.widget.Toast.makeText(
                        this@UserManagementActivity,
                        e.message,
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

}