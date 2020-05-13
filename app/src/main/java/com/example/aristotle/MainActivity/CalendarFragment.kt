package com.example.aristotle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.aristotle.MainActivity.InMeetingFragment
import com.example.aristotle.Models.Meeting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.io.*
import java.lang.reflect.Type
import java.util.*


class CalendarFragment : Fragment() {

    private lateinit var pathToMeetingsFile : String

    private var meetingsList = mutableListOf<Meeting>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pathToMeetingsFile = this.context?.filesDir?.path + "/Meetings.json"

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView.showCurrentMonthPage()

        meetingsList = loadMeetings()
        var eventDayList = mutableListOf<EventDay>()

        for (meeting in meetingsList) {
            val meetingCalendar = Calendar.getInstance()
            meetingCalendar.time = meeting.startTime
            eventDayList.add(EventDay(meetingCalendar, R.drawable.small_orange_dot))
        }

        calendarView.setEvents(eventDayList)


        calendarView.setOnDayClickListener(object :
            OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                Log.d("Day clicked", eventDay.calendar.time.toString())
            }
        })


        meeting_btn1.setOnClickListener() {
//            openameeting()
        }


        // Exit the popup when pressing outside of it
        calendarLayout.setOnClickListener() {
            popup.visibility = View.INVISIBLE
        }


//      Click handler for the calendar
//        calendarView.setOnDayClickListener { eventDay ->
//            val clickedDayCalendar: Calendar = eventDay.calendar
//            println(clickedDayCalendar)
//
//
//            availableMeeting.visibility = View.VISIBLE
//            popup.visibility = View.VISIBLE
//            noEvents.visibility = View.INVISIBLE
//        }

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

    private fun loadMeetings() : MutableList<Meeting> {
        var meetingsJson = mutableListOf<Meeting>()

        try {
            val reader: Reader = FileReader(pathToMeetingsFile)
            val REVIEW_TYPE : Type = object : TypeToken<List<Meeting?>?>() {}.type
            meetingsJson = Gson().fromJson(reader, REVIEW_TYPE) // contains the whole reviews list
            reader.close()
        } catch (e : FileNotFoundException) {
            val writer: Writer = FileWriter(pathToMeetingsFile)
            Gson().toJson(meetingsJson, writer)
            writer.close()
        }

        return meetingsJson
    }
}
