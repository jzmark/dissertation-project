package com.marekj.remaidy.controlpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.marekj.remaidy.patientview.MainMenu
import com.marekj.remaidy.R
import com.marekj.remaidy.database.QuestionDatabase
import com.marekj.remaidy.recyclerview.QuestionListAdapter
import com.marekj.restaurantreview.recyclerview.QuestionListRVModel

class QuestionsList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_list)
        drawerListener()
        floatingButtonListener()

        val imageModelArrayList = populateList()
        val recyclerView =
            findViewById<View>(R.id.my_recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val mAdapter = QuestionListAdapter(imageModelArrayList, this)
        recyclerView.adapter = mAdapter
    }

    private fun floatingButtonListener() {
        val button = findViewById<FloatingActionButton>(R.id.floating_action_button)
        button.setOnClickListener() {
            val menuIntent = Intent(this, EditQuestion::class.java)
            startActivity(menuIntent)
            finish()
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
            if (menuItem.itemId == R.id.historyDrawer) {
                val menuIntent = Intent(this, QuizHistoryList::class.java)
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
    private fun populateList(): ArrayList<QuestionListRVModel> {
        val questionDatabase = QuestionDatabase(this)
        val questionList = questionDatabase.getQuestions()

        val list = ArrayList<QuestionListRVModel>()

        for (i in 0..<questionList.size) {
            val imageModel = QuestionListRVModel()
            imageModel.setNames(questionList[i].description)
            imageModel.setImages(questionList[i].imgPath)
            imageModel.setDescription(questionList[i].description)
            imageModel.setId(questionList[i].id)
            list.add(imageModel)
        }
        return list
    }
}