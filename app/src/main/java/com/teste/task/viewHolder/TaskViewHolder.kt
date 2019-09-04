package com.teste.task.viewHolder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.teste.task.R
import com.teste.task.entities.OnTaskListFragmentInteractionListener
import com.teste.task.entities.TaskEntity
import com.teste.task.repository.PriorityCacheConstants

class TaskViewHolder(
    itemView: View,
    val context: Context,
    val listener: OnTaskListFragmentInteractionListener
) :
    RecyclerView.ViewHolder(itemView) {

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDueDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)

    fun bindData(taskEntity: TaskEntity) {
        mTextDescription.text = taskEntity.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(taskEntity.priorityId)
        mTextDueDate.text = taskEntity.duedate

        setImageTask(taskEntity)

        // Evento de click para edição
        mTextDescription.setOnClickListener(View.OnClickListener {
            listener.onListClick(taskEntity.id)
        })

        mTextDescription.setOnLongClickListener(View.OnLongClickListener {
            showConfirmationDialog(taskEntity)
            true
        })

        mImageTask.setOnClickListener(View.OnClickListener {
            if (taskEntity.complete) {
                listener.onUnCompleteClick(taskEntity.id)
            } else {
                listener.onCompleteClick(taskEntity.id)
            }
        })
    }

    private fun setImageTask(taskEntity: TaskEntity) {
        var resId: Int = R.drawable.ic_todo

        when (taskEntity.complete) {
            true -> {
                resId = R.drawable.ic_done
            }
        }
        mImageTask.setImageResource(resId)
    }

    private fun showConfirmationDialog(task: TaskEntity) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.confirmacao))
            .setMessage(context.getString(R.string.remover_tarefa) + task.description + "?" )
            .setIcon(R.drawable.ic_delete)
            .setNegativeButton(context.getString(R.string.cancelar), null)
            .setPositiveButton(
                context.getString(R.string.remover),
                DialogInterface.OnClickListener { _, _ -> listener.onDeleteClick(task.id) })
            .show()
    }
}
