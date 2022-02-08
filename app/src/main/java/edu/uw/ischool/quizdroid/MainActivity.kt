package edu.uw.ischool.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    lateinit var topics : Array<Topic>
//    private val numberOfQuestions = arrayListOf(3, 2, 1)
//    private val descriptionTemplate = "This is a %s quiz. " +
//            "It is intended to test your knowledge related to %s."
//    private val numberOfQuestionTemplate = "There are %d questions."

    lateinit var view : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = this.application as QuizApp

        topics = app.getTopicsTitle()

        view = findViewById(R.id.view)

        var layoutManager = LinearLayoutManager(this)
        view.layoutManager = layoutManager

        val topicAdapter = TopicAdapter(this, topics, app)
        view.adapter = topicAdapter



//        topicList.setOnItemClickListener { _, _, i, _ ->
//            var intent = Intent(this, TopicOverview::class.java)
////            intent.putExtra("TOPIC", topicsTitle[i])
//            app.topic = topicsTitle[i]
//            startActivity(intent)
//        }

    }
}