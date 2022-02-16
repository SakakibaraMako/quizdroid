package edu.uw.ischool.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    var topics : Array<Topic>? = null

    private lateinit var view : RecyclerView
    lateinit var btnPreference : Button
    lateinit var app : QuizApp

    private val invalidJsonFound = "Invalid JSON File or No JSON Found! " +
            "Please check updates and restart!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        app = this.application as QuizApp
        app.mainActivity = this

        topics = app.getRepository().getTopics()

        view = findViewById(R.id.view)
        btnPreference = findViewById(R.id.btnPreference)

        btnPreference.setOnClickListener {
            startActivity(Intent(this, PreferenceActivity::class.java))
        }

        if (topics != null) {
            val layoutManager = LinearLayoutManager(this)
            view.layoutManager = layoutManager

            val topicAdapter = TopicAdapter(this, topics!!, app)
            view.adapter = topicAdapter
        } else {
            Toast.makeText(this, invalidJsonFound, Toast.LENGTH_LONG).show()
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

    override fun onDestroy() {
        super.onDestroy()
        app.unregisterReceivers()
        app.cancelAlarm()
    }
}