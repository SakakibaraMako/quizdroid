package edu.uw.ischool.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class QuestionActivity : AppCompatActivity() {

    var currentQuestion = 0
    val questionNumberTemplate = "Question %d"
//
//    val mathQuestion = arrayOf("1 + 1 = ?", "1 * 1 = ?", "2 + 1 = ?")
//    val mathChoices = arrayOf(arrayOf("1", "2", "3", "4"), arrayOf("1", "2", "3", "4"),
//                            arrayOf("1", "2", "3", "4"))
//    val mathAnswer = arrayOf("2", "1", "3")
//
//    val physicsQuestion = arrayOf("What is the derivative of displacement?",
//                                "What is the derivative of velocity?")
//    val physicsChoices = arrayOf(arrayOf("displacement", "velocity", "speed", "acceleration"),
//                                arrayOf("displacement", "velocity", "speed", "acceleration"))
//    val physicsAnswer = arrayOf("velocity", "acceleration")
//
//    val marvelQuestion = arrayOf("Which of the following is in Marvel Super Heroes?")
//    val marvelChoices = arrayOf(arrayOf("Naruto", "IChiGo", "Micky Mouse", "Captain America"))
//    val marvelAnswer = arrayOf("Captain America")

    lateinit var questionNumber : TextView
    lateinit var questionText : TextView
    lateinit var btnSubmit : Button
    lateinit var radioGroup: RadioGroup

//    lateinit var questions : Array<String>
//    lateinit var choices : Array<Array<String>>
//    lateinit var answers : Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val app = this.application as QuizApp

//        var topic = intent.getStringExtra("TOPIC")
        val topic = app.topic
//        currentQuestion = intent.getIntExtra("CURRENT", 0)
//        val correctTotal = intent.getIntExtra("CORRECT", 0)
        currentQuestion = app.current
        var correctTotal = app.correct

//        when (topic) {
//            "Math" -> {
//                questions = mathQuestion
//                choices = mathChoices
//                answers = mathAnswer
//            }
//            "Physics" -> {
//                questions = physicsQuestion
//                choices = physicsChoices
//                answers = physicsAnswer
//            }
//            "Marvel Super Heroes" -> {
//                questions = marvelQuestion
//                choices = marvelChoices
//                answers = marvelAnswer
//            }
//        }

        questionNumber = findViewById(R.id.questionNumber)
        questionText = findViewById(R.id.question)
        btnSubmit= findViewById(R.id.btnSubmit)
        radioGroup = findViewById(R.id.radio)

        val quiz = app.getQuestions(topic)[currentQuestion - 1]
        val question = quiz.question
        val choices = quiz.choices
        val answer = choices[quiz.answer]

        for (i in 0 until radioGroup.childCount) {
            val choice = radioGroup.getChildAt(i) as RadioButton
            choice.text = choices[i]
        }

        questionNumber.text = String.format(questionNumberTemplate, currentQuestion)
        questionText.text = question


        radioGroup.setOnCheckedChangeListener { _, _ -> btnSubmit.visibility = View.VISIBLE }

        btnSubmit.setOnClickListener {
            val intent = Intent(this, AnswerActivity::class.java)
            val checkedRB = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)

//            intent.putExtra("TOPIC", topic)
            // prevent bug caused by going backward
            app.current = currentQuestion
            app.correct = correctTotal
            intent.putExtra("CORRECT_ANSWER", answer)
            intent.putExtra("YOUR_ANSWER", checkedRB.text)
//            intent.putExtra("CURRENT", currentQuestion)
//            intent.putExtra("CORRECT_TOTAL", correctTotal)
            startActivity(intent)
        }

    }
}