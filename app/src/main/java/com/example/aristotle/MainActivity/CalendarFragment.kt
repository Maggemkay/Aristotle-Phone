package com.example.aristotle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.aristotle.MainActivity.Adapters.CalendarRecyclerViewAdapter
import com.example.aristotle.MainActivity.MainActivity
import com.example.aristotle.Models.Meeting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.io.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var calendarRecyclerViewAdapter: CalendarRecyclerViewAdapter

    private lateinit var pathToMeetingsFile : String

    private var meetingsList = mutableListOf<Meeting>()
    private var shownMeetings = mutableListOf<Meeting>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pathToMeetingsFile = this.context?.filesDir?.path + "/Meetings.json"

        calendarRecyclerViewAdapter = CalendarRecyclerViewAdapter(this, shownMeetings)

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this.context)
        calendarRecyclerView.adapter = calendarRecyclerViewAdapter
        calendarRecyclerView.hasFixedSize()

        calendarView.showCurrentMonthPage()

        meetingsList = loadMeetings()
        var eventDayList = mutableListOf<EventDay>()

        for (meeting in meetingsList) {
            val meetingCalendar = Calendar.getInstance()
            meetingCalendar.time = meeting.startTime
            eventDayList.add(EventDay(meetingCalendar, R.drawable.orange_dot))
        }

        calendarView.setEvents(eventDayList)

        calendarView.setOnDayClickListener(object :
            OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                shownMeetings.clear()
                val today = Calendar.getInstance()
                val meetingTime = Calendar.getInstance()
                for (meeting in meetingsList) {
                    meetingTime.time = meeting.startTime
                    if (eventDay.calendar.get(Calendar.YEAR) == meetingTime.get(Calendar.YEAR) &&
                        eventDay.calendar.get(Calendar.MONTH) == meetingTime.get(Calendar.MONTH) &&
                        eventDay.calendar.get(Calendar.DAY_OF_MONTH) == meetingTime.get(Calendar.DAY_OF_MONTH)) {

                        shownMeetings.add(meeting)
                    }
                }

                if (makeSimpleDate(eventDay.calendar) == makeSimpleDate(today))
                    recyclerViewHeader.text = "Today"
                else if (eventDay.calendar.before(today))
                    recyclerViewHeader.text = "Previous meetings"
                else if (eventDay.calendar.after(today))
                    recyclerViewHeader.text = "Coming meetings"
                if (shownMeetings.isEmpty()) {
                    noEvents.visibility = View.VISIBLE
                }
                else {
                    noEvents.visibility = View.INVISIBLE
                    shownMeetings.sortBy { meeting -> meeting.startTime }
                }
                calendarRecyclerViewAdapter.updateList(shownMeetings)
            }
        })
        

//        startTime.setOnClickListener {
//            val animation = AnimationUtils.loadAnimation(
//                activity, R.anim.slide_up)
//            setter.visibility = View.VISIBLE
//            setter.startAnimation(animation)
//            deactivateElements()
//
//            currentSelected = startTimeSelected
//
//        }


        // Exit the popup when pressing outside of it
        calendarLayout.setOnClickListener() {
            popup.visibility = View.INVISIBLE
        }


//        // Setting text field to be uneditable
//        popUpMeetingName.setEnabled(false)
//        popUpMeetingTime.setEnabled(false)
//        popUpMeetingRoom.setEnabled(false)
//
//
////  Edit button click listener
//        var editing = false
//        edit.setOnClickListener(){
//            if (!editing) {
//                edit.setImageResource(R.drawable.edit_icon_selected)
////                popUpMeetingName.focusable = View.FOCUSABLE
//                popUpMeetingName.setEnabled(true)
//                popUpMeetingTime.setEnabled(true)
//                popUpMeetingRoom.setEnabled(true)
//                editing = true
//            }
//            else
//            {
//                edit.setImageResource(R.drawable.edit_icon)
//                popUpMeetingName.setEnabled(false)
//                popUpMeetingTime.setEnabled(false)
//                popUpMeetingRoom.setEnabled(false)
//                editing = false
//            }
//        }

    }

    override fun onResume() {
        super.onResume()
        meetingsList = loadMeetings()
        shownMeetings.clear()
        val today = Calendar.getInstance()
        val meetingTime = Calendar.getInstance()
        for (meeting in meetingsList) {
            meetingTime.time = meeting.startTime
            if (today.get(Calendar.YEAR) == meetingTime.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == meetingTime.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == meetingTime.get(Calendar.DAY_OF_MONTH)) {
                shownMeetings.add(meeting)
            }
        }
        if (shownMeetings.isEmpty()) {
            noEvents.visibility = View.VISIBLE
            return
        }
        else {
            noEvents.visibility = View.INVISIBLE
            shownMeetings.sortBy { meeting -> meeting.startTime }
        }
        calendarRecyclerViewAdapter.updateList(shownMeetings)
    }

    fun startMeeting(meetingPos: Int) {
        val act = activity as MainActivity
        act.inMeetingFragment.loadClickedMeeting(shownMeetings[meetingPos])
        act.fragmentSwitcher(act.inMeetingFragment)
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

    private fun makeSimpleDate(calendar: Calendar): String {
        val fmt = SimpleDateFormat("yyyyMMdd")
        return fmt.format(calendar.time)
    }
}
