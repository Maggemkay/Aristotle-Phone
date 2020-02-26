package com.example.aristotle.MainActivity

import android.Manifest.permission.INTERNET
import android.Manifest.permission.RECORD_AUDIO
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult

import android.R
import java.util.*


class MainActivity : AppCompatActivity() {



    // Replace below with your own subscription key
    private val speechSubscriptionKey = "967ff322c456498b86b73d7dec3ad592"
    // Replace below with your own service region (e.g., "westus").
    private val serviceRegion = "northeurope"

    var config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion)!!

    var reco = SpeechRecognizer(config)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.aristotle.R.layout.activity_main)
        val requestCode = 5 // unique code for the permission request
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf<String>(RECORD_AUDIO, INTERNET),
            requestCode
        )
    }

    fun onSpeechButtonClicked(v: View)
    {
        var txt = this.findViewById(com.example.aristotle.R.id.text) as TextView // 'hello' is the ID of your text view
        txt.text = "Text"

        try {

            reco.recognizing.addEventListener({ s, e -> txt.text = "RECOGNIZING: Text=" + e.getResult().getText() })


            // Starts continuous recognition. Uses stopContinuousRecognitionAsync() to stop recognition.
            println("Say something...")
            reco.startContinuousRecognitionAsync().get()


        } catch (ex: Exception) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.message)
            assert(false)
        }

    }

    fun onStopButtonClicked(v: View)
    {
        var txt = this.findViewById(com.example.aristotle.R.id.text) as TextView // 'hello' is the ID of your text view
        txt.text = "Stopped transcribing"
        reco.stopContinuousRecognitionAsync().get()
        reco.close()
    }
}
