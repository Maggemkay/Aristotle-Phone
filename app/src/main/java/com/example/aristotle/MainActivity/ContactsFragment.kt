package com.example.aristotle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aristotle.MainActivity.Adapters.UsersRecyclerViewAdapter
import com.example.aristotle.Models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_viewcontact.*
import java.io.FileReader
import java.io.FileWriter
import java.io.Reader
import java.io.Writer
import java.lang.reflect.Type
import java.io.*


class ContactsFragment : Fragment() {

    private lateinit var usersRecyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var userRecyclerViewAdapter: UsersRecyclerViewAdapter

    private var userList: MutableList<User> = mutableListOf<User>()

    private lateinit var pathToUserFile : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pathToUserFile = this.context?.filesDir?.path + "/Users.json"

        // Get the local users
        userList = loadUsers()

        userRecyclerViewAdapter = UsersRecyclerViewAdapter(userList)
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
        usersRecyclerView.adapter = userRecyclerViewAdapter
        usersRecyclerView.hasFixedSize()

        addContactButton.setOnClickListener {
            // Add new user

            saveUsers(User("", "New user", "", "cool@email1", "New", "a")) // Remove later
            userRecyclerViewAdapter.updateList(userList)
        }
    }


    private fun saveUsers(newUser: User){
        userList = loadUsers()

        if (userList.contains(newUser)) {
            //TODO: User already exists message
        }
        else
        {
            userList.add(newUser)
            val writer: Writer = FileWriter(pathToUserFile)
            Gson().toJson(userList, writer)
            writer.close()
        }

    }

    private fun loadUsers() : MutableList<User> {
        var jsonUsers = mutableListOf<User>()

        try {
            val reader: Reader = FileReader(pathToUserFile)
            val REVIEW_TYPE : Type = object : TypeToken<List<User?>?>() {}.type
            jsonUsers = Gson().fromJson(reader, REVIEW_TYPE) // contains the whole reviews list
            reader.close()
        } catch (e : FileNotFoundException) {
            val writer: Writer = FileWriter(pathToUserFile)
            Gson().toJson(jsonUsers, writer)
            writer.close()
        }

        return jsonUsers
    }

}
