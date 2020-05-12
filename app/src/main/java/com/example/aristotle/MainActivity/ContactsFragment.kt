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
import java.io.FileReader
import java.io.FileWriter
import java.io.Reader
import java.io.Writer
import java.lang.reflect.Type
import java.io.*


class ContactsFragment : Fragment() {

    private lateinit var usersRecyclerView: androidx.recyclerview.widget.RecyclerView

    lateinit var userList: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveUsers()
        // Get the local users
        userList = loadUsers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        saveUsers()

        return inflater.inflate(R.layout.fragment_viewcontact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersRecyclerView = view.findViewById(R.id.UsersRecyclerView)
        usersRecyclerView.layoutManager = LinearLayoutManager(this.context)
        usersRecyclerView.adapter = UsersRecyclerViewAdapter(userList)
        usersRecyclerView.hasFixedSize()
    }


    private fun saveUsers(){
        val user = listOf<User>(
            User("1", "Bobus", "", "cool@email1", "Bob", "Foo"),
            User("3", "Kobus", "", "cool@email2", "Bart", "Baz"),
            User("2", "Fobus", "", "cool@email3", "Lisa", "Bar")
        )
        val writer: Writer = FileWriter(this.context?.filesDir?.path + "/Users.json" )
        Gson().toJson(user, writer)
        writer.close()
    }

    private fun loadUsers() : List<User> {
        val reader: Reader = FileReader(this.context?.filesDir?.path + "/Users.json")
        val REVIEW_TYPE: Type = object : TypeToken<List<User?>?>() {}.type
        val jsonUsers : List<User> =
            Gson().fromJson(reader, REVIEW_TYPE) // contains the whole reviews list

        reader.close()
        return jsonUsers
    }

}
