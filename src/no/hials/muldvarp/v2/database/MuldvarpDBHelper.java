/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import no.hials.muldvarp.v2.database.tables.*;

/**
 * This class is responsible for creating, maintaining and upgrading the SQLITE database.
 * Right now the database creation consists more or less hard-coded as SQL-statements, 
 * perhaps dynamic database creation should be considered.
 * 
 * @author johan
 */
public class MuldvarpDBHelper extends SQLiteOpenHelper {

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
//    //Tables
//    //Programmes
//    public static final String TABLE_PROGRAMME = "programme";    
//    //Courses
//    public static final String TABLE_COURSE = "course";    
//    //Topics
//    public static final String TABLE_TOPIC = "topic";    
//    //Videos
//    public static final String TABLE_VIDEO = "video";    
//    //Documents
//    public static final String TABLE_DOCUMENT = "document";    
//    //Quizzes
//    public static final String TABLE_QUIZ = "quiz";    
//    //Relations
//    public static final String TABLE_PROGRAMME_HAS_COURSE = "programme_has_course";
//    public static final String TABLE_COURSE_HAS_TOPIC = "course_has_task";
//    public static final String TABLE_PROGRAMME_HAS_QUIZ = "programme_has_quiz";    
//    public static final String TABLE_PROGRAMME_HAS_VIDEO = "programme_has_video";    
//    public static final String TABLE_COURSE_HAS_VIDEO = "course_has_video";    
//    public static final String TABLE_PROGRAMME_HAS_DOCUMENT = "programme_has_document";    
//    public static final String TABLE_COURSE_HAS_DOCUMENT = "course_has_document";    
//    public static final String TABLE_USER_COURSES ="usercourses";
        
    public static final String[][] TABLE_PROGRAMME_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_REVISION," integer not null"},
      {COLUMN_UPDATED, " text not null"}
    };
    
    public static final String[][] TABLE_COLUMNS_COURSE = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_REVISION," integer not null"},
      {COLUMN_UPDATED, " text not null"}
    };
    
    public static final String[][] TABLE_COLUMNS_TOPIC = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_REVISION," integer not null"},
      {COLUMN_UPDATED, " text not null"}
    };
    
    public static final String[][] TABLE_COLUMNS_VIDEO = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_REVISION," integer not null"},
      {COLUMN_DESCRIPTION," text not null"},
      {COLUMN_URI," text not null"},
      {COLUMN_UPDATED, " text not null"}
    };
    
    public static final String[][] TABLE_COLUMNS_DOCUMENT = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_REVISION," integer not null"},
      {COLUMN_DESCRIPTION," text not null"},
      {COLUMN_URI," text not null"},
      {COLUMN_UPDATED, " text not null"}
    };
    
    public static final String[][] TABLE_COLUMNS_QUIZ = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_REVISION," integer not null"},
      {COLUMN_DESCRIPTION," text not null"},
      {COLUMN_URI," text not null"},
      {COLUMN_UPDATED, " text not null"}
    };
    
//    public static final String[][] TABLE_PROGRAMME_HAS_COURSE_COLUMNS = {
//      {TABLE_PROGRAMME + COLUMN_ID, " integer not null"},
//      {TABLE_COURSE + COLUMN_ID, " integer not null"}
//    };
//    
//    public static final String[][] TABLE_PROGRAMME_HAS_QUIZ_COLUMNS = {
//      {TABLE_PROGRAMME + COLUMN_ID, " integer not null"},
//      {TABLE_QUIZ + COLUMN_ID, " integer not null"}
//    };
//    
//    public static final String[][] TABLE_PROGRAMME_HAS_DOCUMENT_COLUMNS = {
//      {TABLE_PROGRAMME + COLUMN_ID, " integer not null"},
//      {TABLE_DOCUMENT + COLUMN_ID, " integer not null"}
//    };
//    
//    public static final String[][] TABLE_COURSE_HAS_TOPIC_COLUMNS = {
//      {TABLE_COURSE + COLUMN_ID, " integer not null"},
//      {TABLE_TOPIC + COLUMN_ID, " integer not null"}
//    };
//    
//    public static final String[][] TABLE_COURSE_HAS_QUIZ_COLUMNS = {
//      {TABLE_COURSE + COLUMN_ID, " integer not null"},
//      {TABLE_QUIZ + COLUMN_ID, " integer not null"}
//    };
//    
//    public static final String[][] TABLE_COURSE_HAS_DOCUMENT_COLUMNS = {
//      {TABLE_COURSE + COLUMN_ID, " integer not null"},
//      {TABLE_DOCUMENT + COLUMN_ID, " integer not null"}
//    };
//    
//    public static final String[][] TABLE_USER_HAS_COURSES = {
//      {COLUMN_ID, " integer not null"},
//      {TABLE_COURSE + COLUMN_ID, " integer not null"}
//    };
    

    public MuldvarpDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context.deleteDatabase(DATABASE_NAME);
    }
    
    /**
     * This method returns the field names of a table.
     * 
     * @param table
     * @return 
     */
    public String[] getColumns(String[][] table){
        
        String[] retVal = new String[table.length];
        
        for (int i = 0; i < table.length; i++) {
            retVal[i] = table[i][0];
        }
        
        return retVal;
    }
    
    /**
     * This function creates a SQL-creation statement based on a String name for the
     * table, and a multi-dimensional array of String values containing field names
     * and their attributes.
     * 
     * @param tableName
     * @param fields
     * @return 
     */
    public static String getTableCreationString(String tableName, String[][] fields){
        
        String retVal = "CREATE TABLE " + tableName + "(";
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                retVal += fields[i][j];                
            }
            if(i < (fields.length -1)){
                retVal +=",";
            }
        }
        retVal += ");";
        
        return retVal;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {                
        //Create tables
        database.execSQL(ProgrammeTable.getTableCreationString());
        database.execSQL(CourseTable.getTableCreationString());
        database.execSQL(TopicTable.getTableCreationString());
        database.execSQL(VideoTable.getTableCreationString());
        database.execSQL(DocumentTable.getTableCreationString());
        database.execSQL(QuizTable.getTableCreationString());
        database.execSQL(ProgrammeHasCourseTable.getTableCreationString());
        database.execSQL(ProgrammeHasDocumentTable.getTableCreationString());
        database.execSQL(ProgrammeHasQuizTable.getTableCreationString());
        database.execSQL(CourseHasTopicTable.getTableCreationString());
        database.execSQL(CourseHasDocumentTable.getTableCreationString());
        
        
//        database.execSQL(getTableCreationString(TABLE_PROGRAMME, TABLE_PROGRAMME_COLUMNS));
//        database.execSQL(getTableCreationString(TABLE_COURSE, TABLE_COLUMNS_COURSE));
//        database.execSQL(getTableCreationString(TABLE_TOPIC, TABLE_COLUMNS_TOPIC));
//        database.execSQL(getTableCreationString(TABLE_VIDEO, TABLE_COLUMNS_VIDEO));
//        database.execSQL(getTableCreationString(TABLE_DOCUMENT, TABLE_COLUMNS_DOCUMENT));
//        database.execSQL(getTableCreationString(TABLE_QUIZ, TABLE_COLUMNS_QUIZ));
//        database.execSQL(getTableCreationString(TABLE_PROGRAMME_HAS_COURSE, TABLE_PROGRAMME_HAS_COURSE_COLUMNS));
//        database.execSQL(getTableCreationString(TABLE_PROGRAMME_HAS_DOCUMENT, TABLE_PROGRAMME_HAS_DOCUMENT_COLUMNS));
//        database.execSQL(getTableCreationString(TABLE_PROGRAMME_HAS_QUIZ, TABLE_PROGRAMME_HAS_QUIZ_COLUMNS));
//        database.execSQL(getTableCreationString(TABLE_COURSE_HAS_DOCUMENT, TABLE_COURSE_HAS_DOCUMENT_COLUMNS));
//        database.execSQL(getTableCreationString(TABLE_COURSE_HAS_TOPIC, TABLE_COURSE_HAS_TOPIC_COLUMNS));
//        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MuldvarpDBHelper.class.getName(),
            "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + ProgrammeTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CourseTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TopicTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VideoTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DocumentTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuizTable.TABLE_NAME);
        onCreate(db);
    }

}
