package com.example.aristotle


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aristotle.LoginActivity.LoginActivity
import com.example.aristotle.MainActivity.MainActivity
import kotlinx.android.synthetic.main.fragment_viewprofile.*
import kotlinx.android.synthetic.main.fragment_viewprofile.view.*

class ViewProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_viewprofile, container, false)

        view.viewProfileFullName.setText(APIHandler.user?.firstName + " " + APIHandler.user?.lastName)
        view.viewProfileEmail.setText(APIHandler.user?.email)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonViewProfileLogout.setOnClickListener {
            val act = activity as MainActivity
            act.logout()
        }
    }



}