package com.marekj.remaidy.patientview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marekj.remaidy.R
import com.marekj.remaidy.database.HistoryDatabase
import com.marekj.remaidy.database.QuizDatabase

class EndQuiz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_quiz)
        val db = QuizDatabase(this)
        val finishQuizStats = db.finishQuizStats()
    }
}