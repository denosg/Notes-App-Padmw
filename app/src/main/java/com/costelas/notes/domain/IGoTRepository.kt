package com.costelas.notes.domain

import com.costelas.notes.common.models.GoTQuote

interface IGoTRepository {
    suspend fun fetchRandomQuote(): GoTQuote
}