package com.marekj.remaidy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        drawerListener()

    }

    private fun drawerListener() {
        val navigationView = findViewById<NavigationView>(R.id.navigation)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            if(menuItem.itemId == R.id.logoutDrawer) {
                val menuIntent = Intent(this, MainActivity::class.java)
                startActivity(menuIntent)
                true
            } else {
                val menuIntent = Intent(this, MainMenu::class.java)
                startActivity(menuIntent)
                true
            }
        }
    }
}