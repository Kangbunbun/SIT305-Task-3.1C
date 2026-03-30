package com.example.a21p_converterapp

import java.io.Serializable
data class Question(
    val id: Int,
    val questionText: String,
    val options: List<String>, //  4 options for each question
    val correctAnswerIndex: Int, // Index of the correct answer (0 to 3)
    val explanation: String // explanation text
) : Serializable
