package com.example.aristotle.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.aristotle.R
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult


class MainActivity : AppCompatActivity() {


    // Replace below with your own subscription key
    private val speechSubscriptionKey = "7d9be8793b544d1bb23cf3141aa83db0"
    // Replace below with your own service region (e.g., "westus").
    private val serviceRegion = "northeurope"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onSpeechButtonClicked(v: View)
    {
        var txt = this.findViewById(R.id.text) as TextView // 'hello' is the ID of your text view
        txt.text = "Text"

        try {
            var config = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion)!!

            var reco = SpeechRecognizer(config)

            var task = reco.recognizeOnceAsync()!!

            // Note: this will block the UI thread, so eventually, you want to
            //        register for the event (see full samples)
            var result = task.get()!!

            if (result.reason == ResultReason.RecognizedSpeech) {
                txt.text = result.toString()
            } else {
                txt.text =
                    "Error recognizing. Did you update the subscription info?" + System.lineSeparator() + result.toString()
            }

            reco.close()
        } catch (ex: Exception) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.message)
            assert(false)
        }

    }
}
