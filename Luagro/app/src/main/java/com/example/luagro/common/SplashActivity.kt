package com.example.luagro.common

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.luagro.R
import com.example.luagro.admin.AdminDashboardActivity
import com.example.luagro.auth.LoginActivity
import com.example.luagro.buyer.BuyerMainActivity
import com.example.luagro.seller.SellerMainActivity
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.luagro.model.Usuario

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            CoroutineScope(Dispatchers.IO).launch {

                val user =
                    client.auth.currentUserOrNull()

                if (user == null) {

                    withContext(Dispatchers.Main) {

                        startActivity(
                            Intent(
                                this@SplashActivity,
                                LoginActivity::class.java
                            )
                        )

                        finish()
                    }

                } else {

                    try {

                        val profile =
                            client
                                .from("profiles")
                                .select(
                                    Columns.list("id_rol")
                                ) {

                                    filter {
                                        eq("id", user.id)
                                    }
                                }
                                .decodeSingle<Usuario>()

                        withContext(Dispatchers.Main) {

                            when (profile.id_rol) {

                                "ada7775f-54fe-40bb-a011-11387be97489" -> {

                                    startActivity(
                                        Intent(
                                            this@SplashActivity,
                                            BuyerMainActivity::class.java
                                        )
                                    )
                                }

                                "d03eb7ad-1a40-4545-8e8d-bacba0c18ede" -> {

                                    startActivity(
                                        Intent(
                                            this@SplashActivity,
                                            SellerMainActivity::class.java
                                        )
                                    )
                                }

                                "f3558c6d-e3b3-45cc-b77d-2e8db308108b" -> {

                                    startActivity(
                                        Intent(
                                            this@SplashActivity,
                                            AdminDashboardActivity::class.java
                                        )
                                    )
                                }
                            }

                            finish()
                        }

                    } catch (e: Exception) {

                        withContext(Dispatchers.Main) {

                            startActivity(
                                Intent(
                                    this@SplashActivity,
                                    LoginActivity::class.java
                                )
                            )

                            finish()
                        }
                    }
                }
            }

        }, 2000)
    }
}