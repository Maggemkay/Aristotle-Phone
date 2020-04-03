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

    // TODO: Check up credential max-values that the API can handle
    // Use during registration of new users.
    fun validate(): Pair<Boolean, String> {
        return when {
            !((id.length == 10 || id.length == 12) && (id.toDoubleOrNull() != null)) -> Pair(
                false,
                "personal number"
            )
            !("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.{5,})".toRegex().find(password) != null) -> Pair(false, "password")
            !("\\S+@\\S+\\.\\S+".toRegex().find(email) != null) -> Pair(false, "E-mail")
            firstName == "" -> Pair(false, "first name")
            lastName == "" -> Pair(false, "last name")

            else -> Pair(true, "")
        }
    }
}