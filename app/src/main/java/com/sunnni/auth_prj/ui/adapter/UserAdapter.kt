package com.sunnni.auth_prj.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunnni.auth_prj.R
import com.sunnni.auth_prj.data.dto.User

class UserAdapter(val ctx: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val admin = 2

        val tvId = v.findViewById<TextView>(R.id.tv_id)
        val tvNickname = v.findViewById<TextView>(R.id.tv_nickname)
        val swcAdmin = v.findViewById<Switch>(R.id.swc_user_type)

        fun bind(data: User) {
            tvId.text = data.id
            tvNickname.text = data.nickname

            if (data.type == admin.toByte()) {
                swcAdmin.isChecked = true
            }
        }
    }
}