package edu.uw.ischool.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TopicOverview : AppCompatActivity() {

    lateinit var topicLabel : TextView
    lateinit var descriptionLabel : TextView
    lateinit var numberLabel : TextView
    lateinit var btnBegin : Button
    private val oneQuestionTemplate = "There is %d question."
    private val numberOfQuestionTemplate = "There are %d questions."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val app = this.application as QuizApp

//        var topic = this.intent.getStringExtra("TOPIC")
        val topic = app.topic
        val numberOfQuestion = app.total

        topicLabel = findViewById(R.id.topic)
        descriptionLabel = findViewById(R.id.description)
        numberLabel = findViewById(R.id.numberOfQuestions)
        btnBegin = findViewById(R.id.btnBegin)


        topicLabel.text = app.getRepository().getTopic(topic)!!.title
        descriptionLabel.text = app.getRepository().getTopic(topic)!!.LongDescription
        if (numberOfQuestion > 1)
            numberLabel.text = String.format(numberOfQuestionTemplate, numberOfQuestion)
        else
            numberLabel.text = String.format(oneQuestionTemplate, numberOfQuestion)
        btnBegin.setOnClickListener {
            var intent = Intent(this, QuestionActivity::class.java)
//            intent.putExtra("TOPIC", topic)
//            intent.putExtra("CORRECT", 0)
//            intent.putExtra("CURRENT", 1)
            app.current = 1
            app.correct = 0
            startActivity(intent)
            finish()
        }
    }
}