package com.example.aristotle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_meeting.*
import java.util.*

class MeetingFragment : Fragment() {

    private var selectedDate = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePicker.setIs24HourView(true)
        calendarViewSet.showCurrentMonthPage()

        time.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_up)
            setter.visibility = View.VISIBLE
            setter.startAnimation(animation)
            time.isEnabled = false
        }

        cancelTime.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_down)
            setter.startAnimation(animation)
            setter.visibility = View.INVISIBLE
            val c = Calendar.getInstance()
            timePicker.hour = c.get(Calendar.HOUR_OF_DAY)
            timePicker.minute = c.get(Calendar.MINUTE)
            calendarViewSet.showCurrentMonthPage()

            time.isEnabled = true
        }

        doneTime.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_down)
            setter.startAnimation(animation)
            setter.visibility = View.INVISIBLE
            time.isEnabled = true
            Toast.makeText(activity , "Selected time " + timePicker.hour + " : " + timePicker.minute , Toast.LENGTH_SHORT).show()
            selectedDate.set(Calendar.HOUR, timePicker.hour)
            selectedDate.set(Calendar.MINUTE, timePicker.minute)

            Log.d("Selected year", selectedDate.get(Calendar.YEAR).toString())
            Log.d("Selected month", selectedDate.get(Calendar.MONTH).toString())
            Log.d("Selected day", selectedDate.get(Calendar.DAY_OF_MONTH).toString())
            Log.d("Selected hour", selectedDate.get(Calendar.HOUR).toString())
            Log.d("Selected minute", selectedDate.get(Calendar.MINUTE).toString())
        }

        calendarViewSet.setOnDayClickListener { eventDay ->
            val clickedDayCalendar: Calendar = eventDay.calendar

            selectedDate.set(Calendar.YEAR, clickedDayCalendar.get(Calendar.YEAR))
            selectedDate.set(Calendar.MONTH, clickedDayCalendar.get(Calendar.MONTH))
            selectedDate.set(Calendar.DAY_OF_MONTH, clickedDayCalendar.get(Calendar.DAY_OF_MONTH))
        }

    }

}
