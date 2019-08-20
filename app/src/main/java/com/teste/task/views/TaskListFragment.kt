package com.teste.task.views

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.teste.task.R
import com.teste.task.adapter.TaskListAdapter
import com.teste.task.business.TaskBusiness
import com.teste.task.constants.DataBaseConstants
import com.teste.task.util.SecurityPreferences
import com.teste.task.util.extensions.starNewActivity

class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)

        mContext = rootView.context
        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)

        // Passos para RecyclerView funcionar
        // 1 - Obter o elemento
        // 2 - Definir um adapter com os itens de listagem
        // 3 - Definir um layout

        // Passo 1
        mRecyclerTaskList = rootView.findViewById<RecyclerView>(R.id.recyclerTaskList)

        // Passo 2
        val userId = mSecurityPreferences.getStoredString(DataBaseConstants.TASK.COLUMNS.USER_ID).toInt()
        val taskList = mTaskBusiness.getList(userId)

        for (i in 0..50){
            taskList.add(taskList[0].copy(description = "Descrição $i"))
        }

        mRecyclerTaskList.apply {
            adapter = TaskListAdapter(taskList)
        }

        // Passo 3
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)

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
