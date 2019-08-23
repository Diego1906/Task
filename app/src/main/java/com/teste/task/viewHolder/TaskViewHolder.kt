package com.teste.task.viewHolder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.content.ContextCompat
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

        // Evento de click para edição
        mTextDescription.setOnClickListener(View.OnClickListener {
            listener.onListClick(taskEntity.id)
        })

        mTextDescription.setOnLongClickListener(View.OnLongClickListener {
            showConfirmationDialog(taskEntity)
            true
        })
    }

    private fun showConfirmationDialog(task: TaskEntity) {
        AlertDialog.Builder(context)
            .setTitle("Confirmação")
            .setMessage("Deseja remover a tarefa '${task.description}' ?")
            .setIcon(R.drawable.ic_delete)
            .setNegativeButton("Cancelar", null)
            .setPositiveButton(
                "Remover",
                DialogInterface.OnClickListener { _, _ -> listener.onDeleteClick(task.id) })
            .show()
    }
}
