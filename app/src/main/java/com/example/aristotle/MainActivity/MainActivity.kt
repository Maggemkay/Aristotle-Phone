package com.example.aristotle.MainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.aristotle.*
import com.example.aristotle.LoginActivity.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.aristotle.R
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    val calendarFragment = CalendarFragment()
    val meetingFragment = MeetingFragment()
    val statisticsFragment = StatisticsFragment()
    val contactsFragment = ContactsFragment()
    val inMeetingFragment = InMeetingFragment()
    val changePasswordFragment = ChangePasswordFragment()
    val viewProfileFragment = ViewProfileFragment()
    val addContactFragment = AddContactFragment()
    lateinit var bottomNavigation : BottomNavigationView

    // Fragmnent Switcher
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.calendar -> {
                fragmentSwitcher(calendarFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.newMeeting -> {
                fragmentSwitcher(meetingFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.history -> {
                fragmentSwitcher(statisticsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.contacts -> {
                fragmentSwitcher(contactsFragment)
                return@OnNavigationItemSelectedListener true
            }

            else -> false
        }
    }

    fun fragmentSwitcher(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderMain, fragment)
            .addToBackStack(null)
            .commit()

        if (fragment == calendarFragment)
            bottomNavigation.menu[0].setChecked(true)
    }

    fun logout() {
        APIHandler.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        bottomNavigation = bottom_navigation

//        Setting the default view of the app
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentHolderMain, calendarFragment)
        fragmentTransaction.commit()

//        clickhandler for profile picture
        profile_image.setOnClickListener(){
            fragmentSwitcher(viewProfileFragment)
//            Toast.makeText(this, "Profile icon pressed", Toast.LENGTH_LONG).show()
        }
    }


    //    load options menu into Top AppBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


// handling function for profile icon press
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.profile -> {
            Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

}