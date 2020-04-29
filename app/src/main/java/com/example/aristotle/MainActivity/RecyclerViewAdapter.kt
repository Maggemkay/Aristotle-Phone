package com.example.aristotle.MainActivity

import com.example.aristotle.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
//https://www.youtube.com/watch?v=Vyqz_-sJGFk tutorial for this spaghetti fuckoletti

class RecyclerViewAdapter(
    context: Context
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val mContext: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        private const val TAG = "RecyclerViewAdapter"
    }

    init {
        mContext = context
    }
}