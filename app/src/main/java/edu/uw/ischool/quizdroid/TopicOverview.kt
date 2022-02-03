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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        var intent = this.intent
        var topic = intent.getStringExtra("TOPIC")
        var description = intent.getStringExtra("DESCRIPTION")
        var numberOfQuestion = intent.getStringExtra("NUMBER_OF_QUESTIONS")

        topicLabel = findViewById(R.id.topic)
        descriptionLabel = findViewById(R.id.description)
        numberLabel = findViewById(R.id.numberOfQuestions)
        btnBegin = findViewById(R.id.btnBegin)


        topicLabel.text = topic
        descriptionLabel.text = description
        numberLabel.text = numberOfQuestion

        btnBegin.setOnClickListener {
            var intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("TOPIC", topic)
            intent.putExtra("CORRECT", 0)
            intent.putExtra("CURRENT", 1)
            startActivity(intent)
            finish()
        }
    }
}