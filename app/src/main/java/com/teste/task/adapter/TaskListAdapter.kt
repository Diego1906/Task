package com.teste.task.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teste.task.R
import com.teste.task.entities.TaskEntity
import com.teste.task.viewHolder.TaskViewHolder

class TaskListAdapter(val taskList: List<TaskEntity>) : RecyclerView.Adapter<TaskViewHolder>() {

    // Criação do Layout na memória
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view)
    }

    // Popula os itens do layout com os dados todas. Essa função é chamada todas as vezes que uma linha for criada.
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindData(task)
    }

    // Retorna a quantidade de itens do layout
    override fun getItemCount(): Int {
        return taskList.count()
    }
}