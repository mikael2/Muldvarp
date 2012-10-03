/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is responsible for creating, maintaining and upgrading the SQLITE database.
 * Right now the database creation consists more or less hard-coded as SQL-statements, 
 * perhaps dynamic database creation should be considered.
 * 
 * @author johan
 */
public class MuldvarpSQLDatabaseHelper extends SQLiteOpenHelper {

    //General
    private static final String DATABASE_NAME = "muldvarp.db";
    private static final int DATABASE_VERSION = 1;
    //Fields
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_REVISION = "revision";
    public static final String COLUMN_UPDATED = "updated";    
    //Tables
    //Programmes
    public static final String TABLE_PROGRAMME = "programme";    
    //Courses
    public static final String TABLE_COURSE = "course";    
    //Topics
    public static final String TABLE_TOPIC = "topic";    
    //Videos
    public static final String TABLE_VIDEO = "video";    
    //Documents
    public static final String TABLE_DOCUMENT = "document";    
    //Quizzes
    public static final String TABLE_QUIZ = "quiz";    
    //Relations
    public static final String TABLE_PROGRAMME_HAS_COURSE = "programme_has_course";
    public static final String TABLE_COURSE_HAS_TOPIC = "course_has_task";
    public static final String TABLE_PROGRAMME_HAS_QUIZ = "programme_has_quiz";    
    public static final String TABLE_PROGRAMME_HAS_VIDEO = "programme_has_video";    
    public static final String TABLE_COURSE_HAS_VIDEO = "course_has_video";    
    public static final String TABLE_PROGRAMME_HAS_DOCUMENT = "programme_has_document";    
    public static final String TABLE_COURSE_HAS_DOCUMENT = "course_has_document";

    // Database creation sql statement
    //ID, Name, Revision Updated
    private static final String CREATE_TABLE_PROGRAMME = "create table "
        + TABLE_PROGRAMME + "(" + COLUMN_ID
        + " integer primary key autoincrement, " 
        + COLUMN_NAME + " text not null, "
        + COLUMN_REVISION + " integer not null, " 
        + COLUMN_UPDATED + " text not null);";
    private static final String CREATE_TABLE_COURSE = "create table "
        + TABLE_COURSE + "(" + COLUMN_ID
        + " integer primary key autoincrement, " 
        + COLUMN_NAME + " text not null, "
        + COLUMN_REVISION + " integer not null, " 
        + COLUMN_UPDATED + " text not null);";
    private static final String CREATE_TABLE_TOPIC = "create table "
        + TABLE_TOPIC + "(" + COLUMN_ID
        + " integer primary key autoincrement, " 
        + COLUMN_NAME + " text not null, "
        + COLUMN_REVISION + " integer not null, " 
        + COLUMN_UPDATED + " text not null);";
    private static final String CREATE_TABLE_VIDEO = "create table "
        + TABLE_VIDEO + "(" + COLUMN_ID
        + " integer primary key autoincrement, " 
        + COLUMN_NAME + " text not null, "
        + COLUMN_REVISION + " integer not null, "
        + COLUMN_DESCRIPTION + " text not null, "
        + COLUMN_URI + " text not null, "
        + COLUMN_UPDATED + " text not null);";
    private static final String CREATE_TABLE_DOCUMENT = "create table "
        + TABLE_DOCUMENT + "(" + COLUMN_ID
        + " integer primary key autoincrement, " 
        + COLUMN_NAME + " text not null, "
        + COLUMN_REVISION + " integer not null, "
        + COLUMN_DESCRIPTION + " text not null, "
        + COLUMN_URI + " text not null, "
        + COLUMN_UPDATED + " text not null);";
    private static final String CREATE_TABLE_QUIZ = "create table "
        + TABLE_QUIZ + "(" + COLUMN_ID
        + " integer primary key autoincrement, " 
        + COLUMN_NAME + " text not null, "
        + COLUMN_DESCRIPTION + " text not null, " 
        + COLUMN_URI + " text not null, "
        + COLUMN_UPDATED + " text not null);";

    public MuldvarpSQLDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context.deleteDatabase(DATABASE_NAME);
    }
    
    public String getTableCreationString(String tableName, String[] fields){
        
        String retVal = "CREATE TABLE " + tableName + "(";
        retVal += "";
        
        for (int i = 0; i < fields.length; i++) {
            
        }
        
        retVal += ");";
        
        return "";
    }

    @Override
    public void onCreate(SQLiteDatabase database) {                
        //Create tables
        database.execSQL(CREATE_TABLE_PROGRAMME);
        database.execSQL(CREATE_TABLE_COURSE);
        database.execSQL(CREATE_TABLE_TOPIC);
        database.execSQL(CREATE_TABLE_VIDEO);
        database.execSQL(CREATE_TABLE_DOCUMENT);
        database.execSQL(CREATE_TABLE_QUIZ);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MuldvarpSQLDatabaseHelper.class.getName(),
            "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPIC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);
        onCreate(db);
    }

}
