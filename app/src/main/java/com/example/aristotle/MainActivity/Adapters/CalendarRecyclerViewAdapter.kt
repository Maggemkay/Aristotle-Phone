package com.example.aristotle.MainActivity.Adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.aristotle.CalendarFragment
import com.example.aristotle.Models.Meeting
import com.example.aristotle.R
import java.util.*

class CalendarRecyclerViewAdapter(private val parentFragment: CalendarFragment,
                                  private var meetingsData: List<Meeting>)
    : RecyclerView.Adapter<CalendarRecyclerViewAdapter.CalendarMeetingsViewHolder>() {

    class CalendarMeetingsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val meetingSubejct: TextView = itemView.findViewById(R.id.meetingSubject)
        val meetingTime: TextView = itemView.findViewById(R.id.meetingTime)
        val meetingLocation: TextView = itemView.findViewById(R.id.meetingLocation)
        val startMeetingButton: ImageButton = itemView.findViewById(R.id.startMeetingButton)
        val startMeetingText: TextView = itemView.findViewById(R.id.startText)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMeetingsViewHolder {
        return CalendarMeetingsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recylcerviewitem_calendarmeeting, parent, false))
    }

    override fun onBindViewHolder(holder: CalendarMeetingsViewHolder, position: Int) {
        val start = Calendar.getInstance()
        start.time = meetingsData[position].startTime
        val end = Calendar.getInstance()
        end.time = meetingsData[position].endTime

        holder.meetingSubejct.text = meetingsData[position].subject
        holder.meetingLocation.text = meetingsData[position].location
        holder.meetingTime.text =
            start.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0') + ":" +
            start.get(Calendar.MINUTE).toString().padStart(2, '0') +
            " - " +
            end.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0') + ":" +
            end.get(Calendar.MINUTE).toString().padStart(2, '0')

        val today = Calendar.getInstance()
        start.set(Calendar.MINUTE, start.get(Calendar.MINUTE) - 10)

        holder.startMeetingButton.visibility = View.INVISIBLE
        holder.startMeetingText.visibility = View.INVISIBLE

        if (today.timeInMillis > start.timeInMillis && today.timeInMillis < end.timeInMillis) {
            holder.startMeetingButton.visibility = View.VISIBLE
            holder.startMeetingText.visibility = View.VISIBLE
            holder.startMeetingButton.setOnClickListener {
                val builder = parentFragment.context?.let { it1 -> AlertDialog.Builder(it1) }
                builder?.setTitle("Start Meeting?")
                builder?.setMessage("You are about to start the meeting. Are you sure you want to start it?")
                builder?.setPositiveButton("START MEETING") { dialog, which->
                    parentFragment.startMeeting(position)
                }
                builder?.setNegativeButton("CANCEL") { dialog, which -> }
                val dialog: AlertDialog = builder!!.create()
                dialog.show()
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            }
        }
    }

    override fun getItemCount() = meetingsData.size

    fun updateList(shownMeetingsList: List<Meeting>) {
        meetingsData = shownMeetingsList
        notifyDataSetChanged()
    }

}