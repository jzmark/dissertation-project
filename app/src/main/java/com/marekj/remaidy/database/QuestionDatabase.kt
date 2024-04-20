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
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing one', 4, 'Swansea')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing two', 3, 'London')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing three', 2, 'Bristol')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing four', 1, 'Cardiff')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing five', 5, 'Newport')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 2, 'Testing six', 3, 'Port Talbot')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 3, 'Testing seven', 4, 'Brighton')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 4, 'Testing eight', 4, 'Plymouth')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 5, 'Testing nine', 4, 'Edinburgh')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 6, 'Testing one', 4, 'Glasgow')"
//        )
//        db.execSQL(
//            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
//                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 4, 'Testing two', 3, 'Inverness')"
//        )
    }

    fun addQuestion(question: QuestionEntity) {
        val db = this.writableDatabase
        db.execSQL(
            "INSERT INTO $TABLE_NAME($DESCRIPTION, $ANSWER1_CORRECT, $ANSWER2, " +
                    "$ANSWER3, $ANSWER4, $IMG_PATH) " +
                    "VALUES('${question.description}', '${question.answer1Correct}', " +
                    "'${question.answer2}', '${question.answer3}', " +
                    "'${question.answer4}', '${question.imgPath}')"
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

//    fun getReviewsByRestaurantId(restaurantId: String): ArrayList<ReviewEntity> {
//        val db = this.readableDatabase
//
//        val cursorReviews = db.rawQuery(
//            "SELECT * FROM $TABLE_NAME WHERE " +
//                    "$RESTAURANT_ID = $restaurantId", null
//        )
//
//        val reviews = ArrayList<ReviewEntity>()
//        if (cursorReviews.moveToFirst()) {
//            do {
//                reviews.add(
//                    ReviewEntity(
//                        cursorReviews.getString(0), "Username: "
//                                + cursorReviews.getString(1),
//                        cursorReviews.getString(2), cursorReviews.getString(3).toInt(),
//                        cursorReviews.getString(4), cursorReviews.getString(5).toInt(),
//                        "Location: " + cursorReviews.getString(6)
//                    )
//                )
//            } while (cursorReviews.moveToNext())
//
//        }
//        cursorReviews.close()
//        db.close()
//        return reviews
//    }
//
//    fun getReviewsByUID(uid: String): ArrayList<ReviewEntity> {
//        val db = this.readableDatabase
//
//        val cursorReviews = db.rawQuery(
//            "SELECT * FROM $TABLE_NAME WHERE " +
//                    "$UID like '$uid'", null
//        )
//
//        val reviews = ArrayList<ReviewEntity>()
//        if (cursorReviews.moveToFirst()) {
//            do {
//                reviews.add(
//                    ReviewEntity(
//                        cursorReviews.getString(0), "Username: "
//                                + cursorReviews.getString(1),
//                        cursorReviews.getString(2), cursorReviews.getString(3).toInt(),
//                        cursorReviews.getString(4), cursorReviews.getString(5).toInt(),
//                        "Location: " + cursorReviews.getString(6)
//                    )
//                )
//            } while (cursorReviews.moveToNext())
//
//        }
//        cursorReviews.close()
//        db.close()
//        return reviews
//    }
//
//
//    fun removeReview(reviewId: String) {
//        val db = this.writableDatabase
//        Log.w(TAG, "DELETE FROM $TABLE_NAME WHERE $ID_COL = $reviewId")
//        db.execSQL("DELETE FROM $TABLE_NAME WHERE $ID_COL = $reviewId")
//    }
//
//    fun getReviewsByReviewId(id: String): ArrayList<ReviewEntity> {
//        val db = this.readableDatabase
//
//        val cursorReviews = db.rawQuery(
//            "SELECT * FROM $TABLE_NAME WHERE " +
//                    "$ID_COL = $id", null
//        )
//
//        val reviews = ArrayList<ReviewEntity>()
//        if (cursorReviews.moveToFirst()) {
//            do {
//                reviews.add(
//                    ReviewEntity(
//                        cursorReviews.getString(0), "Username: "
//                                + cursorReviews.getString(1),
//                        cursorReviews.getString(2), cursorReviews.getString(3).toInt(),
//                        cursorReviews.getString(4), cursorReviews.getString(5).toInt(),
//                        "Location: " + cursorReviews.getString(6)
//                    )
//                )
//            } while (cursorReviews.moveToNext())
//
//        }
//        cursorReviews.close()
//        db.close()
//        return reviews
//    }
//
//    fun editReview(reviewId: String, review: String, stars: Int, location: String) {
//        val db = this.writableDatabase
//        Log.w(
//            TAG, "UPDATE $TABLE_NAME SET $REVIEW = '$review', $STARS = $stars, "
//                    + "$LOCATION = '$location' WHERE $ID_COL = $reviewId"
//        )
//        db.execSQL(
//            "UPDATE $TABLE_NAME SET $REVIEW = '$review', $STARS = $stars, "
//                    + "$LOCATION = '$location' WHERE $ID_COL = $reviewId"
//        )
//    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}