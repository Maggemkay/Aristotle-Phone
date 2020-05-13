package com.example.aristotle.MainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aristotle.Models.User
import com.example.aristotle.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_addcontact.*
import kotlinx.android.synthetic.main.fragment_addcontact.view.*
import java.io.*
import java.lang.reflect.Type


class AddContactFragment : Fragment() {

    private lateinit var pathToUserFile : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pathToUserFile = this.context?.filesDir?.path + "/Users.json"

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addcontact, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveNewContactButton.setOnClickListener {

            val newUser = User("", "", "", textInputEditEmail.text.toString(), textInputEditFirstName.text.toString(), textInputEditLastName.text.toString())

            saveUsers(newUser)

            val act = activity as MainActivity
            act.fragmentSwitcher(act.contactsFragment)
        }
    }

    private fun saveUsers(newUser: User){
        var usersJson = loadUsers()

        if (!usersJson.contains(newUser)) {
            usersJson.add(newUser)
            val writer: Writer = FileWriter(pathToUserFile)
            Gson().toJson(usersJson, writer)
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
