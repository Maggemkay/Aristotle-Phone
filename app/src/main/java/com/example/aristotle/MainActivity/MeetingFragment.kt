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
import java.text.SimpleDateFormat
import java.util.*

class MeetingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






//handler for showing the time
        time.setOnClickListener {
            //adding our custom made animation xml file
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_up)
            //appending animation to constraint layout
            setter.visibility = View.VISIBLE
            setter.startAnimation(animation)
            time.isEnabled = false
        }





// handler for cancel button
        cancelTime.setOnClickListener {
            //adding our custom made animation xml file
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_down)
            //appending animation to constraint layout
            setter.startAnimation(animation)
            setter.visibility = View.INVISIBLE
//          reset time timePicker to current time
            val c = Calendar.getInstance()
            timePicker.hour = c.get(Calendar.HOUR_OF_DAY)
            timePicker.minute = c.get(Calendar.MINUTE)
//          Set calendar back to current month
            calendarViewSet.showCurrentMonthPage()

            time.isEnabled = true
        }

//        handler for Done button
        doneTime.setOnClickListener() {
            //adding our custom made animation xml file
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_down)
            //appending animation to constraint layout
            setter.startAnimation(animation)
            setter.visibility = View.INVISIBLE
            time.isEnabled = true
            Toast.makeText(activity , "Selected time " + timePicker.hour + " : " + timePicker.minute , Toast.LENGTH_SHORT).show()
            Log.d("Selected time", timePicker.hour.toString() + " : " + timePicker.minute)
            Log.d("Selected date", calendarViewSet.firstSelectedDate.toString()) // selected date can be retrieved either like this or like in the code below, i added it here so it would logged when you press done. Below it'll be log when you select it 

        }
//        set time picker to 24 hour view
        timePicker.setIs24HourView(true)
//set calendar to current month
        calendarViewSet.showCurrentMonthPage()
//print selected day in gregorian calendar format
        calendarViewSet.setOnDayClickListener { eventDay ->
            val clickedDayCalendar: Calendar = eventDay.calendar
//            println(clickedDayCalendar)
            var date = clickedDayCalendar.toString() // date goes here
            Log.d("Selected Date", date)

        }

    }

}
