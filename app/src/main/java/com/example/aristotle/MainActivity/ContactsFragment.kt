package com.example.aristotle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aristotle.MainActivity.Adapters.UsersRecyclerViewAdapter
import com.example.aristotle.Models.User


class ContactsFragment : Fragment() {

    private lateinit var usersRecyclerView: androidx.recyclerview.widget.RecyclerView

    lateinit var userList: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the local users
        userList = loadUsers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_viewcontact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersRecyclerView = view.findViewById(R.id.UsersRecyclerView)
        usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
        usersRecyclerView.adapter = UsersRecyclerViewAdapter(userList)
        usersRecyclerView.hasFixedSize()
    }


    private fun loadUsers() : List<User> {
        // Load users from local storage

        val madeUpUsers = listOf<User>(
            User("1", "Bobus", "abc", "cool@email", "Bob", "Foo"),
            User("3", "Kobus", "abc", "cool@email", "Bart", "Baz"),
            User("2", "Fobus", "abc", "cool@email", "Lisa", "Bar")
        )

        return madeUpUsers
    }

}
