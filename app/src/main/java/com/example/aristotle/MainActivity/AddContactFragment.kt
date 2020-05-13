package com.example.aristotle.MainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aristotle.R
import kotlinx.android.synthetic.main.fragment_addcontact.*
import kotlinx.android.synthetic.main.fragment_addcontact.view.*


/**
 * A simple [Fragment] subclass.
 */

class AddContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addcontact, container, false)

        /**adduserbutton.setOnClickListener() {
            val userid = textInputID.text.toString()
            val password = textInputUserPassword.text.toString()

        }*/

    }



}
