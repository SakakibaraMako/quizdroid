package edu.uw.ischool.quizdroid

data class Topic(var title: String, var shortDescription: String, var LongDescription: String,
            var questions: Array<Quiz>, var icon: Int) {

}