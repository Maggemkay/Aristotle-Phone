package com.example.aristotle.MainActivity.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.aristotle.Models.User
import com.example.aristotle.R

class UsersRecyclerViewAdapter(private var usersData: MutableList<User>) : RecyclerView.Adapter<UsersRecyclerViewAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.UserItemName)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewitem_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val fullName = usersData[position].firstName + " " + usersData[position].lastName

        holder.name.text = fullName

    }

    override fun getItemCount() = usersData.size

    fun updateList(newUserList: MutableList<User>) {
        usersData = newUserList
        notifyDataSetChanged()
    }

    // Maybe not use
    fun removeAt(position: Int) : User{
        val removeUser = usersData[position]
        usersData.removeAt(position)
        notifyItemRemoved(position)
        return removeUser
    }

}