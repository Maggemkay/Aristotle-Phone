package com.example.aristotle.LoginActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aristotle.APIHandler
import com.example.aristotle.MainActivity.MainActivity
import com.example.aristotle.R

class LoginActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    val loginFragment = LoginFragment()
    val createAccountFragment = LoginCreateAccountFragment()

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

        // Sets initial fragment
        setContentView(R.layout.activity_login)
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentHolderLogin , loginFragment)
        fragmentTransaction.commit()
    }

    fun fragmentSwitcher(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderLogin , fragment)
            //.setCustomAnimation(R.anim.slide_in_left, R.anim.slide_out_right)
            .addToBackStack(null)
            .commit()
    }

}