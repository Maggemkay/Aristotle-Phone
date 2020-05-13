package com.example.aristotle.MainActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aristotle.R
import com.example.aristotle.Models.Meeting

class StatisticsRecyclerAdapter(private var meetingData: List<Meeting>) : RecyclerView.Adapter<StatisticsRecyclerAdapter.MeetingViewHolder>() {

    class MeetingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.MeetingRecyclerViewItem)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        return MeetingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.statistics_dropdown, parent, false))
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val fullName = meetingData[position].firstName + " " + meetingData[position].lastName

        holder.name.text = fullName
    }

    override fun getItemCount() = usersData.size

    fun updateList(newUserList: List<User>) {
        usersData = newUserList
        notifyDataSetChanged()
    }
}