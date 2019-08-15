package com.teste.task.business

import android.content.Context
import com.teste.task.entities.PriorityEntity
import com.teste.task.repository.PriorityRepository

class PriorityBusiness(val context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()
}