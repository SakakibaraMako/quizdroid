package edu.uw.ischool.quizdroid

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RowHolder(row : View, activity: Activity) : RecyclerView.ViewHolder(row) {

    private var title : TextView
    private var description : TextView
    private var img : ImageView
    private var row : View
    private var activity : Activity

    init {
        title = row.findViewById(R.id.title)
        description = row.findViewById(R.id.shortDescription)
        img = row.findViewById(R.id.img)
        this.row = row
        this.activity = activity
    }

    fun bindModel(title: String, description: String, icon: Int) {
        this.title.text = title
        this.description.text = description
        img.setImageResource(icon)
        var app = activity.application as QuizApp
        this.row.setOnClickListener {
            val intent = Intent(activity, TopicOverview::class.java)
            app.topic = title
            app.total = app.getRepository().getQuestions(title)!!.size
            activity.startActivity(intent)
        }
    }

}