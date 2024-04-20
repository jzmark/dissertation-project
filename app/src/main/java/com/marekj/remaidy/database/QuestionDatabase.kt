package com.marekj.remaidy.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuestionDatabase(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "questions"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "questionlist"
        private const val ID_COL = "id"
        private const val DESCRIPTION = "description"
        private const val ANSWER1_CORRECT = "answer1correct"
        private const val ANSWER2 = "answer2"
        private const val ANSWER3 = "answer3"
        private const val ANSWER4 = "answer4"
        private const val IMG_PATH = "imgpath"
    }



    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DESCRIPTION + " TEXT,"
                    + ANSWER1_CORRECT + " TEXT,"
                    + ANSWER2 + " TEXT,"
                    + ANSWER3 + " TEXT,"
                    + ANSWER4 + " TEXT,"
                    + IMG_PATH + " TEXT)"
        )
    }

    fun addQuestion(question: QuestionEntity) {
        val db = this.writableDatabase
        db.execSQL(
            "INSERT INTO $TABLE_NAME($DESCRIPTION, $ANSWER1_CORRECT, $ANSWER2, " +
                    "$ANSWER3, $ANSWER4, $IMG_PATH) " +
                    "VALUES('${question.description.replace("'","''")}', " +
                    "'${question.answer1Correct.replace("'","''")}', " +
                    "'${question.answer2.replace("'","''")}', " +
                    "'${question.answer3.replace("'","''")}', " +
                    "'${question.answer4.replace("'","''")}', '${question.imgPath}')"
        )
    }

    fun getQuestions(): ArrayList<QuestionEntity> {
        val db = this.readableDatabase

        val cursorReviews = db.rawQuery(
            "SELECT * FROM $TABLE_NAME", null
        )

        val questions = ArrayList<QuestionEntity>()
        if (cursorReviews.moveToFirst()) {
            do {
                questions.add(
                    QuestionEntity(
                        cursorReviews.getString(0), cursorReviews.getString(1), cursorReviews.getString(2),
                        cursorReviews.getString(3), cursorReviews.getString(4),
                        cursorReviews.getString(5), cursorReviews.getString(6)
                    )
                )
            } while (cursorReviews.moveToNext())

        }
        cursorReviews.close()
        db.close()
        return questions
    }

    fun getQuestionByID(id: String): QuestionEntity? {
        val db = this.readableDatabase

        val cursorReviews = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE " +
                    "$ID_COL like '$id'", null
        )

        if (cursorReviews.moveToFirst()) {
            return QuestionEntity(
                cursorReviews.getString(0), cursorReviews.getString(1),
                cursorReviews.getString(2), cursorReviews.getString(3),
                cursorReviews.getString(4), cursorReviews.getString(5),
                cursorReviews.getString(6)
            )
        }
        cursorReviews.close()
        db.close()
        return null
    }

    fun deleteQuestion(id: String) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME WHERE $ID_COL = $id")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}