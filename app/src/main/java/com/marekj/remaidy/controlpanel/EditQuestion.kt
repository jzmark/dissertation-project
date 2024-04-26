package com.marekj.remaidy.controlpanel

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.marekj.remaidy.R
import com.marekj.remaidy.database.QuestionDatabase
import com.marekj.remaidy.database.QuestionEntity
import com.marekj.remaidy.patientview.MainMenu
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.UUID


class EditQuestion : AppCompatActivity() {

    private lateinit var bSelectImage : ImageView
    private var imgPath : String? = null
    private var imageUri : Uri? = null

    private var SELECT_PICTURE = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_question)
        drawerListener()
        bSelectImage = findViewById(R.id.imageUpload)
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("id")!!
            editQuestionMode(value)
            val submitButton = findViewById<Button>(R.id.submitButton)
            submitButton.visibility = View.INVISIBLE
            deleteListener(value)
        }
        else {
            submitListener()
            bSelectImage.setOnClickListener {
                pickImage()
            }
            val deleteButton = findViewById<Button>(R.id.deleteButton)
            deleteButton.visibility = View.INVISIBLE
        }
    }

    private fun deleteListener(id : String) {
        val button = findViewById<Button>(R.id.deleteButton)
        button.setOnClickListener {
            val db = QuestionDatabase(this)
            deleteImage(db.getQuestionByID(id)!!.imgPath)
            db.deleteQuestion(id)
            startActivity(Intent(this, QuestionsList::class.java))
            finish()
        }
    }

    private fun editQuestionMode(id : String) {
        Log.w("ID", id)
        val db = QuestionDatabase(this)
        val question = db.getQuestionByID(id)
        findViewById<TextInputEditText>(R.id.descriptionText).setText(question!!.description)
        findViewById<TextInputEditText>(R.id.descriptionText).isEnabled = false
        findViewById<TextInputEditText>(R.id.answer1CorrectTextField).setText(question!!.answer1Correct)
        findViewById<TextInputEditText>(R.id.answer1CorrectTextField).isEnabled = false
        findViewById<TextInputEditText>(R.id.answer2TextField).setText(question!!.answer2)
        findViewById<TextInputEditText>(R.id.answer2TextField).isEnabled = false
        findViewById<TextInputEditText>(R.id.answer3TextField).setText(question!!.answer3)
        findViewById<TextInputEditText>(R.id.answer3TextField).isEnabled = false
        findViewById<TextInputEditText>(R.id.answer4TextField).setText(question!!.answer4)
        findViewById<TextInputEditText>(R.id.answer4TextField).isEnabled = false
        val imageFile = File(filesDir, question!!.imgPath)
        val imgUri = Uri.fromFile(imageFile)
        bSelectImage.setImageURI(imgUri)
    }

    private fun submitListener() {
        val button = findViewById<Button>(R.id.submitButton)
        button.setOnClickListener {
            val db = QuestionDatabase(this)
            val description = findViewById<TextInputEditText>(R.id.descriptionText).text.toString()
            val answer1Correct = findViewById<TextInputEditText>(R.id.answer1CorrectTextField).text.toString()
            val answer2 = findViewById<TextInputEditText>(R.id.answer2TextField).text.toString()
            val answer3 = findViewById<TextInputEditText>(R.id.answer3TextField).text.toString()
            val answer4 = findViewById<TextInputEditText>(R.id.answer4TextField).text.toString()
            if (description == "" || answer1Correct == "" || answer2 == ""
                || answer3 == "" || answer4 == "") {
                Snackbar.make(
                    findViewById(R.id.submitButton), getString(R.string.emptyFields),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
            else if (imageUri == null) {
                Snackbar.make(
                    findViewById(R.id.submitButton), getString(R.string.noPhoto),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
            else if (!areAnswersDistinct(answer1Correct, answer2, answer3, answer4)) {
                Snackbar.make(
                    findViewById(R.id.submitButton), getString(R.string.notDistinctiveAnswers),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
            else {
                saveImage(imageUri)
                db.addQuestion(QuestionEntity("-1", description, answer1Correct, answer2,
                    answer3, answer4, imgPath!!))
                startActivity(Intent(this, QuestionsList::class.java))
                finish()
            }
        }
    }

    private fun areAnswersDistinct(answer1 : String, answer2 : String,
                                   answer3 : String, answer4 : String) : Boolean {
        if (answer1 == answer2) {
            return false
        }
        if (answer1 == answer3) {
            return false
        }
        if (answer1 == answer4) {
            return false
        }
        if (answer2 == answer3) {
            return false
        }
        if (answer2 == answer4) {
            return false
        }
        return answer3 != answer4
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.setType("image/*")
        startActivityForResult(intent, SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            if (data != null && data.data != null) {
                val selectedImageUri = data.data
                bSelectImage.setImageURI(selectedImageUri)
                imageUri = selectedImageUri
                //Log.w("URI", imageUri.toString())
            }
        }
    }

    private fun saveImage(imageUri: Uri?) {
        try {
            val inputStream = contentResolver.openInputStream(imageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            val resizedBitmap = resizeBitmap(bitmap, 600) // Pass the desired max width or height
            imgPath = UUID.randomUUID().toString() + ".jpg"
            val outputFile = File(filesDir, imgPath)
            val outputStream: OutputStream = FileOutputStream(outputFile)
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream) // Adjust compression quality as needed
            outputStream.close()
            bitmap.recycle()
            resizedBitmap.recycle()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun resizeBitmap(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val aspectRatio: Float = width.toFloat() / height.toFloat()
        val newWidth: Int
        val newHeight: Int

        if (width > height) {
            newWidth = maxDimension
            newHeight = (maxDimension / aspectRatio).toInt()
        } else {
            newHeight = maxDimension
            newWidth = (maxDimension * aspectRatio).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun deleteImage(imgPath : String) {
        val file = File(filesDir, imgPath)
        file.delete()
    }


    private fun drawerListener() {
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        val navigationView = findViewById<NavigationView>(R.id.navigation)
        navigationView.setNavigationItemSelectedListener { menuItem ->
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
}