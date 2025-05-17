package com.costelas.notes.domain

import com.costelas.notes.common.models.GoTQuote
import retrofit2.http.GET

interface IGoTApiService {
    @GET("random")
    suspend fun getRandomQuote(): GoTQuote
}