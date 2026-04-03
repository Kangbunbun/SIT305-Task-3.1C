package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    private lateinit var rgOptions: RadioGroup
    private lateinit var llFeedback: View
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button

    private var questionList = listOf<Question>()
    private var currentIndex = 0
    private var score = 0
    private var playerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        playerName = intent.getStringExtra("PLAYER_NAME") ?: "Player"
        val received = intent.getSerializableExtra("QUESTIONS") as? ArrayList<Question>
        questionList = received ?: QuestionBank.getRandomQuestions()

        initViews()
        showQuestion()

        btnSubmit.setOnClickListener { handleSubmission() }
        btnNext.setOnClickListener { moveToNext() }
    }

    private fun initViews() {
        rgOptions = findViewById(R.id.rgOptions)
        llFeedback = findViewById(R.id.llFeedback)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)
        findViewById<TextView>(R.id.tvWelcomeBack).text = "Hello $playerName!"
    }

    private fun showQuestion() {
        val q = questionList[currentIndex]
        rgOptions.clearCheck()
        setOptionsEnabled(true)
        updateColors(Color.BLACK)
        llFeedback.visibility = View.GONE
        btnSubmit.visibility = View.VISIBLE
        btnNext.visibility = View.GONE

        findViewById<TextView>(R.id.tvProgress).text = "${currentIndex + 1}/5"
        findViewById<ProgressBar>(R.id.progressBar).progress = currentIndex + 1
        findViewById<TextView>(R.id.tvQuestion).text = q.questionText

        for (i in 0 until 4) {
            (rgOptions.getChildAt(i) as RadioButton).text = q.options[i]
        }
        btnNext.text = if (currentIndex == 4) "Finish" else "Next"
    }

    private fun handleSubmission() {
        val selectedId = rgOptions.checkedRadioButtonId
        if (selectedId == -1) return Toast.makeText(this, "Select one!", Toast.LENGTH_SHORT).show()

        val q = questionList[currentIndex]
        val selectedRB = findViewById<RadioButton>(selectedId)
        val selectedIdx = rgOptions.indexOfChild(selectedRB)

        setOptionsEnabled(false)

        if (selectedIdx == q.correctAnswerIndex) {
            score++
            selectedRB.setTextColor(Color.GREEN)
            showFeedback("Correct!", Color.GREEN, false)
        } else {
            selectedRB.setTextColor(Color.RED)
            (rgOptions.getChildAt(q.correctAnswerIndex) as RadioButton).setTextColor(Color.GREEN)
            showFeedback("Incorrect!", Color.RED, true)
        }
        btnSubmit.visibility = View.GONE
        btnNext.visibility = View.VISIBLE
    }

    private fun showFeedback(title: String, color: Int, showExpl: Boolean) {
        llFeedback.visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvFeedbackTitle).apply {
            text = title
            setTextColor(color)
        }
        findViewById<TextView>(R.id.tvExplanation).apply {
            text = questionList[currentIndex].explanation
            visibility = if (showExpl) View.VISIBLE else View.GONE
        }
    }

    private fun moveToNext() {
        if (currentIndex < 4) {
            currentIndex++
            showQuestion()
        } else {
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("PLAYER_NAME", playerName)
                putExtra("FINAL_SCORE", score)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun setOptionsEnabled(enabled: Boolean) {
        for (i in 0 until rgOptions.childCount) rgOptions.getChildAt(i).isEnabled = enabled
    }

    private fun updateColors(color: Int) {
        for (i in 0 until rgOptions.childCount) (rgOptions.getChildAt(i) as RadioButton).setTextColor(color)
    }
}
