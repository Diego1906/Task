package com.teste.task.views

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.teste.task.R
import com.teste.task.adapter.TaskListAdapter
import com.teste.task.util.extensions.starNewActivity

class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mRecyclerTaskList: RecyclerView

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
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)

        // Passos para RecyclerView funcionar
        // 1 - Obter o elemento
        // 2 - Definir um adapter com os itens de listagem
        // 3 - Definir um layout

        // Passo 1
        mRecyclerTaskList = rootView.findViewById<RecyclerView>(R.id.recyclerTaskList)

        // Passo 2
        mRecyclerTaskList.apply {
            adapter = TaskListAdapter()
        }

        // Passo 3
        mRecyclerTaskList.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        return rootView
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.floatAddTask -> {
                this.starNewActivity(TaskFormActivity())
            }
        }
    }
}
