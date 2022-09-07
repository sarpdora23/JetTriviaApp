package com.sinamekidev.jettrivia.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinamekidev.jettrivia.data.DataOrException
import com.sinamekidev.jettrivia.model.QuestionItem
import com.sinamekidev.jettrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val questionRepository: QuestionRepository) :ViewModel() {
    var data = mutableStateOf(DataOrException<ArrayList<QuestionItem>,Boolean,Exception>())
    var questionIndex = mutableStateOf(1)
    var questionSize = 100
    init {
        getData()
    }
    private fun getData(){
        viewModelScope.launch {
            data.value = questionRepository.getDataOrException()
        }
    }
    fun getQuestion():QuestionItem?{
        var question = data.value.data?.toMutableList()?.get(questionIndex.value - 1)
        return question
    }
    fun nextQuestion(){
        viewModelScope.launch(Dispatchers.Default) {
            delay(1000)
            if(questionIndex.value == (questionSize -1)){
                questionIndex.value = 1
            }
            else{
                questionIndex.value++
            }

            println("Question Index: ${questionIndex.value}")
        }

    }
}