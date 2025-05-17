package com.costelas.notes.common.models

data class GoTQuote(
    val sentence: String,
    val character: GoTCharacter
)

data class GoTCharacter(
    val name: String,
    val slug: String,
    val house: GoTHouse
)

data class GoTHouse(
    val name: String,
    val slug: String
)