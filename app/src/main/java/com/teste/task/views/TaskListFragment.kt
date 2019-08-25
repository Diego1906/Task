package com.teste.task.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

import com.teste.task.R
import com.teste.task.adapter.TaskListAdapter
import com.teste.task.business.TaskBusiness
import com.teste.task.constants.TaskConstants
import com.teste.task.entities.OnTaskListFragmentInteractionListener
import com.teste.task.util.extensions.starNewActivity

class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private var mTaskFilter: Int = 0
    private lateinit var mListener: OnTaskListFragmentInteractionListener

    companion object {

        @JvmStatic
        fun newInstance(taskFilter: Int) = TaskListFragment().apply {
            arguments = Bundle().apply {
                putInt(TaskConstants.TASK_FILTER.KEY, taskFilter)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mTaskFilter = it.getInt(TaskConstants.TASK_FILTER.KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        try {

            rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)

            mContext = rootView.context
            mTaskBusiness = TaskBusiness(mContext)

            // Classe anônima
            mListener = object : OnTaskListFragmentInteractionListener {

                override fun onListClick(taskId: Int) {
                    val bundle: Bundle = Bundle()
                    bundle.putInt(TaskConstants.BUNDLE.TASK_ID, taskId)

                    val intent: Intent = Intent(mContext, TaskFormActivity::class.java)
                    intent.putExtras(bundle)

                    startActivity(intent)
                }

                override fun onDeleteClick(taskId: Int) {
                    mTaskBusiness.delete(taskId)
                    loadTasks()
                    messageShow(getString(R.string.tarefa_removida_sucesso))
                }

                override fun onUnCompleteClick(taskId: Int) {
                    mTaskBusiness.complete(taskId, false)
                    loadTasks()
                }

                override fun onCompleteClick(taskId: Int) {
                    mTaskBusiness.complete(taskId, true)
                    loadTasks()
                }
            }

            // Passos para RecyclerView funcionar
            // 1 - Obter o elemento
            // 2 - Definir um adapter com os itens de listagem
            // 3 - Definir um layout

            // Passo 1
            mRecyclerTaskList = rootView.findViewById<RecyclerView>(R.id.recyclerTaskList)

            // Passo 2
            mRecyclerTaskList.apply {
                adapter = TaskListAdapter(mutableListOf(), mContext, mListener)
            }

            // Passo 3
            mRecyclerTaskList.layoutManager =
                LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)


        } catch (e: Exception) {
            messageShow(e.message.toString())
        }

        return rootView
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.floatAddTask -> {
                this.starNewActivity(TaskFormActivity())
            }
        }
    }

    override fun onResume() {
        super.onResume()

        loadTasks()
    }

    private fun loadTasks() {
        mRecyclerTaskList.apply {
            adapter = TaskListAdapter(mTaskBusiness.getList(mTaskFilter), mContext, mListener)
        }
    }

    private fun messageShow(message: String) {
        Toast.makeText(mContext, message, android.widget.Toast.LENGTH_LONG).show()
    }
}
