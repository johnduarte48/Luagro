package com.example.luagro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luagro.R
import com.example.luagro.model.Usuario
import android.content.Intent
import com.example.luagro.admin.UserCrudActivity
import android.widget.Toast
import com.example.luagro.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserAdapter(
    private val users: List<Usuario>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val txtUserName =
            view.findViewById<TextView>(R.id.txtUserName)

        val txtUserEmail =
            view.findViewById<TextView>(R.id.txtUserEmail)

        val btnEdit =
            view.findViewById<Button>(R.id.btnEdit)

        val btnDelete =
            view.findViewById<Button>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_user,
                    parent,
                    false
                )

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {

        val user = users[position]

        holder.txtUserName.text =
            "${user.nombre} ${user.apellido}"

        holder.txtUserEmail.text =
            user.correo

        holder.btnEdit.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                UserCrudActivity::class.java
            )

            intent.putExtra(
                "id_usuario",
                user.id
            )

            holder.itemView.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                try {

                    client
                        .from("profiles")
                        .update({

                            set(
                                "estado",
                                false
                            )

                        }) {

                            filter {

                                eq(
                                    "id",
                                    user.id
                                )
                            }
                        }

                    CoroutineScope(Dispatchers.Main).launch {

                        Toast.makeText(
                            holder.itemView.context,
                            "Usuario deshabilitado",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {

                    CoroutineScope(Dispatchers.Main).launch {

                        Toast.makeText(
                            holder.itemView.context,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun getItemCount() =
        users.size
}