package com.marekj.remaidy.patientview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marekj.remaidy.R
import com.marekj.remaidy.database.QuizDatabase

class QuizMode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_question)
    }

    fun initialiseLayout() {
        val question = QuizDatabase(this).getQuestionAndUpdate()

    }
}