package com.example.aristotle.MainActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aristotle.*
import com.example.aristotle.LoginActivity.LoginActivity
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
    val changePasswordFragment = ChangePasswordFragment()
    val viewProfileFragment = ViewProfileFragment()

    // Fragmnent Switcher
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.calendar -> {
                fragmentSwitcher(calendarFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.newMeeting -> {
                fragmentSwitcher(meetingFragment)
//                fragmentSwitcher(inMeetingFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.statistics -> {
                fragmentSwitcher(statisticsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.contacts -> {
                fragmentSwitcher(contactsFragment)
//                fragmentSwitcher(viewProfileFragment)
                return@OnNavigationItemSelectedListener true
            }

//            R.id.changepassword -> {
//                fragmentSwitcher(changePasswordFragment)
//                return@OnNavigationItemSelectedListener true
//            }

            else -> false
        }
    }

    fun fragmentSwitcher(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentHolderMain, fragment)
            //.setCustomAnimation(R.anim.slide_in_left, R.anim.slide_out_right)
            .addToBackStack(null)
            .commit()
    }

    fun logout() {
        APIHandler.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        Setting the default view of the app
        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentHolderMain, calendarFragment)
        fragmentTransaction.commit()

//      Setting the action bar in the toolbar's place
        setSupportActionBar(findViewById(R.id.toolbar1))

        val actionbar1: ActionBar? = supportActionBar
        actionbar1?.setDisplayHomeAsUpEnabled(true)
        actionbar1?.setDisplayShowTitleEnabled(true)

        toolbar1.setNavigationIcon(R.drawable.arrow)
        toolbar1.setNavigationOnClickListener { toolbar1 }
        toolbar1.setNavigationOnClickListener(View.OnClickListener {
            // perform whatever you want on back arrow click
            Toast.makeText(this, "Back ", Toast.LENGTH_LONG).show()
        })


//Setting the main action bar in the toolbar's place
        setSupportActionBar(findViewById(R.id.toolbar))

        val actionbar: ActionBar? = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationIcon(R.drawable.notifications)
        toolbar.setNavigationOnClickListener { toolbar }
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            // perform whatever you want on back arrow click
            Toast.makeText(this, "Notifications", Toast.LENGTH_LONG).show()
        })


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

// //      Bottom nav bar switching fragments
//     private fun replaceFragment(fragment: Fragment) {
//         val fragmentTransaction = supportFragmentManager.beginTransaction()
//         fragmentTransaction.replace(R.id.fragmentContainer, fragment)
//         fragmentTransaction.commit()
// }

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