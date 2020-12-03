package com.example.contact.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Adapter.UserAdapter
import com.example.contact.AddNewContactActivity
import com.example.contact.Callback.SwipeToDeleteCallback
import com.example.contact.R
import com.example.contact.ViewModel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_user.view.*

class UserFragment() : Fragment() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        //set up recycler view
        val useradapter = UserAdapter()
        val recycler_view = view.recycler_view
        recycler_view.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        recycler_view.adapter = useradapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.readAllUser.observe(viewLifecycleOwner, Observer { user->
            useradapter.setData(user)
        })

        //set up add new contact button
        val AddNewContact = view.findViewById<FloatingActionButton>(R.id.addNewContact)

        AddNewContact.setOnClickListener {
            val intent = Intent(activity, AddNewContactActivity::class.java)
            startActivity(intent)
        }

        //add the swipe to delete callback function
        val swipeHandler = object: SwipeToDeleteCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userViewModel.deleteUser(useradapter.getUserAt(viewHolder.adapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view)

        return view
    }
}