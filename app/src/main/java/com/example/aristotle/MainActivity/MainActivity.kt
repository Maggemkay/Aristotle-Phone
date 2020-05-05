package com.example.aristotle.MainActivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aristotle.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

// Navigation fragments for bottom nav bar
    private val m0nNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            //        Fragmnent Switcher
            when (item.itemId) {
                R.id.calendar -> {
                    println("calendar pressed")
                    replaceFragment(CalendarFragment())

                    toolbar1.visibility = View.INVISIBLE
                    profile_image.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE

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
//                    hide primary toolbar, profile icon and text and reveal secondary toolbar
                    toolbar.visibility = View.INVISIBLE
                    profile_image.visibility = View.INVISIBLE
                    toolbar1.visibility = View.VISIBLE

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
            Toast.makeText(this, "Profile icon pressed", Toast.LENGTH_LONG).show()
        }
    }


    //    load options menu into Top AppBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

//      Bottom nav bar switching fragments
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
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