package com.marekj.remaidy.patientview

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.marekj.remaidy.R
import com.marekj.remaidy.controlpanel.ControlPanel
import com.marekj.remaidy.database.HistoryDatabase
import com.marekj.remaidy.database.QuizDatabase
import com.marekj.remaidy.database.QuizQuestionEntity
import com.marekj.remaidy.database.QuizSummaryEntity
import java.io.File

class QuizMode : AppCompatActivity() {
    private lateinit var question : QuizQuestionEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_question)
        question = QuizDatabase(this).getQuestionAndUpdate()
        question.isAnsweredCorrectly = "true"
        initialiseLayout()
        drawerListener()
        answer1Listener()
        answer2Listener()
        answer3Listener()
        answer4Listener()
        nextButtonListener()
    }

    private fun nextButtonListener() {
        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {
            val db = QuizDatabase(this)
            if (!db.finishQuestion(question)) {
                val nextQuestion = Intent(this, QuizMode::class.java)
                startActivity(nextQuestion)
                finish()
            }
            else {
                val dbQuiz = QuizDatabase(this)
                val quizSummary = dbQuiz.finishQuizStats()
                val dbHistory = HistoryDatabase(this)
                dbHistory.addQuiz(QuizSummaryEntity("-1", quizSummary[0].toString(),
                    quizSummary[1].toString()))
                val finish = Intent(this, MainMenu::class.java)
                startActivity(finish)
                finish()
            }
        }
    }

    private fun initialiseLayout() {
        val imageFile = File(filesDir, question.imgPath)
        val imageUri = Uri.fromFile(imageFile)
        findViewById<ImageView>(R.id.imageDisplay).setImageURI(imageUri)
        findViewById<TextView>(R.id.questionDescription).text = question.description
        findViewById<Button>(R.id.ans1).text = question.answer1
        findViewById<Button>(R.id.ans2).text = question.answer2
        findViewById<Button>(R.id.ans3).text = question.answer3
        findViewById<Button>(R.id.ans4).text = question.answer4
        findViewById<Button>(R.id.nextButton).isEnabled = false
    }

    private fun buttonsChangeState() {
        findViewById<Button>(R.id.ans1).isEnabled = false
        findViewById<Button>(R.id.ans2).isEnabled = false
        findViewById<Button>(R.id.ans3).isEnabled = false
        findViewById<Button>(R.id.ans4).isEnabled = false
        findViewById<Button>(R.id.nextButton).isEnabled = true
    }

    private fun highlightCorrect() {
        val correctId = "ans" + question.correctAnswerID
        val btn = findViewById<Button>(resources
            .getIdentifier(correctId, "id", packageName))
        btn.setBackgroundColor(Color.GREEN)
        btn.setTextColor(Color.BLACK)
        question.isAnsweredCorrectly = "false"
    }

    private fun answer1Listener() {
        val button = findViewById<Button>(R.id.ans1)
        button.setOnClickListener {
            if (question.correctAnswerID == "1") {
                button.setBackgroundColor(Color.GREEN)
            }
            else {
                button.setBackgroundColor(Color.RED)
                highlightCorrect()
            }
            buttonsChangeState()
        }
    }

    private fun answer2Listener() {
        val button = findViewById<Button>(R.id.ans2)
        button.setOnClickListener {
            if (question.correctAnswerID == "2") {
                button.setBackgroundColor(Color.GREEN)
            }
            else {
                button.setBackgroundColor(Color.RED)
                highlightCorrect()
            }
            buttonsChangeState()
        }
    }

    private fun answer3Listener() {
        val button = findViewById<Button>(R.id.ans3)
        button.setOnClickListener {
            if (question.correctAnswerID == "3") {
                button.setBackgroundColor(Color.GREEN)
            }
            else {
                button.setBackgroundColor(Color.RED)
                highlightCorrect()
            }
            buttonsChangeState()
        }
    }

    private fun answer4Listener() {
        val button = findViewById<Button>(R.id.ans4)
        button.setOnClickListener {
            if (question.correctAnswerID == "4") {
                button.setBackgroundColor(Color.GREEN)
            }
            else {
                button.setBackgroundColor(Color.RED)
                highlightCorrect()
            }
            buttonsChangeState()
        }
    }

    private fun drawerListener() {
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        val navigationView = findViewById<NavigationView>(R.id.navigation)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            drawerLayout.close()
            if (menuItem.itemId == R.id.mainMenu) {
                val menuIntent = Intent(this, MainMenu::class.java)
                startActivity(menuIntent)
                finish()
                false
            }
            if (menuItem.itemId == R.id.controlPanel) {
                val menuIntent = Intent(this, ControlPanel::class.java)
                startActivity(menuIntent)
                finish()
                false
            }
            else {
                menuItem.isChecked = false
                false
            }
        }
    }
}