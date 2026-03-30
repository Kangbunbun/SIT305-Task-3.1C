package com.example.a21p_converterapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for the Quiz Screen (Placeholder for now)
        setContentView(R.layout.activity_quiz)

        // Receive the player name passed from MainActivity
        // Using getExtra with default value if null
        val playerName = intent.getStringExtra("PLAYER_NAME") ?: ""
        
        // Find the Welcome Back TextView
        val tvWelcomeBack = findViewById<TextView>(R.id.tvWelcomeBack)
        
        // Personalize the welcome message based on name
        if (playerName.isNotEmpty()) {
            tvWelcomeBack.text = "Hello $playerName! Ready for the quiz?"
        } else {
            tvWelcomeBack.text = "Hello! Ready for the quiz?"
        }
        
        /**
         * Receive the 5 random questions selected in MainActivity
         * For now, we only receive and log or verify them.
         * The UI flow to show questions one by one will be in Phase 2.
         */
        val questions = intent.getSerializableExtra("QUESTIONS") as? ArrayList<Question>
        // In Phase 2, we will use this 'questions' list to display content
    }
}
