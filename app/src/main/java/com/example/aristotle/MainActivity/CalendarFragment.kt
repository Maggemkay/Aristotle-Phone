package com.example.aristotle


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aristotle.MainActivity.InMeetingFragment
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*


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
//            openameeting()
        }


//      Click handler for the calendar
        calendarView.setOnDayClickListener { eventDay ->
            val clickedDayCalendar: Calendar = eventDay.calendar
            println(clickedDayCalendar)


            availableMeeting.visibility = View.VISIBLE
            popup.visibility = View.VISIBLE
            noEvents.visibility = View.INVISIBLE
        }

//      Set calendar to start at current month
        calendarView.showCurrentMonthPage()

//       Exit the popup when pressing outside of it
        calendarLayout.setOnClickListener() {
            popup.visibility = View.INVISIBLE
        }

// Setting text field to be uneditable
        popUpMeetingName.setEnabled(false)
        popUpMeetingTime.setEnabled(false)
        popUpMeetingRoom.setEnabled(false)


//  Edit button click listener
        var editing = false
        edit.setOnClickListener(){
            if (!editing) {
                edit.setImageResource(R.drawable.edit_icon_selected)
//                popUpMeetingName.focusable = View.FOCUSABLE
                popUpMeetingName.setEnabled(true)
                popUpMeetingTime.setEnabled(true)
                popUpMeetingRoom.setEnabled(true)
                editing = true
            }
            else
            {
                edit.setImageResource(R.drawable.edit_icon)
                popUpMeetingName.setEnabled(false)
                popUpMeetingTime.setEnabled(false)
                popUpMeetingRoom.setEnabled(false)
                editing = false
            }
        }

    }

    private fun openameeting() {
        val intent = Intent(context, InMeetingFragment::class.java)
        startActivity(intent)
    }
}
