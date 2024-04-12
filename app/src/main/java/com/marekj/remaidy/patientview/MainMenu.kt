package com.marekj.remaidy.patientview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.marekj.remaidy.R
import com.marekj.remaidy.controlpanel.ControlPanel

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        drawerListener()
        buttonListener()
    }

    private fun buttonListener() {
        val button = findViewById<Button>(R.id.mainMenuButton)
        button.setOnClickListener() {
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