package db_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mishu on 7/28/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "class_routine";
    private static final int DATABASE_VERSION = 1;

    // TABLE SETUP FOR NOTES
    static final String TABLE_NOTE = "tbl_note";
    static final String COL_N_UID = "id";
    static final String COL_N_TEXT = "text";
    private static final String CREATE_TABLE_NOTE = "create table " + TABLE_NOTE
            + " ( "
            + COL_N_UID + " integer primary key, "
            + COL_N_TEXT + " text )";

    // TABLE SETUP FOR EXAM SCHEDULE
    static final String TABLE_EXAM = "tbl_exam";
    static final String COL_E_UID = "id";
    static final String COL_E_SUBJECT_NAME = "subject_name";
    static final String COL_E_DATE = "date";
    static final String COL_E_TIME = "time";
    static final String COL_E_DURATION = "duration";
    static final String COL_E_ALARM = "alarm";
    private static final String CREATE_TABLE_EXAM = "create table " + TABLE_EXAM
            + " ( "
            + COL_E_UID + " integer primary key, "
            + COL_E_SUBJECT_NAME + " text, "
            + COL_E_DATE + " text, "
            + COL_E_TIME + " text, "
            + COL_E_DURATION + " text default 0 )";


    // TABLE SETUP FOR ADD_SUBJECT
    static final String TABLE_SUBJECT = "tbl_subject";
    static final String COL_ID = "id";
    static final String COL_SUBJECT = "subject_name";
    private static final String CREATE_TABLE_SUBJEC_NAME = "create table " + TABLE_SUBJECT
            + " ( "
            + COL_ID + " integer primary key, "
            + COL_SUBJECT + " text DEFAULT \"Science\" )";


    //TABLE SETUP FOR MAIN_ROUTINE
    static final String TABLE_ROUTINE = "tbl_class";
    static final String COL_ID_R = "id";
    static final String COL_SUBJECT_NAME_R = "subject_name";
    static final String COL_DAY = "day";
    static final String COL_TIME = "time";
    static final String COL_ROOM = "room";
    static final String COL_DURATION = "duration";
    static final String COL_ALARM_BEFORE_TIME = "alarm";

    private static final String CREATE_TABLE_ROUTINE = "create table " + TABLE_ROUTINE
            + " ( "
            + COL_ID_R + "  integer primary key, "
            + COL_SUBJECT_NAME_R + " text, "
            + COL_DAY + " integer, "
            + COL_TIME + " text, "
            + COL_ROOM + " text DEFAULT \"room 0\", "
            + COL_DURATION + " integer DEFAULT \"60\" )";
/*
    static final String CREATE_TABLE_ROUTINE_WITH_ALARM = "create table " + TABLE_ROUTINE + " ( " +
            COL_ID_R + " integer primary key, " + COL_SUBJECT_NAME_R + " text, " +
            COL_DAY + " text, " + COL_TIME + " text, " + COL_ROOM + " text DEFAULT \"room 0\", " +
            COL_DURATION + " integer DEFAULT \"60\", " + COL_ALARM_BEFORE_TIME + " integer DEFAULT \"0\" )";*/


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SUBJEC_NAME);
        db.execSQL(CREATE_TABLE_ROUTINE);
        db.execSQL(CREATE_TABLE_EXAM);
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_SUBJECT);
        db.execSQL("drop table if exists " + TABLE_ROUTINE);
        db.execSQL("drop table if exists " + CREATE_TABLE_EXAM);
        db.execSQL("drop table if exists " + CREATE_TABLE_NOTE);
        onCreate(db);
    }
}
