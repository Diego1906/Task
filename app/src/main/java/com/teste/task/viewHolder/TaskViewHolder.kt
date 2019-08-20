package com.teste.task.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.teste.task.R
import com.teste.task.entities.TaskEntity

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)

    fun bindData(taskEntity: TaskEntity) {
        mTextDescription.text = taskEntity.description
    }
}
