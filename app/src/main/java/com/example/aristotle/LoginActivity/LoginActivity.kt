package com.example.aristotle.LoginActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aristotle.APIHandler
import com.example.aristotle.MainActivity.MainActivity
import com.example.aristotle.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = this.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        APIHandler.token = sharedPref.getString("TOKEN", "")
        APIHandler.personalId = sharedPref.getString("ID", "")

        if (!APIHandler.token.equals("")) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_login)
    }
}