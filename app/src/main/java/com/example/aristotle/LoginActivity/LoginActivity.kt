package com.example.aristotle.LoginActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.aristotle.APIHandler
import com.example.aristotle.MainActivity.MainActivity
import com.example.aristotle.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = this.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        APIHandler.token = sharedPref.getString("TOKEN", "")
        APIHandler.email = sharedPref.getString("EMAIL", "")

        APIHandler.token = ""

        if (!APIHandler.token.equals("")) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
//        else {
//            setContentView(R.layout.activity_login)
//        }

        setContentView(R.layout.activity_login)
    }


}