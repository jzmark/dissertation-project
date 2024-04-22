package com.marekj.remaidy.patientview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
        findViewById<TextView>(R.id.completedQuestions).text = "Number of questions answered: " +
                finishQuizStats.totalAnswers
        findViewById<TextView>(R.id.correctlyAnsweredQuestions).text = "Number of correctly " +
                "answered questions: " + finishQuizStats.totalCorrectAnswers
        findViewById<Button>(R.id.mainMenuButton).setOnClickListener {
            val finish = Intent(this, MainMenu::class.java)
            startActivity(finish)
            finish()
        }
    }
}