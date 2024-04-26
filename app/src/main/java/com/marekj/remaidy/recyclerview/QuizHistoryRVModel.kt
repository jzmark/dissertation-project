package com.marekj.restaurantreview.recyclerview

/*
 * Data model class to store logos and team names from F1
 */
class QuizHistoryRVModel {
    private var modelName: String? = null
    private var modelTimeDate: String? = null
    private var modelAnswers: String? = null
    private var modelCorrectAnswers: String? = null

    fun getNames(): String {
        return modelName!!
    }

    fun setNames(name: String) {
        this.modelName = name
    }

    fun getCorrectAnswers(): String {
        return modelCorrectAnswers!!
    }

    fun setCorrectAnswers(correctAnswerNum : String) {
        this.modelCorrectAnswers = correctAnswerNum
    }

    fun getAnswers(): String {
        return modelAnswers!!
    }

    fun setAnswers(answerNum : String) {
        this.modelAnswers = answerNum
    }

    fun setTimeDate(timeDate: String) {
        this.modelTimeDate = timeDate
    }

    fun getTimeDate(): String {
        return modelTimeDate!!
    }
}