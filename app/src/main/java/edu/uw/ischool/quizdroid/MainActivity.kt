package edu.uw.ischool.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    var topics : Array<Topic>? = null
//    private val numberOfQuestions = arrayListOf(3, 2, 1)
//    private val descriptionTemplate = "This is a %s quiz. " +
//            "It is intended to test your knowledge related to %s."
//    private val numberOfQuestionTemplate = "There are %d questions."

    lateinit var view : RecyclerView
    lateinit var btnPreference : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = this.application as QuizApp

//        Log.i("MainActivity", app.getRepository().getTopics().toString())

        topics = app.getRepository().getTopics()

        view = findViewById(R.id.view)
        btnPreference = findViewById(R.id.btnPreference)

        btnPreference.setOnClickListener {
            startActivity(Intent(this, PreferenceActivity::class.java))
        }

        if (topics != null) {
            var layoutManager = LinearLayoutManager(this)
            view.layoutManager = layoutManager

            val topicAdapter = TopicAdapter(this, topics!!, app)
            view.adapter = topicAdapter
        }
//
//        val dir = filesDir
//        Log.i("Topic", dir.absolutePath)
//        Log.i("Topic", dir.canonicalPath)
//        Log.i("Topic", dir.path)

//        topicList.setOnItemClickListener { _, _, i, _ ->
//            var intent = Intent(this, TopicOverview::class.java)
////            intent.putExtra("TOPIC", topicsTitle[i])
//            app.topic = topicsTitle[i]
//            startActivity(intent)
//        }

    }
}