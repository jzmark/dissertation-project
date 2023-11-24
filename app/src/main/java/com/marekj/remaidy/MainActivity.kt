package com.marekj.remaidy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_menu)

        val userLoginButton = findViewById<Button>(R.id.userLogin)
        userLoginButton.setOnClickListener {
            val menuIntent = Intent(this, MainMenu::class.java)
            startActivity(menuIntent)
        }
        val carerLoginButton = findViewById<Button>(R.id.carerLogin)
        carerLoginButton.setOnClickListener {
            val menuIntent = Intent(this, MainMenu::class.java)
            startActivity(menuIntent)
        }
    }

}