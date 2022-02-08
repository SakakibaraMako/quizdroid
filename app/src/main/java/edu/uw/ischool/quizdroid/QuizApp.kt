package edu.uw.ischool.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application(), TopicRepository {

    private val tag = "QuizApp"
    var topic = ""
    var current = 0
    var correct = 0
    var total = 0

    override fun onCreate() {
        super.onCreate()
        Log.i(tag, "QuizApp has been initiated!")
    }

}