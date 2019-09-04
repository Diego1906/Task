package com.teste.task.views

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.teste.task.R
import com.teste.task.business.PriorityBusiness
import com.teste.task.business.TaskBusiness
import com.teste.task.constants.TaskConstants
import com.teste.task.entities.PriorityEntity
import com.teste.task.entities.TaskEntity
import com.teste.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private var mListPrioritiesEntity: MutableList<PriorityEntity> = mutableListOf()
    private var mListPrioritiesId: MutableList<Int> = mutableListOf()

    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    private val mSimpleDateFormat = SimpleDateFormat(getString(R.string.pattern_date))
    private var mTaskId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        loadPriorities()
        loadDataFromActivity()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonDate -> {
                openDatePickerDialog()
            }
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        buttonDate.text = mSimpleDateFormat.format(calendar.time)
    }

    private fun setListeners() {
        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun openDatePickerDialog() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, dayOfMonth).show()
    }

    private fun loadPriorities() {
        mListPrioritiesEntity = mPriorityBusiness.getList()

        mListPrioritiesId = mListPrioritiesEntity.map {
            it.id
        }.toMutableList()

        val lstPrioritiesDescription = mListPrioritiesEntity.map {
            it.description
        }

        spinnerPriority.apply {
            adapter =
                ArrayAdapter<String>(
                    context,
                    android.R.layout.simple_spinner_dropdown_item,
                    lstPrioritiesDescription
                )
        }
    }

    private fun loadDataFromActivity() {
        intent.extras?.let {
            mTaskId = it.getInt(TaskConstants.BUNDLE.TASK_ID)

            val task = mTaskBusiness.get(mTaskId)
            task?.let {
                editDescription.setText(it.description)
                spinnerPriority.setSelection(getIndex(it.priorityId))
                checkComplete.isChecked = it.complete
                buttonDate.text = it.duedate

                buttonSave.text = getString(R.string.atualizar_tarefa)
            }
        }
    }

    private fun getIndex(id: Int): Int {
        var index: Int = 0
        for (i in 0..mListPrioritiesEntity.size) {
            if (mListPrioritiesEntity[i].id == id) {
                index = i
                break
            }
        }
        return index
    }

    private fun handleSave() {

        try {
            val description = editDescription.text.toString()
            val priorityId = mListPrioritiesId[spinnerPriority.selectedItemPosition]
            val complete = checkComplete.isChecked
            val duedate = buttonDate.text.toString()
            val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()

            val taskEntity = TaskEntity(mTaskId, userId, priorityId, description, complete, duedate)

            when (mTaskId) {
                0 -> {
                    mTaskBusiness.insert(taskEntity)
                    messageShow(getString(R.string.tarefa_incluida_com_sucesso))
                }
                else -> {
                    mTaskBusiness.update(taskEntity)
                    messageShow(getString(R.string.tarefa_atualizada_com_sucesso))
                }
            }

            finish()

        } catch (e: Exception) {
            messageShow(getString(R.string.erro_inesperado))
        }
    }

    fun messageShow(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
