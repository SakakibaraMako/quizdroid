package edu.uw.ischool.quizdroid

interface TopicRepository {
    fun getTopicsTitle(): Array<Topic> {
//        val shortDescriptionTemplate = "This is a %s quiz."
//        val longDescriptionTemplate = shortDescriptionTemplate +
//                " It is intended to test your knowledge related to %s."

//        val mathTitle = "Math"
//        val mathShortDescription = String.format(shortDescriptionTemplate, mathTitle)
//        val mathLongDescription = String.format(longDescriptionTemplate, mathTitle, mathTitle)
//        var mathTopic = Topic(mathTitle, mathShortDescription, mathLongDescription,
//            getQuestions(mathTitle))
//
//        val physicsTitle = "Physics"
//        val physicsShortDescription = String.format(shortDescriptionTemplate, physicsTitle)
//        val physicsLongDescription = String.format(longDescriptionTemplate, physicsTitle,
//            physicsTitle)
//        val physicsTopic = Topic(physicsTitle, physicsShortDescription, physicsLongDescription,
//            getQuestions(physicsTitle))
//
//        val marvelTitle = "Marvel Super Heroes"
//        val marvelShortDescription = String.format(shortDescriptionTemplate, marvelTitle)
//        val marvelLongDescription = String.format(longDescriptionTemplate, marvelTitle, marvelTitle)
//        val marvelTopic = Topic(marvelTitle, marvelShortDescription, marvelLongDescription,
//            getQuestions(marvelTitle))

        val mathTopic = getTopic("Math")
        val physicsTopic = getTopic("Physics")
        val marvelTopic = getTopic("Marvel Super Heroes")

        val topics = arrayOf(mathTopic, physicsTopic, marvelTopic)
        return topics
    }

    fun getTopic(topic: String): Topic {
        val shortDescription = "This is a $topic quiz."
        val longDescription = shortDescription +
                " It is intended to test your knowledge related to $topic."
        val iconID = edu.uw.ischool.quizdroid.R.drawable.ic_launcher_foreground
        return Topic(topic, shortDescription, longDescription, getQuestions(topic), iconID)
    }

    fun getQuestions(topic: String): Array<Quiz> {
        var questions : ArrayList<Quiz> = ArrayList()


        val mathQuestion = arrayOf("1 + 1 = ?", "1 * 1 = ?", "2 + 1 = ?")
        val mathChoices = arrayOf(arrayOf("1", "2", "3", "4"), arrayOf("1", "2", "3", "4"),
            arrayOf("1", "2", "3", "4"))
        val mathAnswer = arrayOf(1, 0, 2)

        val physicsQuestion = arrayOf("What is the derivative of displacement?",
            "What is the derivative of velocity?")
        val physicsChoices = arrayOf(arrayOf("displacement", "velocity", "speed", "acceleration"),
            arrayOf("displacement", "velocity", "speed", "acceleration"))
        val physicsAnswer = arrayOf(1, 3)

        val marvelQuestion = arrayOf("Which of the following is in Marvel Super Heroes?")
        val marvelChoices = arrayOf(arrayOf("Naruto", "IChiGo", "Micky Mouse", "Captain America"))
        val marvelAnswer = arrayOf(3)

        when(topic) {
            "Math" -> {
                for (i in mathQuestion.indices) {
                    questions.add(Quiz(mathQuestion[i], mathChoices[i], mathAnswer[i]))
                }
            }
            "Physics" -> {
                for (i in physicsQuestion.indices) {
                    questions.add(Quiz(physicsQuestion[i], physicsChoices[i], physicsAnswer[i]))
                }
            }
            "Marvel Super Heroes" -> {
                for (i in marvelQuestion.indices) {
                    questions.add(Quiz(marvelQuestion[i], marvelChoices[i], marvelAnswer[i]))
                }
            }
        }
        return questions.toTypedArray()
    }
}