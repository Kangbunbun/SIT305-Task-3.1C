package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvCongrats = findViewById<TextView>(R.id.tvCongrats)
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val tvFinalScore = findViewById<TextView>(R.id.tvFinalScore)
        val tvCorrectCount = findViewById<TextView>(R.id.tvCorrectCount)
        val tvWrongCount = findViewById<TextView>(R.id.tvWrongCount)
        val btnTryAgain = findViewById<Button>(R.id.btnTryAgain)
        val btnFinish = findViewById<Button>(R.id.btnFinish)

        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Player"
        val finalScore = intent.getIntExtra("FINAL_SCORE", 0)
        val wrongCount = 5 - finalScore

        tvUserName.text = playerName
        tvFinalScore.text = "$finalScore/5"
        tvCorrectCount.text = "Correct Answers: $finalScore"
        tvWrongCount.text = "Wrong Answers: $wrongCount"

        tvCongrats.text = when {
            finalScore == 5 -> "Perfect Score! Well done!"
            finalScore >= 3 -> "Good Job!"
            else -> "Better luck next time!"
        }

        btnTryAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("RETAINED_NAME", playerName)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }

        btnFinish.setOnClickListener {
            finishAffinity()
        }
    }
}
