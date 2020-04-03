package com.example.aristotle.LoginActivity

import android.os.Bundle
import android.util.Log
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
            val username = editTextUsername.text.toString().replace("\\s".toRegex(), "")
            val firstName = editTextFirstName.text.toString().replace("\\s".toRegex(), "")
            val lastName = editTextLastName.text.toString().replace("\\s".toRegex(), "")
            val email = editTextEmail.text.toString().replace("\\s".toRegex(), "")
            val password = editTextPassword.text.toString().replace("\\s".toRegex(), "")
            val confirmPassword = editTextConfirmPassword.text.toString().replace("\\s".toRegex(), "")

            // TODO: Handle confirm password.

            var isValid = true

            if (!isValid || username.isBlank() || username.isEmpty() || username.length > 64)
                isValid = false
            if (!isValid || firstName.isBlank() || firstName.isEmpty() || firstName.length > 64)
                isValid = false
            if (!isValid || lastName.isBlank() || lastName.isEmpty() || lastName.length > 64)
                isValid = false
            if (!isValid || email.isBlank() || email.isEmpty() || email.length > 256)
                isValid = false
            if (!isValid || password.isBlank() || password.isEmpty() || password.length > 64)
                isValid = false
            if (!isValid || confirmPassword.isBlank() || confirmPassword.isEmpty() || confirmPassword.length > 64 || password != confirmPassword)
                isValid = false


            activity?.runOnUiThread {
                if (isValid) {
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
                                    act.onBackPressed()
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
                        "Invalid input!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        buttonCancel.setOnClickListener {
            val act = activity as LoginActivity
            act.onBackPressed()
        }


    }


}
