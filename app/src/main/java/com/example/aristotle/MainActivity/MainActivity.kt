package com.example.aristotle.MainActivity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aristotle.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.aristotle.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    val calendarFragment = CalendarFragment()
    val meetingFragment = MeetingFragment()
    val statisticsFragment = StatisticsFragment()
    val contactsFragment = ContactsFragment()
    val inMeetingFragment = InMeetingFragment()

    // Fragmnent Switcher
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.calendar -> {
                fragmentSwitcher(calendarFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.newMeeting -> {
                fragmentSwitcher(inMeetingFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.statistics -> {
                fragmentSwitcher(statisticsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.contacts -> {
                fragmentSwitcher(contactsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun fragmentSwitcher(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderMain, fragment)
            //.setCustomAnimation(R.anim.slide_in_left, R.anim.slide_out_right)
            .addToBackStack(null)
            .commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        Setting the default view of the app
        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentHolderMain, calendarFragment)
        fragmentTransaction.commit()

//      Hiding the status bar so the app can be full screen.
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }





}
