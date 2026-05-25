package com.example.luagro.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    //private const val SUPABASE_URL = "https://bsqpjuyeymnikgkfujdz.supabase.co/rest/v1/"
    private const val SUPABASE_URL = "https://bsqpjuyeymnikgkfujdz.supabase.co/"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJzcXBqdXlleW1uaWtna2Z1amR6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg5NTc4OTgsImV4cCI6MjA5NDUzMzg5OH0.8Z8btYGi4jYDj9pQSPtusRchRvmlJX1LDmTETy0zYR8"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {

        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}