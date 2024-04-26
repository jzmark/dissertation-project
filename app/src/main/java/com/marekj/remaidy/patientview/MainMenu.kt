package com.marekj.remaidy.patientview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.marekj.remaidy.R
import com.marekj.remaidy.controlpanel.ControlPanel
import com.marekj.remaidy.controlpanel.QuestionsList
import com.marekj.remaidy.database.QuestionDatabase
import com.marekj.remaidy.database.QuizDatabase

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        drawerListener()
        settingsButton()
        quizButton()
    }

    private fun quizButton() {
        val button = findViewById<Button>(R.id.mainMenuButton)
        button.setOnClickListener {
            openQuiz()
        }
    }

    private fun openQuiz() {
        if (QuestionDatabase(this).numberOfQuestions() > 0) {
            QuizDatabase(this).purge()
            QuizDatabase(this).addQuestions(QuestionDatabase(this).getQuestions())
            val quiz = Intent(this, QuizMode::class.java)
            startActivity(quiz)
        }
        else {
            Snackbar.make(
                findViewById(R.id.settingsButton), getString(R.string.no_questions_error),
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun settingsButton() {
        val button = findViewById<Button>(R.id.settingsButton)
        button.setOnClickListener {
            startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS))
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
            menuItem.isChecked = false
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
                false
            }
            if (menuItem.itemId == R.id.questionsDrawer) {
                openQuiz()
                false
            }
            else {
                menuItem.isChecked = false
                false
            }
        }
    }
}