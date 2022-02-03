package edu.uw.ischool.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private val topics = arrayOf("Math", "Physics", "Marvel Super Heroes")
    private val numberOfQuestions = arrayListOf(3, 2, 1)
    private val descriptionTemplate = "This is a %s quiz. " +
            "It is intended to test your knowledge related to %s."
    private val numberOfQuestionTemplate = "There are %d questions."

    lateinit var topicList : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topicList = findViewById(R.id.listView)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topics)
        topicList.adapter = arrayAdapter

        topicList.setOnItemClickListener { _, _, i, _ ->
            var intent = Intent(this, TopicOverview::class.java)
            intent.putExtra("TOPIC", topics[i])
            intent.putExtra("DESCRIPTION",
                String.format(descriptionTemplate, topics[i], topics[i]))
            intent.putExtra("NUMBER_OF_QUESTIONS",
                String.format(numberOfQuestionTemplate, numberOfQuestions[i]))
            startActivity(intent)
        }

    }
}