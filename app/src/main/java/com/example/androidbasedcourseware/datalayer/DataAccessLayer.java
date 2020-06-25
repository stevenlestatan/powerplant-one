package com.example.androidbasedcourseware.datalayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataAccessLayer extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBStatistics.db";
    public static final String COLUMN_INSTRUCTOR_ID = "instructor_id";
    public static final String COLUMN_INSTRUCTOR_NAME = "instructor_name";
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_STUDENT_NAME = "student_name";
    public static final String COLUMN_STATISTICS_STUDENT_ID = "student_id";
    public static final String COLUMN_STATISTICS_DISCUSSION_ID = "discussion_id";
    public static final String COLUMN_STATISTICS_ITEM_PASSED = "item_passed";
    public static final String COLUMN_STATISTICS_ITEM_COUNT = "item_count";
    public static final String COLUMN_STATISTICS_IS_PASSED = "is_passed";
    public static final String COLUMN_QUIZ_SET_ITEM_QUESTIONS = "item_questions";
    public static final String COLUMN_QUIZ_SET_CORRECT_ANSWER = "correct_answer";

    public static final String DEFAULT_INSTRUCTOR_ID = "00001-23450";
    public static final String DEFAULT_INSTRUCTOR_NAME = "Test Instructor";
    private HashMap hp;

    private SQLiteDatabase db;

    public DataAccessLayer(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;

        String instructor = "CREATE TABLE IF NOT EXISTS instructor" +
                "(id integer primary key, instructor_id text unique, instructor_name text)";
        String students = "CREATE TABLE IF NOT EXISTS students" +
                "(id integer primary key, student_id text unique, student_name text)";
        String bellBearingQuizItems = "CREATE TABLE IF NOT EXISTS ball_bearing_quiz_set" +
                "(id integer primary key, item_no integer, item_questions text)";
        String bellBearingItemCorrectAnswer = "CREATE TABLE IF NOT EXISTS ball_bearing_quiz_answer" +
                "(id integer primary key, item_no integer, correct_answer text)";
        String turboQuizItems = "CREATE TABLE IF NOT EXISTS turbo_quiz_set" +
                "(id integer primary key, item_no integer, item_questions text)";
        String turboItemCorrectAnswer = "CREATE TABLE IF NOT EXISTS turbo_quiz_answer" +
                "(id integer primary key, item_no integer, correct_answer text)";
        String discussions = "CREATE TABLE IF NOT EXISTS discussions" +
                "(id integer primary key, discussion_name text)";
        String statistics = "CREATE TABLE IF NOT EXISTS statistics" +
                "(id integer primary key, student_id text, discussion_id integer, item_passed integer, item_count integer, is_passed integer)";
        String bellBearingQuizResult = "CREATE TABLE IF NOT EXISTS ball_bearing_quiz_result" +
                "(id integer primary key, student_id text, item_no integer, student_answer text)";
        String turboQuizResult = "CREATE TABLE IF NOT EXISTS turbo_quiz_result" +
                "(id integer primary key, student_id text, item_no integer, student_answer text)";

        db.execSQL(instructor);
        db.execSQL(students);
        db.execSQL(bellBearingQuizItems);
        db.execSQL(bellBearingItemCorrectAnswer);
        db.execSQL(turboQuizItems);
        db.execSQL(turboItemCorrectAnswer);
        db.execSQL(discussions);
        db.execSQL(statistics);
        db.execSQL(bellBearingQuizResult);
        db.execSQL(turboQuizResult);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db = sqLiteDatabase;

        String statistics = "DROP TABLE IF EXISTS statistics";
        String turboQuizResult = "DROP TABLE IF EXISTS turbo_quiz_result";
        String bellBearingQuizResult = "DROP TABLE IF EXISTS ball_bearing_quiz_result";
        String discussions = "DROP TABLE IF EXISTS discussions";
        String turboQuizAnswer = "DROP TABLE IF EXISTS turbo_quiz_answer";
        String bellBearingQuizAnswer = "DROP TABLE IF EXISTS ball_bearing_quiz_answer";
        String turboSet = "DROP TABLE IF EXISTS turbo_quiz_set";
        String bellBearingSet = "DROP TABLE IF EXISTS ball_bearing_quiz_set";
        String students = "DROP TABLE IF EXISTS students";
        String instructor = "DROP TABLE IF EXISTS instructor";

        db.execSQL(statistics);
        db.execSQL(turboQuizResult);
        db.execSQL(bellBearingQuizResult);
        db.execSQL(discussions);
        db.execSQL(turboQuizAnswer);
        db.execSQL(bellBearingQuizAnswer);
        db.execSQL(turboSet);
        db.execSQL(bellBearingSet);
        db.execSQL(students);
        db.execSQL(instructor);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
        super.onDowngrade(this.db, oldVersion, newVersion);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase _db;

        if (db != null) {
            _db = db;
            return _db;
        }

        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        final SQLiteDatabase _db;

        if (db != null) {
            _db = db;
            return _db;
        }

        return super.getReadableDatabase();
    }

    private void insertDefaultInstructor() {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM instructor WHERE instructor_id = '00001-23450'";
        Cursor cursor = db.rawQuery(fetch, null);

        if (cursor.getCount() == 0) {
            SQLiteDatabase wb = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("instructor_id", "00001-23450");
            contentValues.put("instructor_name", "Test Instructor");
            wb.insert("instructor", null, contentValues);
        }
    }

    public boolean validateIfInstructorExists(String instructorId) {
        boolean result = true;
        Cursor cursor = getInstructorById(instructorId);

        if (cursor.getCount() <= 0) {
            SQLiteDatabase wb = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("instructor_id", DEFAULT_INSTRUCTOR_ID);
            contentValues.put("instructor_name", DEFAULT_INSTRUCTOR_NAME);
            wb.insert("instructor", null, contentValues);

            cursor = getInstructorById(instructorId);
            if (cursor.getCount() > 0) {
                result = false;
            }
        }

        return result;
    }

    public Cursor getTurboAllCorrectAnswers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM turbo_quiz_answer";
        Cursor c = db.rawQuery(fetch, null);
        return c;
    }

    public Cursor getTurboCorrectAnswerByItemNo(int itemNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM turbo_quiz_answer WHERE item_no = '" + itemNo + "'";
        Cursor c = db.rawQuery(fetch, null);
        return c;
    }

    public Cursor getTurboQuizSetByItemNo(int itemNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM turbo_quiz_set WHERE item_no = '" + itemNo + "'";
        Cursor c = db.rawQuery(fetch, null);
        return c;
    }

    public boolean insertTurboQuizSet() {
        SQLiteDatabase wb = this.getWritableDatabase();
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Compresses fuel and air");
        map.put(2, "It is designed to allow the pilot to select any desired power output at any time. This includes boosting at sea level and altitude compensation");
        map.put(3, "Mechanical Aspiration is the process of mechanically increasing the manifold pressure of an engine in order to _____ and ___________ horsepower");
        map.put(4, "This is a safety valve used in some Continental to relieve MAP at a present b maximum pressure by venting deck pressure");
        map.put(5, "It does not boost above 30InHg and uses to exhaust to drive the compressor");

        map.forEach((i, s) -> {
            String fetch = "SELECT * FROM turbo_quiz_set WHERE item_questions = '" + s + "'";
            Cursor c = db.rawQuery(fetch, null);

            if (c.getCount() <= 0) {
                contentValues.put("item_no", i);
                contentValues.put("item_questions", s);
                wb.insert("turbo_quiz_set", null, contentValues);
            }
        });

        Cursor c = getTurboAllCorrectAnswers();
        if (c.getCount() <= 0) {
            ContentValues _contentV = new ContentValues();
            Map<Integer, String> ans = new HashMap<>();
            ans.put(1, "A");
            ans.put(2, "B");
            ans.put(3, "A");
            ans.put(4, "B");
            ans.put(5, "B");

            ans.forEach((i,s) -> {
                _contentV.put("item_no", i);
                _contentV.put("correct_answer", s);
                wb.insert("turbo_quiz_answer", null, _contentV);
            });
        }

        return true;
    }

    public boolean insertTurboQuizResult(ItemsCaterer itemsCaterer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("student_id", itemsCaterer.getStudentId());
        contentValues.put("item_no", itemsCaterer.getItemNo());
        contentValues.put("student_answer", itemsCaterer.getStudentAnswer());
        db.insert("turbo_quiz_result", null, contentValues);
        return true;
    }

    public boolean insertBeallBearingQuizSet() {
        SQLiteDatabase wb = this.getWritableDatabase();
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "It holds all of the engine parts in alignment and is made of aluminum alloy");
        map.put(2, "It connects the piston to the end of the connecting rod and made of hardened steel");
        map.put(3, "The link between the crankshaft and the piston");
        map.put(4, "It rotates within the crankcase and is supported by main bearing journals");
        map.put(5, "It consist of bearing journals and lobes spaced along the shaft");

        map.forEach((i, s) -> {
            String fetch = "SELECT * FROM ball_bearing_quiz_set WHERE item_questions = '" + s + "'";
            Cursor c = db.rawQuery(fetch, null);

            if (c.getCount() <= 0) {
                contentValues.put("item_no", i);
                contentValues.put("item_questions", s);
                wb.insert("ball_bearing_quiz_set", null, contentValues);
            }
        });

        Cursor c = getBellBearingAllCorrectAnswers();
        if (c.getCount() <= 0) {
            ContentValues _contentV = new ContentValues();
            Map<Integer, String> ans = new HashMap<>();
            ans.put(1, "A");
            ans.put(2, "A");
            ans.put(3, "D");
            ans.put(4, "A");
            ans.put(5, "B");

            ans.forEach((i,s) -> {
                _contentV.put("item_no", i);
                _contentV.put("correct_answer", s);
                wb.insert("ball_bearing_quiz_answer", null, _contentV);
            });
        }

        return true;
    }

    public boolean insertBallBearingQuizResult(ItemsCaterer itemsCaterer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("student_id", itemsCaterer.getStudentId());
        contentValues.put("item_no", itemsCaterer.getItemNo());
        contentValues.put("student_answer", itemsCaterer.getStudentAnswer());
        db.insert("ball_bearing_quiz_result", null, contentValues);
        return true;
    }

    public Cursor getBellBearingAllCorrectAnswers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM ball_bearing_quiz_answer";
        Cursor c = db.rawQuery(fetch, null);
        return c;
    }

    public Cursor getBallBearingQuizSetByItemNo(int itemNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM ball_bearing_quiz_set WHERE item_no = '" + itemNo + "'";
        Cursor c = db.rawQuery(fetch, null);
        return c;
    }

    public Cursor getBallBearingCorrectAnswerByItemNo(int itemNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM ball_bearing_quiz_answer WHERE item_no = '" + itemNo + "'";
        Cursor c = db.rawQuery(fetch, null);
        return c;
    }

    public boolean insertToStatistics(String studentId, String discussionName, int itemPassed, int itemCount) {
        SQLiteDatabase wb = this.getWritableDatabase();
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cV = new ContentValues();

        String fetch = "SELECT * FROM discussions";
        Cursor c = db.rawQuery(fetch, null);
        if (c.getCount() <= 0) {
            SQLiteDatabase wr = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            Set<String> set = new HashSet<>();
            set.add("Turbocharger");
            set.add("Ball Bearing");

            for (String s : set) {
                contentValues.put("discussion_name", s);
                wr.insert("discussions", null, contentValues);
            }
        }

        Cursor d = getDiscussionIdByName(discussionName);
        d.moveToFirst();
        int discussionId = d.getInt(d.getColumnIndex("id"));

        Cursor s = getStatisticsByStudentId(studentId, discussionId);
        if (s.getCount() > 0) {
            wb.delete("statistics", "student_id = '" + studentId + "'", null);
        }

        int isPassed = 0;
        if (itemPassed > 3) {
            isPassed = 1;
        }

        cV.put("student_id", studentId);
        cV.put("discussion_id", discussionId);
        cV.put("item_passed", itemPassed);
        cV.put("item_count", itemCount);
        cV.put("is_passed", isPassed);
        wb.insert("statistics", null, cV);

        return isPassed == 0 ? false : true; // if else condition short
    }

    public boolean insertStudent(String studentId, String studentName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("student_id", studentId);
        contentValues.put("student_name", studentName);
        db.insert("students", null, contentValues);
        return true;
    }

    public Cursor getStudentById(String studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM students WHERE student_id = '" + studentId + "'";
        Cursor res = db.rawQuery(fetch, null);
        return res;
    }

    public Cursor getInstructorById(String instructorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM instructor WHERE '" + DEFAULT_INSTRUCTOR_ID + "' = '" + instructorId + "'";
        Cursor res = db.rawQuery(fetch, null);
        return res;
    }

    public Cursor getStatisticsByStudentId(String studentId, int discussionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "SELECT DISTINCT * FROM statistics WHERE student_id = '" + studentId + "' AND discussion_id = " + discussionId + "";
        Cursor res = db.rawQuery(fetch, null);
        return res;
    }

    public Cursor getDiscussionIdByName(String discussionName) {
        SQLiteDatabase db = getReadableDatabase();
        String fetch = "SELECT id FROM discussions WHERE discussion_name = '" + discussionName + "'";
        Cursor c = db.rawQuery(fetch, null);
        return c;
    }

    public Cursor getStatistics() {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "SELECT * FROM statistics";
        Cursor res = db.rawQuery(fetch, null);
        return res;
    }

    public int getCountTurbochargerQuizFailed() {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM statistics WHERE discussion_id = (SELECT id FROM discussions WHERE discussion_name = 'Turbocharger') AND is_passed = 0";
        Cursor c = db.rawQuery(fetch, null);

        return c.getCount();
    }

    public int getCountTurbochargerQuizPassed() {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM statistics WHERE discussion_id = (SELECT id FROM discussions WHERE discussion_name = 'Turbocharger') AND is_passed = 1";
        Cursor c = db.rawQuery(fetch, null);

        return c.getCount();
    }

    public int getCountBallBearingQuizFailed() {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM statistics WHERE discussion_id = (SELECT id FROM discussions WHERE discussion_name = 'Ball Bearing') AND is_passed = 0";
        Cursor c = db.rawQuery(fetch, null);

        return c.getCount();
    }

    public int getCountBallBearingQuizPassed() {
        SQLiteDatabase db = this.getReadableDatabase();
        String fetch = "SELECT DISTINCT * FROM statistics WHERE discussion_id = (SELECT id FROM discussions WHERE discussion_name = 'Ball Bearing') AND is_passed = 1";
        Cursor c = db.rawQuery(fetch, null);

        return c.getCount();
    }
}