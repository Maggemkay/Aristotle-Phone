package com.example.aristotle.LoginActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aristotle.APIHandler
import com.example.aristotle.Models.User
import com.example.aristotle.R
import kotlinx.android.synthetic.main.fragment_createaccount.*
import kotlinx.coroutines.runBlocking

class LoginCreateAccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_createaccount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignup.setOnClickListener {
            val username = editTextUsername.text.toString()
            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            // TODO: Handle confirm password.

            var check = true

            if (username.isBlank())
                check = false
            if (firstName.isBlank())
                check = false
            if (lastName.isBlank())
                check = false
            if (email.isBlank())
                check = false
            if (password.isBlank())
                check = false
            if (confirmPassword.isBlank())
                check = false

            activity?.runOnUiThread {
                if (check) {
                    val newUser = User(
                        id = "",
                        username = username,
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        password = password
                    )

                    runBlocking {
                        APIHandler.Register.register(newUser) {
                            val didRegister = it

                            activity?.runOnUiThread {
                                if (didRegister) {
                                    Toast.makeText(activity, "Account Created!", Toast.LENGTH_SHORT)
                                        .show()

                                    val act = activity as LoginActivity
                                    act.fragmentSwitcher(act.loginFragment)
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Could not create account.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        }
                    }
                } else {
                    Toast.makeText(
                        activity,
                        "Empty Fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        buttonCancel.setOnClickListener {
            val act = activity as LoginActivity
            act.fragmentSwitcher(act.loginFragment)
        }


    }


}
