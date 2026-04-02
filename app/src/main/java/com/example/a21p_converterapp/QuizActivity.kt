package com.example.a21p_converterapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    // UI Elements
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvWelcomeBack: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var rgOptions: RadioGroup
    private lateinit var rbOption1: RadioButton
    private lateinit var rbOption2: RadioButton
    private lateinit var rbOption3: RadioButton
    private lateinit var rbOption4: RadioButton
    private lateinit var llFeedback: LinearLayout
    private lateinit var tvFeedbackTitle: TextView
    private lateinit var tvExplanation: TextView
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button

    // Quiz State Variables
    private var questionList: List<Question> = listOf()
    private var currentQuestionIndex = 0
    private var score = 0
    private var isAnswered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // 1. Find UI components in the XML layout
        initializeViews()

        // 2. Get Data from Intent (Player Name&Question list)
        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Player"
        tvWelcomeBack.text = "Hello $playerName!"

        // Receive the 5 random questions from MainActivity
        val receivedQuestions = intent.getSerializableExtra("QUESTIONS") as? ArrayList<Question>
        if (receivedQuestions != null) {
            questionList = receivedQuestions
        } else {
            // Fallback: Get new random questions if sth fail
            questionList = QuestionBank.getRandomQuestions()
        }

        // 3. Show the first question
        showQuestion()

        // 4. Handle SBClick
        btnSubmit.setOnClickListener {
            handleSubmission()
        }

        // 5. Handle Next/Finish BClick
        btnNext.setOnClickListener {
            moveToNextQuestion()
        }
    }

    /**
     */
    private fun initializeViews() {
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        tvWelcomeBack = findViewById(R.id.tvWelcomeBack)
        tvQuestion = findViewById(R.id.tvQuestion)
        rgOptions = findViewById(R.id.rgOptions)
        rbOption1 = findViewById(R.id.rbOption1)
        rbOption2 = findViewById(R.id.rbOption2)
        rbOption3 = findViewById(R.id.rbOption3)
        rbOption4 = findViewById(R.id.rbOption4)
        llFeedback = findViewById(R.id.llFeedback)
        tvFeedbackTitle = findViewById(R.id.tvFeedbackTitle)
        tvExplanation = findViewById(R.id.tvExplanation)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)
    }

    /**
     */
    private fun showQuestion() {
        val currentQuestion = questionList[currentQuestionIndex]

        // Reset state for new question
        isAnswered = false
        rgOptions.clearCheck()
        enableOptions(true)
        resetOptionColors()
        
        // Hide feedback and buttons
        llFeedback.visibility = View.GONE
        btnSubmit.visibility = View.VISIBLE
        btnNext.visibility = View.GONE

        // Update progress text & bar
        val progressText = "${currentQuestionIndex + 1}/${questionList.size}"
        tvProgress.text = progressText
        progressBar.progress = currentQuestionIndex + 1

        // Set question&option texts
        tvQuestion.text = currentQuestion.questionText
        rbOption1.text = currentQuestion.options[0]
        rbOption2.text = currentQuestion.options[1]
        rbOption3.text = currentQuestion.options[2]
        rbOption4.text = currentQuestion.options[3]

        // Next button text for the last question
        if (currentQuestionIndex == questionList.size - 1) {
            btnNext.text = "Finish"
        } else {
            btnNext.text = "Next"
        }
    }

    /**
     * Logic when the user clicks 'Submit'
     */
    private fun handleSubmission() {
        val selectedId = rgOptions.checkedRadioButtonId

        // Verify user's answer selection
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer first!", Toast.LENGTH_SHORT).show()
            return
        }

        isAnswered = true
        val currentQuestion = questionList[currentQuestionIndex]
        
        // Identify ưhich radio button was chosen?
        val selectedRadioButton = findViewById<RadioButton>(selectedId)
        val selectedIndex = rgOptions.indexOfChild(selectedRadioButton)

        // Lock the answers
        enableOptions(false)

        // Checking
        if (selectedIndex == currentQuestion.correctAnswerIndex) {
            // CORRECT flow
            score++
            selectedRadioButton.setTextColor(Color.GREEN)
            tvFeedbackTitle.text = "Correct!"
            tvFeedbackTitle.setTextColor(Color.GREEN)
            tvExplanation.visibility = View.GONE // Requirements say don't show explanation if correct
        } else {
            // WRONG flow
            selectedRadioButton.setTextColor(Color.RED)
            
            // Highlight the correct one in green
            val correctRadioButton = rgOptions.getChildAt(currentQuestion.correctAnswerIndex) as RadioButton
            correctRadioButton.setTextColor(Color.GREEN)

            tvFeedbackTitle.text = "Incorrect!"
            tvFeedbackTitle.setTextColor(Color.RED)
            tvExplanation.text = currentQuestion.explanation
            tvExplanation.visibility = View.VISIBLE
        }

        // Show feedback
        llFeedback.visibility = View.VISIBLE
        
        // Switch buttons
        btnSubmit.visibility = View.GONE
        btnNext.visibility = View.VISIBLE
    }

    /**
     * Logic when the user clicks 'Next' or 'Finish'.
     */
    private fun moveToNextQuestion() {
        if (currentQuestionIndex < questionList.size - 1) {
            currentQuestionIndex++
            showQuestion()
        } else {
            // End of Quiz—Usually go to a screen with the results in Phase 3
            Toast.makeText(this, "Quiz Finished! Score: $score/${questionList.size}", Toast.LENGTH_LONG).show()
            // finish() //
        }
    }

    /**
     * turn all RadioButtons on or off
     */
    private fun enableOptions(enabled: Boolean) {
        for (i in 0 until rgOptions.childCount) {
            rgOptions.getChildAt(i).isEnabled = enabled
        }
    }

    /**
     * reset RadioButtons text colors to black
     */
    private fun resetOptionColors() {
        for (i in 0 until rgOptions.childCount) {
            (rgOptions.getChildAt(i) as RadioButton).setTextColor(Color.BLACK)
        }
    }
}
