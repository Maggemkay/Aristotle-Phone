package com.example.aristotle.MainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.aristotle.R
import kotlinx.android.synthetic.main.fragment_inmeeting.*

import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer

import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InMeetingFragment : Fragment() {

    // Replace below with your own subscription key
    private val speechSubscriptionKey = "90477e8df88e4ebca77c765846d66795"
    // Replace below with your own service region (e.g., "westus").
    private val serviceRegion = "northeurope"

    var config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion)!!
    var reco = SpeechRecognizer(config)
    var pause = true

    var result = ""
    lateinit var notesTextView: TextView

    var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestCode = 5 // unique code for the permission request
        ActivityCompat.requestPermissions(
            this.context as Activity,
            arrayOf<String>(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET),
            requestCode
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inmeeting, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesTextView = transcriptionTextView as TextView
        notesTextView.setMovementMethod(ScrollingMovementMethod())
        notesTextView.setText(result)
        

//        val animDrawable = InMeeting.background as AnimationDrawable
//        animDrawable.setEnterFadeDuration(10)
//        animDrawable.setExitFadeDuration(5000)
//        animDrawable.start()

        try {
            reco.recognizing.addEventListener({ _, e -> notesTextView.text = result + e.getResult().getText()+". " })
            reco.recognized.addEventListener({ _, e -> result = notesTextView.text.toString() + "\n"})
            Log.e("Text", notesTextView.text.toString())

        } catch (ex: Exception) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.message)
            Log.e("Text", notesTextView.text.toString())
            assert(false)
        }

        transcriptionStartPause.setOnClickListener() {
            if (pause == false) {
//                PlayPauseButton.setImageResource(R.drawable.resume)
                recogniztionStop()
                Toast.makeText(this.context , "Pause Button Pressed" , Toast.LENGTH_SHORT).show()
                pause = true
            }
            else {
//                PlayPauseButton.setImageResource(R.drawable.pause)
                recogniztionStart()
                Toast.makeText(this.context , "Resume Button Pressed" , Toast.LENGTH_SHORT).show()
                pause = false
            }
        }

        transcriptionStop.setOnClickListener {
            val builder = this.context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Exit Meeting ?")
            builder?.setMessage("You are about to end the current meeting. Are you sure you want to end it?")
            builder?.setPositiveButton("END MEETING") { dialog, which->
                Toast.makeText(this.context , "Stop Button Pressed , meeting finished" , Toast.LENGTH_SHORT).show()
                recogniztionStop()
            }
            builder?.setNegativeButton("CANCEL") { dialog, which ->
                Toast.makeText(this.context , "Continuing meeting" , Toast.LENGTH_SHORT).show()
            }
            val dialog: AlertDialog = builder!!.create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        }

        WriteNotesButton.setOnClickListener {
            isEditing = true
            transcriptionTextView.visibility = View.VISIBLE
            recogniztionStop()
            transcriptionTextView.setFocusable(true);
            transcriptionTextView.setFocusableInTouchMode(true);
            transcriptionTextView.setClickable(true);
        }

        SaveButton.setOnClickListener {
            saveTextToFile()
        }

        TranscriptionsButton.setOnClickListener {
            transcriptionTextView.visibility = View.VISIBLE
        }

        InMeeting.setOnClickListener() {
            if (isEditing) {
                isEditing = false
                transcriptionTextView.setFocusable(false)
                transcriptionTextView.setFocusableInTouchMode(false)
                transcriptionTextView.setClickable(false)
                result = transcriptionTextView.text.toString() + "\n"
                recogniztionStart()
            }

            transcriptionTextView.visibility = View.INVISIBLE
        }

    }

    private fun recogniztionStart() {
        // Starts continuous recognition. Uses stopContinuousRecognitionAsync() to stop recognition.
        reco.startContinuousRecognitionAsync().get()
    }

    private fun recogniztionStop() {
        reco.stopContinuousRecognitionAsync().get()
    }

    private fun saveTextToFile() {
        val currentDateTime = LocalDateTime.now()
        var extension = ".txt"
        var fileName = this.context?.filesDir?.path + "/" + currentDateTime.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + extension

        Log.d("Reading data filename", fileName)

        var file = File(fileName)

        // create a new file
        val isNewFileCreated :Boolean = file.createNewFile()

        if(isNewFileCreated){
            file.writeText(notesTextView.text.toString())
            Toast.makeText(this.context,"data saved",Toast.LENGTH_LONG).show()

            var testfile = File(fileName)

            Log.d("Reading data", testfile.toString())
        } else{
            Toast.makeText(this.context,"already exists.",Toast.LENGTH_LONG).show()

            var testfile = File(fileName)
            Log.d("Reading data", testfile.bufferedReader().readLines().toString())
        }
    }
}
