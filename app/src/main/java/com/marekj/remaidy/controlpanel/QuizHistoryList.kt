package com.marekj.remaidy.controlpanel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.marekj.remaidy.R
import com.marekj.remaidy.database.HistoryDatabase
import com.marekj.remaidy.patientview.MainMenu
import com.marekj.remaidy.recyclerview.QuizHistoryAdapter
import com.marekj.restaurantreview.recyclerview.QuizHistoryRVModel

class QuizHistoryList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_history_list)

        val imageModelArrayList = populateList()
        val recyclerView =
            findViewById<View>(R.id.my_recycler_view2) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val mAdapter = QuizHistoryAdapter(imageModelArrayList, this)
        recyclerView.adapter = mAdapter
        drawerListener()
    }

    private fun populateList(): ArrayList<QuizHistoryRVModel> {
        val quizHistoryDatabase = HistoryDatabase(this)
        val quizList = quizHistoryDatabase.getQuizzes()

        val list = ArrayList<QuizHistoryRVModel>()

        for (i in 0..<quizList.size) {
            val imageModel = QuizHistoryRVModel()
            imageModel.setTimeDate(quizList[i].id)
            imageModel.setAnswers(quizList[i].totalAnswers)
            imageModel.setCorrectAnswers(quizList[i].totalCorrectAnswers)
            list.add(imageModel)
        }
        return list
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
                val menuIntent = Intent(this, ControlPanel::class.java)
                startActivity(menuIntent)
                finish()
                menuItem.isChecked = false
                false
            }
            if (menuItem.itemId == R.id.patientMode) {
                val menuIntent = Intent(this, MainMenu::class.java)
                startActivity(menuIntent)
                finish()
                menuItem.isChecked = false
                false
            }
            if (menuItem.itemId == R.id.questionsList) {
                val menuIntent = Intent(this, QuestionsList::class.java)
                startActivity(menuIntent)
                menuItem.isChecked = false
                false
            }
            else {
                menuItem.isChecked = false
                false
            }
        }
    }
}