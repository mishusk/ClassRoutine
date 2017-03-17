package db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Model_Exam;

/**
 * Created by mishu on 3/12/2017.
 */

public class ExamDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Model_Exam modelExam;

    public ExamDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    // insert subject name by using constructor with no id
    public boolean insertExam(Model_Exam modelExam) {
        this.open();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.COL_E_SUBJECT_NAME, modelExam.getSubject_name());
        contentValues.put(DatabaseHelper.COL_E_DATE, modelExam.getDate());
        contentValues.put(DatabaseHelper.COL_E_TIME, modelExam.getTime());
        contentValues.put(DatabaseHelper.COL_E_DURATION, modelExam.getDuration());

        long inserted = database.insert(DatabaseHelper.TABLE_EXAM, null,
                contentValues);
        this.close();
        if (inserted > 0) {
            return true;
        } else {
            return false;
        }
    }


    public ArrayList<Model_Exam> getAllExam() {
        ArrayList<Model_Exam> model_examArrayList = new ArrayList<>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " +
                DatabaseHelper.TABLE_EXAM + " order by " +
                DatabaseHelper.COL_E_UID
                + " DESC ", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int mExamUid = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_E_UID));
                String mSubjectName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_E_SUBJECT_NAME));
                String mDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_E_DATE));
                String mTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_E_TIME));
                String mDuration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_E_DURATION));
                modelExam = new Model_Exam(mExamUid, mSubjectName, mDate, mTime, mDuration);
                cursor.moveToNext();
                model_examArrayList.add(modelExam);
            }
        }
        if (cursor != null)
            cursor.close();
        this.close();
        return model_examArrayList;
    }

    public boolean updateExam(int UID, Model_Exam model_exam) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_E_SUBJECT_NAME, model_exam.getSubject_name());
        contentValues.put(DatabaseHelper.COL_E_DATE, model_exam.getDate());
        contentValues.put(DatabaseHelper.COL_E_TIME, model_exam.getTime());
        contentValues.put(DatabaseHelper.COL_E_DURATION, model_exam.getDuration());
        int updatedRow = database.update(DatabaseHelper.TABLE_EXAM,
                contentValues, DatabaseHelper.COL_E_UID + " = " + UID, null);
        this.close();
        if (updatedRow > 0)
            return true;
        return false;
    }

    public boolean deleteExamByID(int uid) {
        this.open();
        int delete = database.delete(DatabaseHelper.TABLE_EXAM,
                DatabaseHelper.COL_E_UID + " = " + uid, null);
        this.close();
        return delete > 0;
    }

    public void deleteAllExam() {
        this.open();
        String query = "delete from " + DatabaseHelper.TABLE_EXAM;
        database.execSQL(query);
        this.close();
    }
}
