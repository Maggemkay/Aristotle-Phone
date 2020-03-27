package com.example.aristotle.MainActivity

import androidx.fragment.app.Fragment

import android.Manifest.permission.INTERNET
import android.Manifest.permission.RECORD_AUDIO
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup

import com.example.aristotle.R
import kotlinx.android.synthetic.main.fragment_transcription.*

class TranscriptionFragment : Fragment() {

    // Replace below with your own subscription key
    private val speechSubscriptionKey = "967ff322c456498b86b73d7dec3ad592"
    // Replace below with your own service region (e.g., "westus").
    private val serviceRegion = "northeurope"

    var config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion)!!
    var reco = SpeechRecognizer(config)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestCode = 5 // unique code for the permission request
        ActivityCompat.requestPermissions(
            this.context as Activity,
            arrayOf<String>(RECORD_AUDIO, INTERNET),
            requestCode
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transcription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transcriptionStart.setOnClickListener {
            var result = ""
            var txt = transcriptionTextView as TextView
            txt.setMovementMethod(ScrollingMovementMethod())
            txt.text = ""

            try {
                reco.recognizing.addEventListener({ _, e -> txt.text = result + e.getResult().getText()+". " })
                reco.recognized.addEventListener({ _, e -> result = txt.text.toString() + "\n"})

                // Starts continuous recognition. Uses stopContinuousRecognitionAsync() to stop recognition.
                reco.startContinuousRecognitionAsync().get()
            } catch (ex: Exception) {
                Log.e("SpeechSDKDemo", "unexpected " + ex.message)
                assert(false)
            }
        }

        transcriptionStop.setOnClickListener {
            var txt = transcriptionTextView
            txt.text = "Stopped transcribing"
            reco.stopContinuousRecognitionAsync().get()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        reco.close()
    }

}
