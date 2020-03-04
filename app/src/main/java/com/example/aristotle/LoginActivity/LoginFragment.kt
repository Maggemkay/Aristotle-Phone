package com.example.aristotle.LoginActivity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.aristotle.APIHandler
import com.example.aristotle.MainActivity.MainActivity

import com.example.aristotle.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin.setOnClickListener {
            val email = textInputEditTextLoginEmail.text.toString()
            val password = textInputEditTextLoginPassword.text.toString()

            Thread {
                runBlocking {
                    APIHandler.login(email, password) {
                        val didLogin = it

                        activity?.runOnUiThread {
                            if (didLogin) {
                                Toast.makeText(activity, "Signed in successfully!", Toast.LENGTH_SHORT).show()

                                val sharedPrefs = context!!.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
                                val sharedPrefsEditor = sharedPrefs.edit()
                                sharedPrefsEditor.putString("TOKEN", APIHandler.token)
                                sharedPrefsEditor.putString("EMAIL", APIHandler.email)
                                sharedPrefsEditor.apply()

                                startActivity(Intent(activity, MainActivity::class.java))
                                activity!!.finish()
                            } else {
                                Toast.makeText(activity, "Failed signing in.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }.start()
        }

    }
}
