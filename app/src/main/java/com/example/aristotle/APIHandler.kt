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
    var email = ""


    suspend fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        val session = loginRequest(email, password)

        callback(
            when (session.auth) {
                true -> {
                    this.token = session.token
                    this.email = email
                    true
                }
                else -> {
                    token = ""
                    false
                }
            }
        )
    }


    suspend fun loginRequest(email: String, password: String): Session {
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


    fun logout(): Boolean {
        return when (token) {
            "" -> { false }
            else -> {
                token = ""
                email = ""
                true
            }
        }
    }


    suspend fun registerRequest(user: User): Boolean {
        val (_, _, result) = run {
            Fuel.post("$url/users/")
                .jsonBody(Gson().toJson(user))
                .awaitStringResponseResult()
        }
        return result.fold({ true }, { error -> print(error.message); false })
    }


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
}