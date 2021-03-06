package com.example.aristotle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.aristotle.MainActivity.MainActivity
import com.example.aristotle.Models.Meeting
import com.example.aristotle.Models.User
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_meeting.*
import java.io.*
import java.lang.reflect.Type
import java.util.*


class MeetingFragment : Fragment() {

    private lateinit var pathToMeetingsFile : String
    private lateinit var pathToUserFile : String

    private var startTimeSelected = Calendar.getInstance()
    private var endTimeSelected = Calendar.getInstance()
    private var isStartSelected = true

    private lateinit var participantChipGroup : ChipGroup
    private var clickedParticipants = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pathToMeetingsFile = this.context?.filesDir?.path + "/Meetings.json"
        pathToUserFile = this.context?.filesDir?.path + "/Users.json"

        return inflater.inflate(R.layout.fragment_meeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePicker.setIs24HourView(true)
        calendarViewSet.showCurrentMonthPage()

        clickedParticipants = mutableListOf<User>()

        participantChipGroup = participantsChipGroupView

        val users = loadUsers()

        for (user: User in users) {
            val chip = Chip(participantChipGroup.context)
            chip.setText(user.firstName + " " + user.lastName)
            chip.isCheckable = true
            participantChipGroup.addView(chip)

            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    clickedParticipants.add(user)
                else
                    clickedParticipants.remove(user)
            }

        }


        startTime.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_up)
            setter.visibility = View.VISIBLE
            setter.startAnimation(animation)
            deactivateElements()
            isStartSelected = true

        }

        endTime.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(
                activity, R.anim.slide_up)
            setter.visibility = View.VISIBLE
            setter.startAnimation(animation)
            deactivateElements()
            isStartSelected = false
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

            Toast.makeText(
                activity ,
                "Selected time " + timePicker.hour.toString().padStart(2, '0') + ":" + timePicker.minute.toString().padStart(2, '0') ,
                Toast.LENGTH_SHORT
            ).show()

            val cal = calendarViewSet.firstSelectedDate
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)

            if(isStartSelected) {
                startTimeSelected.set(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE)
                )
                startTime.setText(cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0') + ':' + cal.get(Calendar.MINUTE).toString().padStart(2, '0') + ' ' + cal.get(Calendar.DAY_OF_MONTH) + '/'  + (cal.get(Calendar.MONTH) + 1).toString() + "/" +  cal.get(Calendar.YEAR).toString())
            } else {
                endTimeSelected.set(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE)
                )
                endTime.setText(cal.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0') + ':' + cal.get(Calendar.MINUTE).toString().padStart(2, '0') + ' ' + cal.get(Calendar.DAY_OF_MONTH) + '/'  +(cal.get(Calendar.MONTH) + 1).toString() + "/" +  cal.get(Calendar.YEAR).toString())
            }

            activateElements()
        }

        buttonNewMeetingBook.setOnClickListener {
            val newMeeting = Meeting(
                startTimeSelected.time,
                endTimeSelected.time,
                editTextMeetingSubject.text.toString(),
                editTextLocation.text.toString(),
                clickedParticipants,
                ""
            )

            val builder = this.context?.let { it1 -> AlertDialog.Builder(it1) }
            var changeFragmentFlag = false

            if(editTextMeetingSubject.text!!.isEmpty())
            {
                builder?.setTitle("Subject field is empty")
                builder?.setMessage("You have not written a subject, please enter a subject to book a meeting")
            }
            else if(editTextLocation.text!!.isEmpty())
            {
                builder?.setTitle("Location field is empty")
                builder?.setMessage("You have not written a location, please enter a location to book a meeting")
            }
            else
            {
                saveMeeting(newMeeting)
                builder?.setTitle("Your meeting is booked")
                builder?.setMessage("Your meeting have been booked and added to the calendar.")
                changeFragmentFlag = true
            }

            builder?.setPositiveButton("Ok") { dialog, which->
                val act = activity as MainActivity
                if(changeFragmentFlag)
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
        buttonNewMeetingBook.isEnabled = true
    }

    private fun deactivateElements() {
        startTime.isEnabled = false
        endTime.isEnabled = false
        textInputMeetingSubject.isEnabled = false
        textInputLocation.isEnabled = false
        buttonNewMeetingBook.isEnabled = false
    }

    private fun saveMeeting(newMeeting: Meeting) {
        var meetingsJson = loadMeetings()

        if (!meetingsJson.contains(newMeeting)) {
            meetingsJson.add(newMeeting)
        }
        val writer: Writer = FileWriter(pathToMeetingsFile)
        Gson().toJson(meetingsJson, writer)
        writer.close()

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

    private fun loadUsers() : MutableList<User> {
        var jsonUsers = mutableListOf<User>()

        try {
            val reader: Reader = FileReader(pathToUserFile)
            val REVIEW_TYPE : Type = object : TypeToken<List<User?>?>() {}.type
            jsonUsers = Gson().fromJson(reader, REVIEW_TYPE) // contains the whole reviews list
            reader.close()
        } catch (e : FileNotFoundException) {
            val writer: Writer = FileWriter(pathToUserFile)
            Gson().toJson(jsonUsers, writer)
            writer.close()
        }

        return jsonUsers
    }


}
