package com.example.aristotle

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.aristotle.MainActivity.MainActivity
import kotlinx.android.synthetic.main.fragment_meeting.*
import java.util.*

class MeetingFragment : Fragment() {

    private var startTimeSelected = Calendar.getInstance()
    private var endTimeSelected = Calendar.getInstance()

    private lateinit var currentSelected : Calendar

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

        startTime.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_up)
            setter.visibility = View.VISIBLE
            setter.startAnimation(animation)
            deactivateElements()

            currentSelected = startTimeSelected
        }

        endTime.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_up)
            setter.visibility = View.VISIBLE
            setter.startAnimation(animation)
            deactivateElements()

            currentSelected = endTimeSelected
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
            activateElements()
        }

        doneTime.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_down)
            setter.startAnimation(animation)
            setter.visibility = View.INVISIBLE

            Toast.makeText(activity , "Selected time " + timePicker.hour.toString().padStart(2, '0') + ":" + timePicker.minute.toString().padStart(2, '0') , Toast.LENGTH_SHORT).show()
            currentSelected.set(Calendar.HOUR, timePicker.hour)
            currentSelected.set(Calendar.MINUTE, timePicker.minute)

            Log.d("Selected year", currentSelected.get(Calendar.YEAR).toString())
            Log.d("Selected month", currentSelected.get(Calendar.MONTH).toString())
            Log.d("Selected day", currentSelected.get(Calendar.DAY_OF_MONTH).toString())
            Log.d("Selected hour", currentSelected.get(Calendar.HOUR_OF_DAY).toString())
            Log.d("Selected minute", currentSelected.get(Calendar.MINUTE).toString())

            activateElements()
        }

        calendarViewSet.setOnDayClickListener { eventDay ->
            val clickedDayCalendar: Calendar = eventDay.calendar
            currentSelected.set(Calendar.YEAR, clickedDayCalendar.get(Calendar.YEAR))
            currentSelected.set(Calendar.MONTH, clickedDayCalendar.get(Calendar.MONTH))
            currentSelected.set(Calendar.DAY_OF_MONTH, clickedDayCalendar.get(Calendar.DAY_OF_MONTH))
        }

        buttonNewMeetingBook.setOnClickListener {
            saveMeeting()

            val builder = this.context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Your meeting is booked")
            builder?.setMessage("Your meeting have been booked and added to the calendar.")
            builder?.setPositiveButton("Ok") { dialog, which->
                val act = activity as MainActivity
                act.fragmentSwitcher(act.calendarFragment)
            }
            val dialog: AlertDialog = builder!!.create()
            dialog.show()
        }

    }

    private fun activateElements() {
        startTime.isEnabled = true
        endTime.isEnabled = true
        textInputMeetingSubject.isEnabled = true
        textInputLocation.isEnabled = true
    }

    private fun deactivateElements() {
        startTime.isEnabled = false
        endTime.isEnabled = false
        textInputMeetingSubject.isEnabled = false
        textInputLocation.isEnabled = false
    }

    private fun saveMeeting() {
        // Save the meeting to local storage

    }


}
