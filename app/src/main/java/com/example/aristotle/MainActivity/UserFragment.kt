package com.example.aristotle.MainActivity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aristotle.R
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteContact.setOnClickListener() {
                       //Alert Dialog initialization
            val builder = this.context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Delete contact ?")
            builder?.setMessage("You are about to delete a contact. \n" + "Are you sure you want to?")
            // Setting positive button value and action
            builder?.setPositiveButton("DELETE") { dialog, which->
                Toast.makeText(this.context , "Contact deleted" , Toast.LENGTH_SHORT).show()
            }
            // Setting negative button value and action
            builder?.setNegativeButton("CANCEL") { dialog, which ->
                Toast.makeText(this.context , "Deletion canceled" , Toast.LENGTH_SHORT).show()
            }
            // initializing builder into a val and displaying the dialog
            val dialog: AlertDialog = builder!!.create()
            dialog.show()
            // Changing negative button color
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
        }




    }


    private fun initRecyclerView() {
        val recyclerView: RecyclerView = recyclerHistory
        val adapter = RecyclerViewAdapter(this.requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
    }
}
