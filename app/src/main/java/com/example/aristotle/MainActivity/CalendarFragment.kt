package com.example.aristotle


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aristotle.MainActivity.meeting
import kotlinx.android.synthetic.main.fragment_calendar.*


/**
 * A simple [Fragment] subclass.
 */
class CalendarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        meeting_btn1.setOnClickListener() {
            openameeting()
        }

    }

    public fun openameeting() {
        val intent = Intent(context, meeting::class.java)
        startActivity(intent)
    }
}
