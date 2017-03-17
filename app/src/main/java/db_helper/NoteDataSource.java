package db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import models.Model_Note;

/**
 * Created by mishu on 3/12/2017.
 */

public class NoteDataSource {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Model_Note model_note;

    public NoteDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public boolean insertNote(Model_Note model_note) {
        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_N_TEXT, model_note.getNote());
        long inserted = database.insert(DatabaseHelper.TABLE_NOTE, null, contentValues);
        this.close();
        if (inserted > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Model_Note> getAllNote() {
        ArrayList<Model_Note> modelNoteArray = new ArrayList<>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " +
                DatabaseHelper.TABLE_NOTE + " order by " +
                DatabaseHelper.COL_N_UID + " ASC ", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int mNoteUID = cursor.getInt(cursor.getColumnIndex(
                        DatabaseHelper.COL_N_UID));
                String mNoteText = cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.COL_N_TEXT));
                model_note = new Model_Note(mNoteUID, mNoteText);
                cursor.moveToFirst();//here is the culprit (move to nex)
                modelNoteArray.add(model_note);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        this.close();
        return modelNoteArray;
    }

    public ArrayList<Model_Note> getAllNotes() {
        ArrayList<Model_Note> modelNoteArray = new ArrayList<>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " +
                DatabaseHelper.TABLE_NOTE + " order by " +
                DatabaseHelper.COL_N_UID + " ASC ", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int mNoteUID = cursor.getInt(cursor.getColumnIndex(
                        DatabaseHelper.COL_N_UID));
                String mNoteText = cursor.getString(cursor.getColumnIndex(
                        DatabaseHelper.COL_N_TEXT));
                model_note = new Model_Note(mNoteUID, mNoteText);
                cursor.moveToNext();
                modelNoteArray.add(model_note);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        this.close();
        return modelNoteArray;
    }

    //get only a string array for subject to add in class routine
    public ArrayList<String> getAllNoteArrayListString() {
        ArrayList<String> noteTextArray = new ArrayList<>();
        this.open();
        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_NOTE, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String mNoteText = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_N_TEXT));
                cursor.moveToNext();
                noteTextArray.add(mNoteText);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        this.close();
        return noteTextArray;
    }


    public boolean deleteNoteByID(int noteUID) {
        this.open();
        int delete = database.delete(DatabaseHelper.TABLE_NOTE,
                DatabaseHelper.COL_N_UID + " = " + noteUID, null);
        this.close();
        if (delete > 0) {
            return true;
        } else {
            return false;
        }
    }
}