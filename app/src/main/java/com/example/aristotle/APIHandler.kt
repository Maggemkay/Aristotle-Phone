package com.example.aristotle

import com.example.aristotle.Models.Session
import com.example.aristotle.Models.User
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import io.github.cdimascio.dotenv.dotenv


object APIHandler {

    val dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    }

    private val url = "http://" + dotenv["API_IP"] + ":" + dotenv["API_PORT"]
    var token = ""
    var personalId = ""


    suspend fun login(username: String, password: String, callback: (success: Boolean) -> Unit) {
        val session = loginRequest(username, password)
        callback(
            when (session.auth) {
                true -> {
                    this.token = session.token
                    this.personalId = username
                    true
                }
                else -> {
                    token = ""
                    false
                }
            }
        )
    }


    suspend fun loginRequest(username: String, password: String): Session {
        val (_, _, result) = run {
            Fuel.post("$url/")
                .jsonBody(""""{
                        "username": "$username",
                        "password": "$password",
                        "grant_type": "password" }""")
                .awaitStringResponseResult()
        }

        var token = Session(false, "")
        result.fold({ data ->
            token = Gson().fromJson(data, Session::class.java)
            // token = Json.parse(Session.serializer(), data)
        }, {
            print("Failed with login attempt.")
        })
        return token
    }


    fun logout(): Boolean {
        return when (token) {
            "" -> { false }
            else -> {
                token = ""
                personalId = ""
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
