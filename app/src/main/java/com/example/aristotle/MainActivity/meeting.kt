package com.example.aristotle.MainActivity

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.aristotle.R
import kotlinx.android.synthetic.main.activity_meeting.*
import kotlinx.android.synthetic.main.fragment_calendar.view.*

class meeting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting)

//      Gradient Animation initializing
        val animDrawable = InMeeting.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()




//      Play/Pause and Stop Button Handling
        var pause = false
        PlayPauseButton.setOnClickListener() {
            if (pause == false) {
                PlayPauseButton.setImageResource(R.drawable.resume)
                Toast.makeText(this , "Pause Button Pressed" , Toast.LENGTH_SHORT).show()
                pause = true
            }
            else {
                PlayPauseButton.setImageResource(R.drawable.pause)
                Toast.makeText(this , "Resume Button Pressed" , Toast.LENGTH_SHORT).show()
                pause = false
            }
        }

        StopButton.setOnClickListener() {
//Alert Dialog initialization
            val builder = AlertDialog.Builder(this@meeting)
            builder.setTitle("Exit Meeting ?")
            builder.setMessage("You are about to end the current meeting." + "Are you sure you want to end it?")
// Setting positive button value and action
            builder.setPositiveButton("END") { dialog , which->
                Toast.makeText(this , "Stop Button Pressed , meeting finished" , Toast.LENGTH_SHORT).show()
            }
// Setting negative button value and action
            builder.setNegativeButton("CANCEL") { dialog , which ->
                Toast.makeText(this , "Stop Button Pressed , meeting resumed" , Toast.LENGTH_SHORT).show()
            }
// initializing builder into a val and displaying the dialog
            val dialog: AlertDialog = builder.create()
            dialog.show()
// Changing negative button color
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)


        }

// Show transcriptions
        TranscriptionsButton.setOnClickListener {
            Transcriptions.visibility = View.VISIBLE
        }

        InMeeting.setOnClickListener() {
            Transcriptions.visibility = View.INVISIBLE
        }

    }







}
