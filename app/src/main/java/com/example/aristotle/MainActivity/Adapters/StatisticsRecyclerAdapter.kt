package com.example.aristotle.MainActivity.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.aristotle.R
import com.example.aristotle.Models.Meeting
import com.example.aristotle.StatisticsFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.io.*
import java.lang.reflect.Type

class StatisticsRecyclerAdapter(private var meetingData: List<Meeting>, val statisticsFragment: StatisticsFragment) : RecyclerView.Adapter<StatisticsRecyclerAdapter.MeetingViewHolder>() {

    class MeetingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val meetingSubject: TextView = itemView.findViewById(R.id.Title_subject)
        val startTime: TextView = itemView.findViewById(R.id.notes)
        val endTime: TextView = itemView.findViewById(R.id.transcription)
        val participants: TextView = itemView.findViewById(R.id.attendance)
        val location: TextView = itemView.findViewById(R.id.activity)
        val meetingClickArea: ConstraintLayout = itemView.findViewById(R.id.meetingShowNotes)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        return MeetingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.statistics_dropdown, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        holder.meetingSubject.text = meetingData[position].subject
        holder.startTime.text = meetingData[position].startTime.toString()
        holder.endTime.text = meetingData[position].endTime.toString()

        var meetingParticipants = ""
        if(meetingData[position].participants.isNotEmpty())
        {
            for (meeting in meetingData[position].participants)
            {
                val participant = meeting.firstName + " " + meeting.lastName
                meetingParticipants = "$meetingParticipants, $participant"
                meetingParticipants = meetingParticipants.drop(1)
            }
        }
        else
        {
            meetingParticipants = "No participants selected"
        }
        holder.participants.text = meetingParticipants

        holder.location.text = meetingData[position].location

        holder.meetingClickArea.setOnClickListener{
            var file = File(meetingData[position].notePath)
            var fileText = file.readText()
            statisticsFragment.meetingNotes.text = fileText
            statisticsFragment.meetingNotes.visibility = View.VISIBLE
        }

        statisticsFragment.statisticsLayout.setOnClickListener{
            statisticsFragment.meetingNotes.visibility = View.INVISIBLE
        }
    }


    override fun getItemCount() = meetingData.size

    fun updateList(newMeetingList: List<Meeting>) {
        meetingData = newMeetingList
        notifyDataSetChanged()
    }

}