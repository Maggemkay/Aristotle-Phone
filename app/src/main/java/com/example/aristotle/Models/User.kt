package com.example.aristotle.Models

class User(
    val id: String,
    var username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String
) {

    init {
        if (username == "") {
            username = firstName;
        }
    }

}