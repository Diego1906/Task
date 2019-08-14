package com.teste.task.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teste.task.R

class TaskListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* arguments?.let {
             mParam1 = it.getString(ARG_PARAM1)
             mParam2 = it.getString(ARG_PARAM2)
         }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TaskListFragment().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, mParam1)
                    putString(ARG_PARAM2, mParam2)
                }*/
            }
    }
}
