package com.example.aristotle.Models

class User(
    var username: String,
    val personalIdNumber: String,
    val password: String,
    val email: String,
    val address: String,
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
            !((personalIdNumber.length == 10 || personalIdNumber.length == 12) && (personalIdNumber.toDoubleOrNull() != null)) -> Pair(
                false,
                "personal number"
            )
            !("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.{5,})".toRegex().find(password) != null) -> Pair(false, "password")
            !("\\S+@\\S+\\.\\S+".toRegex().find(email) != null) -> Pair(false, "E-mail")
            address == "" -> Pair(false, "address")
            firstName == "" -> Pair(false, "first name")
            lastName == "" -> Pair(false, "last name")

            else -> Pair(true, "")
        }
    }
}