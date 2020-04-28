package com.example.aristotle.MainActivity

import android.Manifest
import android.app.Activity
import android.content.Context
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

import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult

import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.io.FileOutputStream
import java.io.*

class InMeetingFragment : Fragment() {

    // Replace below with your own subscription key
    private val speechSubscriptionKey = "1a19105f812048d1981d7c37f9bb2019"
//    private val speechSubscriptionKey = "967ff322c456498b86b73d7dec3ad592"
    // Replace below with your own service region (e.g., "westus").
    private val serviceRegion = "northeurope"

    var config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion)!!
    var reco = SpeechRecognizer(config)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val animDrawable = InMeeting.background as AnimationDrawable
//        animDrawable.setEnterFadeDuration(10)
//        animDrawable.setExitFadeDuration(5000)
//        animDrawable.start()

        //  Play/Pause and Stop Button Handling

        var result = ""
        var txt = transcriptionTextView as TextView
        txt.setMovementMethod(ScrollingMovementMethod())
        txt.text = ""



        try {
            reco.recognizing.addEventListener({ _, e -> txt.text = result + e.getResult().getText()+". " })
            reco.recognized.addEventListener({ _, e -> result = txt.text.toString() + "\n"})
            Log.e("Text", txt.text.toString())

            // Starts continuous recognition. Uses stopContinuousRecognitionAsync() to stop recognition.
            reco.startContinuousRecognitionAsync().get()
        } catch (ex: Exception) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.message)
            Log.e("Text", txt.text.toString())
            assert(false)
        }

        var pause = false
        transcriptionStartPause.setOnClickListener() {

            if (pause == false) {
//                PlayPauseButton.setImageResource(R.drawable.resume)
                Toast.makeText(this.context , "Pause Button Pressed" , Toast.LENGTH_SHORT).show()
                pause = true
            }
            else {
//                PlayPauseButton.setImageResource(R.drawable.pause)
                Toast.makeText(this.context , "Resume Button Pressed" , Toast.LENGTH_SHORT).show()
                pause = false
            }
        }

        transcriptionStop.setOnClickListener() {
            var txt = transcriptionTextView as TextView
            txt.text = "Stopped transcribing"
            reco.stopContinuousRecognitionAsync().get()

            //Alert Dialog initialization
            val builder = this.context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Exit Meeting ?")
            builder?.setMessage("You are about to end the current meeting." + "Are you sure you want to end it?")
            // Setting positive button value and action
            builder?.setPositiveButton("END") { dialog, which->
                Toast.makeText(this.context , "Stop Button Pressed , meeting finished" , Toast.LENGTH_SHORT).show()
            }
            // Setting negative button value and action
            builder?.setNegativeButton("CANCEL") { dialog, which ->
                Toast.makeText(this.context , "Stop Button Pressed , meeting resumed" , Toast.LENGTH_SHORT).show()
            }
            // initializing builder into a val and displaying the dialog
            val dialog: AlertDialog = builder!!.create()
            dialog.show()
            // Changing negative button color
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        }

        SaveButton.setOnClickListener {
//            val writeDir = this.context?.filesDir?.path + "/transcriptions/"
//            var fileName = writeDir + "example.txt"
            var fileName = this.context?.filesDir?.path + "/example.txt"

            Log.d("Reading data filename", fileName)

            var file = File(fileName)

//            Log.d("Reading data BIG BOI TEST", file.bufferedReader().readLines().toString())

            // create a new file
            val isNewFileCreated :Boolean = file.createNewFile()

            if(isNewFileCreated){
                file.writeText(txt.text.toString())
                Toast.makeText(this.context,"data saved",Toast.LENGTH_LONG).show()

                var testfile = File(fileName)

                Log.d("Reading data", testfile.toString())

            } else{
                Toast.makeText(this.context,"already exists.",Toast.LENGTH_LONG).show()

                var testfile = File(fileName)

                Log.d("Reading data", testfile.bufferedReader().readLines().toString())
            }

    }

        // Show transcriptions
        TranscriptionsButton.setOnClickListener {
            transcriptionTextView.visibility = View.VISIBLE
        }

        InMeeting.setOnClickListener() {
            transcriptionTextView.visibility = View.INVISIBLE
        }

    }


}
