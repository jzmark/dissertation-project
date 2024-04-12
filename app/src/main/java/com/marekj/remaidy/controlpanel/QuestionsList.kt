package com.marekj.remaidy.controlpanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.marekj.remaidy.patientview.MainMenu
import com.marekj.remaidy.R

class QuestionsList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_list)
        drawerListener()
//        buttonListener()
    }

    private fun buttonListener() {
//        val button = findViewById<Button>(R.id.mainMenuButton)
//        button.setOnClickListener() {
//            startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS))
//        }
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