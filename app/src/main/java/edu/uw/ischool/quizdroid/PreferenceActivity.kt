package edu.uw.ischool.quizdroid

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PreferenceActivity : AppCompatActivity() {

    lateinit var uriTextView: TextView
    lateinit var updateTextView: TextView
    lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val app = application as QuizApp
//        Log.i("Preference", URI.create(filesDir.parent).toString())
        val prefs: SharedPreferences = getSharedPreferences(app.PREFERENCE_NAME, MODE_PRIVATE)
        val url = prefs.getString(app.URI_KEY, app.defaultURL)
        val updateInterval = prefs.getInt(app.INTERVAL_KEY, app.defaultInterval)

        uriTextView = findViewById(R.id.URLTextEdit)
        updateTextView = findViewById(R.id.updateText)
        btnUpdate = findViewById(R.id.btnUpdate)

        uriTextView.text = url
        updateTextView.text = updateInterval.toString()
        btnUpdate.setOnClickListener {
            val editor = prefs.edit()
            editor.putString(app.URI_KEY, uriTextView.text.toString())
            editor.putInt(app.INTERVAL_KEY, updateTextView.text.toString().toInt())
            editor.commit()
        }
    }
}