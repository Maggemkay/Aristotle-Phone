package com.example.aristotle

import com.example.aristotle.Models.Session
import com.example.aristotle.Models.User
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.runBlocking


object APIHandler {

    val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }

    private val url = "http://" + dotenv["API_IP"] + ":" + dotenv["API_PORT"]
    var token = ""
//    var email = ""

    lateinit var user: User


    object Login {
        suspend fun login(inputEmail: String, inputPassword: String, callback: (Boolean) -> Unit) {
            val session = loginRequest(inputEmail, inputPassword)

            callback(
                when (session.auth) {
                    true -> {
                        token = session.token
                        user = getLoginUser(token)
                        true
                    }
                    else -> {
                        token = ""
                        false
                    }
                }
            )
        }

        private suspend fun loginRequest(email: String, password: String): Session {
            val (_, _, result) = runBlocking {
                Fuel.post("$url/")
                    .jsonBody("""{
                    "email": "$email",
                    "password": "$password"
                    }""")
                    .awaitStringResponseResult()
            }

            var session = Session(false, "")

            result.fold({ data ->
                session = Session(true, data)
            }, {
                print("Failed with login attempt.")
            })

            return session
        }

        private suspend fun getLoginUser(token: String): User {
            val (_, _, result) = runBlocking {
                Fuel.get("$url/users/id")
                    .set("Authorization", token)
                    .awaitStringResponseResult()
            }

            lateinit var user: User

            result.fold({ data ->
                user = Gson().fromJson(data, User::class.java)
            }, {
                print("Failed to get user.")
            })

            return user
        }


    }

    fun logout(): Boolean {
        return when (token) {
            "" -> { false }
            else -> {
                token = ""
//                email = ""
                true
            }
        }
    }

    object Register {

        suspend fun register(user: User, callback: (successful: Boolean) -> Unit) {
            callback(
                when (registerRequest(user)) {
                    false -> {
                        print("Registration failed!"); false
                    }
                    else -> {
                        true
                    }
                }
            )
        }

        private suspend fun registerRequest(user: User): Boolean {
            val (_, _, result) = run {
                Fuel.post("$url/users")
                    .jsonBody(Gson().toJson(user))
                    .awaitStringResponseResult()
            }
            return result.fold({ true }, { error -> print(error.message); false })
        }
    }

}

