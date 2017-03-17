package db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Model_Routine;

/**
 * Created by mishu on 8/3/2016.
 */
public class RoutineDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Model_Routine modelRoutine;

    public RoutineDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public boolean saveClassToRoutine(Model_Routine modelRoutine) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_SUBJECT_NAME_R, modelRoutine.getRoutinSubjctName());
        contentValues.put(DatabaseHelper.COL_DAY, modelRoutine.getRoutinDay());
        contentValues.put(DatabaseHelper.COL_ROOM, modelRoutine.getRoutineRoom());
        contentValues.put(DatabaseHelper.COL_TIME, modelRoutine.getRoutinTime());
        contentValues.put(DatabaseHelper.COL_DURATION, modelRoutine.getRoutinDuration());

        long inserted = database.insert(DatabaseHelper.TABLE_ROUTINE, null, contentValues);
        this.close();
        return inserted > 0;
    }


    // find all subject and return an arrayList containing id and subject name;
    public ArrayList<Model_Routine> findSubjectByDay(int day) {
        ArrayList<Model_Routine> todayRoutine = new ArrayList<>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_ROUTINE + " where day = " +
                day, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int mID = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                int mDayName = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DAY));
                String mSubName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SUBJECT));
                String mRoom = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ROOM));
                String mStartTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TIME));
                int mDuration = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DURATION));
                modelRoutine = new Model_Routine(mID, mSubName, mDayName, mRoom, mStartTime, mDuration);
                cursor.moveToNext();
                todayRoutine.add(modelRoutine);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        this.close();
        return todayRoutine;
    }


    public ArrayList<Model_Routine> getAllClassRoutine() {
        ArrayList<Model_Routine> modelsArrayRoutine = new ArrayList<>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_ROUTINE, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int mClassID = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID_R));
                String mClassSubjectName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SUBJECT_NAME_R));
                int mClassDayName = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DAY));
                String mClassRoom = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ROOM));
                String mClassTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TIME));
                int mClassHour = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DURATION));

                modelRoutine = new Model_Routine(mClassID, mClassSubjectName, mClassDayName, mClassRoom, mClassTime, mClassHour);
                cursor.moveToNext();
                modelsArrayRoutine.add(modelRoutine);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        this.close();

        return modelsArrayRoutine;
    }

    public boolean updateClassRoutine(int id, Model_Routine modelRoutine) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_SUBJECT_NAME_R, modelRoutine.getRoutinSubjctName());
        contentValues.put(DatabaseHelper.COL_DAY, modelRoutine.getRoutinDay());
        contentValues.put(DatabaseHelper.COL_ROOM, modelRoutine.getRoutineRoom());
        contentValues.put(DatabaseHelper.COL_TIME, modelRoutine.getRoutinTime());
        contentValues.put(DatabaseHelper.COL_DURATION, modelRoutine.getRoutinDuration());

        int updated = database.update(DatabaseHelper.TABLE_ROUTINE, contentValues, DatabaseHelper.COL_ID_R + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else {
            return false;
        }
    }


    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public boolean deleteClassRoutine(int id) {
        this.open();
        int deleted = database.delete(DatabaseHelper.TABLE_ROUTINE, DatabaseHelper.COL_ID_R + " = " + id, null);
        this.close();

        if (deleted > 0) {
            return true;
        } else {
            return false;
        }
    }

}
