package com.teste.task.repository

import com.teste.task.entities.PriorityEntity

class PriorityCacheConstants private constructor() {

    // Salvar em cache os dados das prioridades
    companion object {

        private val mPriorityCache: HashMap<Int, String> = hashMapOf<Int, String>()

        fun setCache(list: List<PriorityEntity>) {
            for (item in list) {
                mPriorityCache.put(item.id, item.description)
            }
        }

        fun getPriorityDescription(id: Int): String {
            mPriorityCache[id]?.let {
                return it
            }
            return ""
        }
    }
}