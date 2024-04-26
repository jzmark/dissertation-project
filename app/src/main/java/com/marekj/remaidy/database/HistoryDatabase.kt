package com.marekj.remaidy.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDatabase(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "userHistory"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "history"
        private const val ID_COL = "id"
        private const val TIME = "time"
        private const val TOTAL_ANSWERS = "totalAnswers"
        private const val TOTAL_CORRECT_ANSWERS = "totalCorrectAnswers"
    }



    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TIME + " DATE DEFAULT (datetime('now','localtime')),"
                    + TOTAL_ANSWERS + " INTEGER,"
                    + TOTAL_CORRECT_ANSWERS + " INTEGER)"
        )
    }

    fun addQuiz(q: QuizSummaryEntity) {
        val db = this.writableDatabase
        db.execSQL(
            "INSERT INTO $TABLE_NAME($TOTAL_ANSWERS, $TOTAL_CORRECT_ANSWERS) " +
                    "VALUES('${q.totalAnswers}', " +
                    "'${q.totalCorrectAnswers}')"
        )
        db.close()
    }

    fun getQuizzes(): ArrayList<QuizSummaryEntity> {
        val db = this.readableDatabase

        val cursorQuizList = db.rawQuery(
            "SELECT * FROM $TABLE_NAME", null
        )

        val questions = ArrayList<QuizSummaryEntity>()
        if (cursorQuizList.moveToFirst()) {
            do {
                questions.add(
                    QuizSummaryEntity(cursorQuizList.getString(1),
                        "Total answers: " + cursorQuizList.getString(2),
                        "Correct answers: " + cursorQuizList.getString(3))
                )
            } while (cursorQuizList.moveToNext())

        }
        cursorQuizList.close()
        db.close()
        return questions
    }


//    fun getQuestionByID(id: String): QuestionEntity? {
//        val db = this.readableDatabase
//
//        val cursorReviews = db.rawQuery(
//            "SELECT * FROM $TABLE_NAME WHERE " +
//                    "$ID_COL like '$id'", null
//        )
//
//        if (cursorReviews.moveToFirst()) {
//            return QuestionEntity(
//                cursorReviews.getString(0), cursorReviews.getString(1),
//                cursorReviews.getString(2), cursorReviews.getString(3),
//                cursorReviews.getString(4), cursorReviews.getString(5),
//                cursorReviews.getString(6)
//            )
//        }
//        cursorReviews.close()
//        db.close()
//        return null
//    }
//
//    fun deleteQuestion(id: String) {
//        val db = this.writableDatabase
//        db.execSQL("DELETE FROM $TABLE_NAME WHERE $ID_COL = $id")
//    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}