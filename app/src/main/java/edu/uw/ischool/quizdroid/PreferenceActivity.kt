package edu.uw.ischool.quizdroid

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.net.URI

class PreferenceActivity : AppCompatActivity() {

    lateinit var uriTextView: TextView
    lateinit var updateTextView: TextView
    lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val app = application as QuizApp
//        Log.i("Preference", URI.create(filesDir.parent).toString())
        val prefs: SharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val uri : URI = URI.create(prefs.getString(app.URIKey, app.defaultPath))
        val updateInterval = prefs.getInt(app.intervalKey, app.defaultInterval)

        uriTextView = findViewById(R.id.URLTextEdit)
        updateTextView = findViewById(R.id.updateText)
        btnUpdate = findViewById(R.id.btnUpdate)

        uriTextView.text = uri.toString()
        updateTextView.text = updateInterval.toString()
        btnUpdate.setOnClickListener {
            val editor = prefs.edit()
            editor.putString(app.URIKey, uriTextView.text.toString())
            editor.putInt(app.intervalKey, updateTextView.text.toString().toInt())
            editor.commit()
        }
    }
}