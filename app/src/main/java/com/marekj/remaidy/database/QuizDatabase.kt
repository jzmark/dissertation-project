package com.marekj.remaidy.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuizDatabase(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "quiz"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "questionlist"
        private const val ID_COL = "id"
        private const val DESCRIPTION = "description"
        private const val ANSWER1 = "answer1"
        private const val ANSWER2 = "answer2"
        private const val ANSWER3 = "answer3"
        private const val ANSWER4 = "answer4"
        private const val IMG_PATH = "imgpath"
        private const val CORRECT_ANSWER_ID = "correctAnswerId"
        private const val IS_ANSWERED_CORRECTLY = "isCorrectlyAnswered"
        private const val WAS_ANSWERED = "wasAnswered"
    }



    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DESCRIPTION + " TEXT,"
                    + ANSWER1 + " TEXT,"
                    + ANSWER2 + " TEXT,"
                    + ANSWER3 + " TEXT,"
                    + ANSWER4 + " TEXT,"
                    + IMG_PATH + " TEXT,"
                    + CORRECT_ANSWER_ID + " INTEGER,"
                    + IS_ANSWERED_CORRECTLY + " TEXT,"
                    + WAS_ANSWERED + " TEXT)"
        )
    }

    fun addQuestions(questions: ArrayList<QuestionEntity>) {
        val db = this.writableDatabase
        questions.shuffle()
        for (q in questions) {
            val answers : ArrayList<String> = ArrayList()
            answers.add(q.answer1Correct)
            answers.add(q.answer2)
            answers.add(q.answer3)
            answers.add(q.answer4)
            answers.shuffle()
            val correctAnswerString = q.answer1Correct
            var correctId = 0
            for (i in 0 until answers.size) {
                if (answers[i] == correctAnswerString) {
                    correctId = i + 1
                }
            }
            db.execSQL(
                "INSERT INTO $TABLE_NAME($DESCRIPTION, $ANSWER1, $ANSWER2, " +
                        "$ANSWER3, $ANSWER4, $IMG_PATH, $CORRECT_ANSWER_ID, " +
                        "$IS_ANSWERED_CORRECTLY,$WAS_ANSWERED) " +
                        "VALUES('${q.description.replace("'","''")}', " +
                        "'${answers[0].replace("'","''")}', " +
                        "'${answers[1].replace("'","''")}', " +
                        "'${answers[2].replace("'","''")}', " +
                        "'${answers[3].replace("'","''")}', '${q.imgPath}'," +
                        "'${correctId}','null','false')"
            )
        }
        db.close()
    }

    fun purge() {
        val db = this.readableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getQuestionAndUpdate(): QuizQuestionEntity {
        val db = this.readableDatabase

        val cursorReviews = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $WAS_ANSWERED = 'false' " +
                    "ORDER BY $ID_COL ASC LIMIT 1", null
        )
        var question : QuizQuestionEntity? = null
        if (cursorReviews.moveToFirst()) {
            question = QuizQuestionEntity(
                cursorReviews.getString(0), cursorReviews.getString(1), cursorReviews.getString(2),
                cursorReviews.getString(3), cursorReviews.getString(4),
                cursorReviews.getString(5), cursorReviews.getString(6), cursorReviews.getString(7),
                cursorReviews.getString(8)
            )

        }
        cursorReviews.close()
        db.close()
        return question!!
    }

    fun finishQuestion(question : QuizQuestionEntity) : Boolean {
        val db = this.readableDatabase
        db.execSQL("UPDATE $TABLE_NAME SET $IS_ANSWERED_CORRECTLY = '${question!!.isAnsweredCorrectly}' " +
                "WHERE $ID_COL = ${question!!.id}")
        db.execSQL("UPDATE $TABLE_NAME SET $WAS_ANSWERED = 'true' WHERE $ID_COL = ${question!!.id}")
        db.close()

        // if number of unanswered questions is equal to 0 it will return true and trigger finishing
        // the quiz in QuizMode nextButtonListener() method
        return numberOfUnansweredQuestions() == 0
    }

    fun finishQuizStats(): QuizSummaryEntity {
        return QuizSummaryEntity(
            "null", numberOfQuestions().toString(),
            numberOfCorrectQuestions().toString()
        )
    }

    private fun numberOfUnansweredQuestions() : Int {
        val db = this.readableDatabase
        val cursorNumberOfUnansweredQuestions = db.rawQuery(
            "SELECT COUNT(*) FROM $TABLE_NAME WHERE $WAS_ANSWERED = 'false'", null
        )
        var numberOfUnansweredQuestions = 0
        if (cursorNumberOfUnansweredQuestions.moveToFirst()) {
            numberOfUnansweredQuestions = cursorNumberOfUnansweredQuestions.getString(0).toInt()
        }
        db.close()
        return numberOfUnansweredQuestions
    }

    private fun numberOfQuestions() : Int {
        val db = this.readableDatabase
        val cursorNumberOfUnansweredQuestions = db.rawQuery(
            "SELECT COUNT(*) FROM $TABLE_NAME", null
        )
        var numberOfQuestions = 0
        if (cursorNumberOfUnansweredQuestions.moveToFirst()) {
            numberOfQuestions = cursorNumberOfUnansweredQuestions.getString(0).toInt()
        }
        db.close()
        return numberOfQuestions
    }

    private fun numberOfCorrectQuestions() : Int {
        val db = this.readableDatabase
        val cursorNumberOfUnansweredQuestions = db.rawQuery(
            "SELECT COUNT(*) FROM $TABLE_NAME WHERE $IS_ANSWERED_CORRECTLY = 'true'", null
        )
        var numOfCorrectQuestions = 0
        if (cursorNumberOfUnansweredQuestions.moveToFirst()) {
            numOfCorrectQuestions = cursorNumberOfUnansweredQuestions.getString(0).toInt()
        }
        db.close()
        return numOfCorrectQuestions
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}