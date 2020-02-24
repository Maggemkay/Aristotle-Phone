package com.example.aristotle.MainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aristotle.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private val m0nNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        Fragmnent Switcher
        when(item.itemId){
            R.id.calendar -> {
                println("calendar pressed")
                replaceFragment(CalendarFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.newMeeting -> {
                println("New Meeting pressed")
                replaceFragment(MeetingFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.statistics -> {
                println("Statistics pressed")
                replaceFragment(StatisticsFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.contacts -> {
                println("Contacts pressed")
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
        bottom_navigation.setOnNavigationItemSelectedListener(m0nNavigationItemSelectedListener)
        replaceFragment(CalendarFragment())



    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer , fragment)
        fragmentTransaction.commit()
    }

}
