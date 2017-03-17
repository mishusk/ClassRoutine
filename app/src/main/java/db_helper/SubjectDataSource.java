package db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Model_Subject;

/**
 * Created by mishu on 7/28/2016.
 */
public class SubjectDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Model_Subject modelSubject;

    public SubjectDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // insert subject name by using constructor with no id
    public boolean saveSubject(Model_Subject modelSubject) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_SUBJECT, modelSubject.getSubjectName());
        long inserted = database.insert(DatabaseHelper.TABLE_SUBJECT, null, contentValues);
        this.close();
        if (inserted > 0) {
            return true;
        } else {
            return false;
        }
    }


    // find all subject and return an arrayList containing id and subject name;
    public Model_Subject findSubjecById(int id) {
        this.open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_SUBJECT, new String[]
                {DatabaseHelper.COL_ID, DatabaseHelper.COL_SUBJECT}, DatabaseHelper.COL_ID + "=" + id, null, null, null, null);
        cursor.moveToFirst();
        int mID = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
        String mSubjentName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SUBJECT));

        cursor.close();
        this.close();
        modelSubject = new Model_Subject(mID, mSubjentName);
        return modelSubject;
    }

    public ArrayList<Model_Subject> getAllSubject() {
        ArrayList<Model_Subject> modelSubjectArray = new ArrayList<>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " +
                DatabaseHelper.TABLE_SUBJECT + " order by " +
                DatabaseHelper.COL_SUBJECT + " ASC ", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int mSubjectID = cursor.getInt(cursor.getColumnIndex(
                        DatabaseHelper.COL_ID));
                String mSubjectNam = cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.COL_SUBJECT));
                modelSubject = new Model_Subject(mSubjectID, mSubjectNam);
                cursor.moveToNext();
                modelSubjectArray.add(modelSubject);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        this.close();
        return modelSubjectArray;
    }


    //get only a string array for subject to add in class routine
    public ArrayList<String> allSubjectStringArray() {
        ArrayList<String> subjectNameArray = new ArrayList<>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_SUBJECT, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String mSubjectNam = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SUBJECT));
                cursor.moveToNext();
                subjectNameArray.add(mSubjectNam);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        this.close();
        return subjectNameArray;
    }


    public boolean updateSubject(int id, Model_Subject modelSubject) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_SUBJECT,
                modelSubject.getSubjectName());
        int updated = database.update(DatabaseHelper.TABLE_SUBJECT,
                contentValues, DatabaseHelper.COL_ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateSubjectByIdName(int id, String subjectName) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_SUBJECT, subjectName);
        int updated = database.update(DatabaseHelper.TABLE_SUBJECT, contentValues, DatabaseHelper.COL_ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean deleteSubject(int id) {
        this.open();
        int deleted = database.delete(DatabaseHelper.TABLE_SUBJECT,
                DatabaseHelper.COL_ID + " = " + id, null);
        this.close();

        if (deleted > 0) {
            return true;
        } else {
            return false;
        }
    }
}