package com.example.aristotle.MainActivity

import android.os.Bundle
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
                fragmentSwitcher(CalendarFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.newMeeting -> {
                fragmentSwitcher(InMeetingFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.statistics -> {
                fragmentSwitcher(StatisticsFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.contacts -> {
                fragmentSwitcher(ContactsFragment())
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
        fragmentSwitcher(CalendarFragment())
    }



    private fun fragmentSwitcher(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentHolderMain , fragment)
        fragmentTransaction.commit()
    }


}
