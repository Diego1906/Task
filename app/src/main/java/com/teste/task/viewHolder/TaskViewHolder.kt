package com.teste.task.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.teste.task.R
import com.teste.task.entities.TaskEntity

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDueDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)

    fun bindData(taskEntity: TaskEntity) {
        mTextDescription.text = taskEntity.description
        mTextPriority.text = taskEntity.priorityId.toString()
        mTextDueDate.text = taskEntity.duedate

        var resId: Int = 0
        when (taskEntity.complete) {
            true -> {
                resId = R.drawable.ic_done
            }
            else -> {
                resId = R.drawable.ic_todo
            }
        }
        mImageTask.setImageResource(resId)
    }
}
