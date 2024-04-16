package com.marekj.remaidy.controlpanel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.marekj.remaidy.R
import com.marekj.remaidy.patientview.MainMenu


class EditQuestion : AppCompatActivity() {

    // One Button
    lateinit var bSelectImage : ImageView

    // constant to compare
    // the activity result code
    var SELECT_PICTURE = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_question)
        drawerListener()
        bSelectImage = findViewById(R.id.imageUpload)

        // handle the Choose Image button to trigger
        // the image chooser function

        // handle the Choose Image button to trigger
        // the image chooser function
        bSelectImage.setOnClickListener {
            imageChooser()
        }
    }

    // this function is triggered when
    // the Select Image Button is clicked
    fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                val selectedImageUri = data?.data
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    bSelectImage.setImageURI(selectedImageUri)
                }
            }
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
            else {
                menuItem.isChecked = false
                false
            }
        }
    }
}