package com.example.luagro.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luagro.R
import com.example.luagro.seller.AddProductActivity
import com.example.luagro.auth.LoginActivity
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val btnUsers =
            findViewById<Button>(R.id.btnUsers)

        val btnReports =
            findViewById<Button>(R.id.btnReports)

        val btnProducts =
            findViewById<Button>(R.id.btnProducts)

        val btnLogout =
            findViewById<ImageButton>(R.id.btnLogout)



        btnUsers.setOnClickListener {

            startActivity(
                Intent(this, UserManagementActivity::class.java)
            )

        }

        btnReports.setOnClickListener {

            startActivity(
                Intent(this, ReportsActivity::class.java)
            )

        }

        btnProducts.setOnClickListener {

            startActivity(
                Intent(this, AddProductActivity::class.java)
            )
        }

        btnLogout.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                client.auth.signOut()

                runOnUiThread {

                    startActivity(
                        Intent(
                            this@AdminDashboardActivity,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }
            }
        }

    }

}