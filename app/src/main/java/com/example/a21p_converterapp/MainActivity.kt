package com.example.a21p_converterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //SET the layout for Welcome Screen
        setContentView(R.layout.activity_main)

        // POINT to EditText and Button in the layout
        val etName = findViewById<EditText>(R.id.etName)
        val btnStart = findViewById<Button>(R.id.btnStart)

        // Start button logic
        btnStart.setOnClickListener {
            // GET player name
            val playerName = etName.text.toString()
            
            // Randomly select 5 questions
            val selectedQuestions = QuestionBank.getRandomQuestions()

            // Prepare intent to move to QuizActivity
            val intent = Intent(this, QuizActivity::class.java)
            
            // Pass the player name & the list of selected questions to the Quiz screen
            // USE putExtra to pass data between Activities
            intent.putExtra("PLAYER_NAME", playerName)
            // USE ArrayList implements Serializable
            intent.putExtra("QUESTIONS", ArrayList(selectedQuestions))
            
            // Start the next Activity (Quiz Screen)
            startActivity(intent)
        }
    }
}
