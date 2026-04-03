package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val btnStart = findViewById<Button>(R.id.btnStart)

        val retainedName = intent.getStringExtra("RETAINED_NAME")
        if (retainedName != null) {
            etName.setText(retainedName)
        }

        btnStart.setOnClickListener {
            val playerName = etName.text.toString().trim()
            if (playerName.isEmpty()) {
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val selectedQuestions = QuestionBank.getRandomQuestions()
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("PLAYER_NAME", playerName)
                putExtra("QUESTIONS", ArrayList(selectedQuestions))
            }
            startActivity(intent)
        }
    }
}
