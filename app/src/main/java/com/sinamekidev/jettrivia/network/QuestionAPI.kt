package com.sinamekidev.jettrivia.network

import com.sinamekidev.jettrivia.model.Question
import retrofit2.http.GET

interface QuestionAPI {
    @GET("world.json")
    suspend fun getQuestions():Question
}