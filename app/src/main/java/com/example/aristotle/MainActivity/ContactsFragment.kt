package com.example.aristotle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aristotle.MainActivity.Adapters.UsersRecyclerViewAdapter
import com.example.aristotle.MainActivity.MainActivity
import com.example.aristotle.Models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_addcontact.*
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
    private var searchUserList: MutableList<User> = mutableListOf<User>()

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


        searchContactsField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    searchUserList = userList.filter { (it.firstName + it.lastName).startsWith(s) } as MutableList<User>
                    userRecyclerViewAdapter.updateList(searchUserList)
                }
                else
                    userRecyclerViewAdapter.updateList(userList)
            }
        })

        addContactButton.setOnClickListener {
            val act = activity as MainActivity
            act.fragmentSwitcher(act.addContactFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        userList = loadUsers()
        searchUserList = userList
        userRecyclerViewAdapter.updateList(userList)
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
