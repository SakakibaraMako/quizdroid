package edu.uw.ischool.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {

    lateinit var correctAnswerLabel : TextView
    lateinit var yourAnswerLabel : TextView
    lateinit var correctTotalLabel : TextView
    lateinit var btnNext : Button
    lateinit var btnFinish : Button

    val correctAnswerTemplate = "The Correct Answer is: %s"
    val yourAnswerTemplate = "Your Answer is: %s"
    val correctTotalTemplate = "You have %d out of %d correct"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val app = this.application as QuizApp

        correctAnswerLabel = findViewById(R.id.correctAnswer)
        yourAnswerLabel = findViewById(R.id.yourAnswer)
        correctTotalLabel = findViewById(R.id.correctTotal)
        btnNext = findViewById(R.id.btnNext)
        btnFinish = findViewById(R.id.btnFinish)

//        val topic = app.topic
        val total = app.total
        val correctAnswer = intent.getStringExtra("CORRECT_ANSWER")
        val yourAnswer = intent.getStringExtra("YOUR_ANSWER")
//        var correctTotal = intent.getIntExtra("CORRECT_TOTAL", 0)
//        val current = intent.getIntExtra("CURRENT", 0)
        var correctTotal = app.correct
        var current = app.current


        if (correctAnswer == yourAnswer) correctTotal += 1

        correctAnswerLabel.text = String.format(correctAnswerTemplate, correctAnswer)
        yourAnswerLabel.text = String.format(yourAnswerTemplate, yourAnswer)
        correctTotalLabel.text = String.format(correctTotalTemplate, correctTotal, total)

        // finish
        if (current == total) {
            btnFinish.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            btnFinish.visibility = View.VISIBLE
        } else { // next
            btnNext.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java)
//                intent.putExtra("TOPIC", topic)
                app.current += 1
                app.correct = correctTotal
//                intent.putExtra("CURRENT", current + 1)
//                intent.putExtra("CORRECT", correctTotal)
                startActivity(intent)
                finish()
            }
            btnNext.visibility = View.VISIBLE
        }
    }
}