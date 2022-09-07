package com.sinamekidev.jettrivia.repository

import com.sinamekidev.jettrivia.data.DataOrException
import com.sinamekidev.jettrivia.model.QuestionItem
import com.sinamekidev.jettrivia.network.QuestionAPI
import javax.inject.Inject


class QuestionRepository @Inject constructor(private val questionAPI: QuestionAPI) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>,Boolean,Exception>()

    suspend fun getDataOrException():DataOrException<ArrayList<QuestionItem>,Boolean,Exception>{
        try {
            dataOrException.loaded = true
            dataOrException.data = questionAPI.getQuestions()
            if (dataOrException.data == null){
                dataOrException.loaded = false
            }
        }catch (e:Exception){
            dataOrException.loaded = false
            dataOrException.exception = e
        }
        return dataOrException
    }
}