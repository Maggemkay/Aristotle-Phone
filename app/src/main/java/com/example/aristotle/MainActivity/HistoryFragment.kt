package com.example.aristotle


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_history.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aristotle.MainActivity.Adapters.HistoryRecyclerAdapter
import com.example.aristotle.Models.Meeting
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type


class HistoryFragment : Fragment() {

    private lateinit var statisticsRecyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var statisticsRecyclerAdapter: HistoryRecyclerAdapter

    private var meetingList: MutableList<Meeting> = mutableListOf<Meeting>()
    private var searchMeetingList: MutableList<Meeting> = mutableListOf<Meeting>()

    lateinit var pathToMeetingFile : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pathToMeetingFile = this.context?.filesDir?.path + "/Meetings.json"

        meetingList = loadMeetings()

        statisticsRecyclerAdapter =
            HistoryRecyclerAdapter(
                meetingList,
                this
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statisticsRecyclerView= view.findViewById(R.id.statistics_dropdown_recyclerview)
        statisticsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        statisticsRecyclerView.adapter = statisticsRecyclerAdapter
        statisticsRecyclerView.hasFixedSize()


        searchMeetingSubject.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    searchMeetingList = meetingList.filter { (it.subject).startsWith(s) } as MutableList<Meeting>
                    statisticsRecyclerAdapter.updateList(searchMeetingList)
                }
                else
                    statisticsRecyclerAdapter.updateList(meetingList)
            }
        })

        meetingNotes.setOnClickListener {
            meetingNotes.visibility = View.INVISIBLE
        }
}

    override fun onResume() {
        super.onResume()
        meetingList = loadMeetings()
        searchMeetingList = meetingList
        statisticsRecyclerAdapter.updateList(meetingList)
    }

    private fun loadMeetings() : MutableList<Meeting> {
        var meetingsJson = mutableListOf<Meeting>()

        try {
            val reader: Reader = FileReader(pathToMeetingFile)
            val REVIEW_TYPE : Type = object : TypeToken<List<Meeting?>?>() {}.type
            meetingsJson = Gson().fromJson(reader, REVIEW_TYPE) // contains the whole reviews list
            reader.close()
        } catch (e : FileNotFoundException) {
            val writer: Writer = FileWriter(pathToMeetingFile)
            Gson().toJson(meetingsJson, writer)
            writer.close()
        }

        return meetingsJson
    }
}
