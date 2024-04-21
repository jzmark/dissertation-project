package com.marekj.remaidy.patientview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.marekj.remaidy.R
import com.marekj.remaidy.controlpanel.ControlPanel
import com.marekj.remaidy.database.QuizDatabase
import com.marekj.remaidy.database.QuizQuestionEntity
import java.io.File

class QuizMode : AppCompatActivity() {
    private lateinit var question : QuizQuestionEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_question)
        question = QuizDatabase(this).getQuestionAndUpdate()
        initialiseLayout()
        drawerListener()
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