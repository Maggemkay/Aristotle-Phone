package com.example.aristotle.MainActivity.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aristotle.R
import com.example.aristotle.Models.Meeting
import com.example.aristotle.StatisticsFragment
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsRecyclerAdapter(private var meetingData: List<Meeting>, var statisticsFragment: StatisticsFragment) : RecyclerView.Adapter<StatisticsRecyclerAdapter.MeetingViewHolder>() {

    class MeetingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val meeting_title: TextView = itemView.findViewById(R.id.Title_subject)
        val start_time: TextView = itemView.findViewById(R.id.notes)
        val end_time: TextView = itemView.findViewById(R.id.transcription)
        val participants: TextView = itemView.findViewById(R.id.attendance)
        val location: TextView = itemView.findViewById(R.id.activity)

        /*
        fun bind(meeting: Meeting, clickListener: AdapterView.OnItemClickListener)
        {
            Log.d("Test tag", "Test")
            itemView.setOnClickListener{
                clickListener.onItemClick(meeting)
            }
        }
         */
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        return MeetingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.statistics_dropdown, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val meetingTitle = meetingData[position].subject
        holder.meeting_title.text = meetingTitle
        val meetingStartTime = meetingData[position].startTime.toString()
        holder.start_time.text = meetingStartTime
        val meetingEndTime = meetingData[position].endTime.toString()
        holder.end_time.text = meetingEndTime

        var meetingParticipants: String = ""
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

        val meetingLocation : String = meetingData[position].location.toString()
        holder.location.text = meetingLocation

        holder.meeting_title.setOnClickListener{
            Log.d("bajs", "och kiss")
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