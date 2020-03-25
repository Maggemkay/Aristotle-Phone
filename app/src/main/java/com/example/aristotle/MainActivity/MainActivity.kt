package com.example.aristotle.MainActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aristotle.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.aristotle.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Fragmnent Switcher
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when(item.itemId){
            R.id.calendar -> {
                replaceFragment(CalendarFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.newMeeting -> {
                replaceFragment(InMeetingFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.statistics -> {
                replaceFragment(StatisticsFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.contacts -> {
                replaceFragment(ContactsFragment())
                return@OnNavigationItemSelectedListener true
            }

        }

        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Setting the default view of the app
        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        replaceFragment(CalendarFragment())
    }



    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentHolderMain , fragment)
        fragmentTransaction.commit()
    }


}
