package com.example.a21p_converterapp
object QuestionBank {

    fun getQuestions(): List<Question> {
        return listOf(
            Question(1, "What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2, "Paris is the capital of France."),
            Question(2, "Which planet is known as the Red Planet?", listOf("Earth", "Mars", "Jupiter", "Saturn"), 1, "Mars appears red due to iron oxide on its surface."),
            Question(3, "What is the largest mammal on Earth?", listOf("Elephant", "Blue Whale", "Giraffe", "Shark"), 1, "The Blue Whale is the largest animal to ever exist."),
            Question(4, "Who wrote 'Romeo and Juliet'?", listOf("Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"), 1, "William Shakespeare wrote this famous tragedy."),
            Question(5, "What is the chemical symbol for water?", listOf("H2O", "CO2", "O2", "NaCl"), 0, "H2O consists of two Hydrogen atoms and one Oxygen atom."),
            Question(6, "Which country is home to the Kangaroo?", listOf("India", "USA", "Australia", "Brazil"), 2, "Kangaroos are indigenous to Australia."),
            Question(7, "What is the fastest land animal?", listOf("Lion", "Cheetah", "Horse", "Tiger"), 1, "The Cheetah can reach speeds of over 100km/h."),
            Question(8, "How many continents are there on Earth?", listOf("5", "6", "7", "8"), 2, "There are 7 continents: Asia, Africa, NA, SA, Antarctica, Europe, Australia."),
            Question(9, "What is the smallest prime number?", listOf("0", "1", "2", "3"), 2, "2 is the smallest and only even prime number."),
            Question(10, "In which year did World War II end?", listOf("1943", "1944", "1945", "1946"), 2, "World War II ended in 1945 with the surrender of Japan and Germany.")
        )
    }
    fun getRandomQuestions(): List<Question> {
        return getQuestions().shuffled().take(5)
    }
}
