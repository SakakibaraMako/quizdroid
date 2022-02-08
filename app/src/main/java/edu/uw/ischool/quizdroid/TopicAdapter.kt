package edu.uw.ischool.quizdroid

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TopicAdapter(val activity: MainActivity, var topics: Array<Topic>, var app: QuizApp) :
    RecyclerView.Adapter<RowHolder>() {

    override fun getItemCount(): Int { return topics.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder(activity.layoutInflater.inflate(R.layout.row, parent, false), activity)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bindModel(topics[position].title, topics[position].shortDescription,
            topics[position].icon)
    }

}