package edu.uw.ischool.quizdroid

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import java.net.URI

class QuizApp : Application() {

    private val tag = "QuizApp"
    var topic = ""
    var current = 0
    var correct = 0
    var total = 0

    val URIKey = "application.dataURI"
    val intervalKey = "application.updateInterval"

    var defaultPath = ""
    val defaultInterval = 24 * 60

    override fun onCreate() {
        super.onCreate()
        defaultPath = filesDir.parent + "/data/questions.json"
        Log.i(tag, "QuizApp has been initiated!")
    }

    fun getRepository(): TopicRepository {
        val prefs: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val uri : URI = URI.create(prefs.getString(URIKey, defaultPath))
        return MemoryTopicRepository(uri)
    }
}