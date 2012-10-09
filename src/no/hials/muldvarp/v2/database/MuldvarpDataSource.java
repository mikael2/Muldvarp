/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import no.hials.muldvarp.v2.database.tables.CourseTable;
import no.hials.muldvarp.v2.database.tables.MuldvarpTable;
import no.hials.muldvarp.v2.database.tables.ProgrammeHasCourseTable;
import no.hials.muldvarp.v2.database.tables.ProgrammeTable;
import no.hials.muldvarp.v2.domain.*;

/**
 * This class is responsible for managing the SQLITE data source, integrating it
 * with the Muldvarp application Domain.
 * @author johan
 */
public class MuldvarpDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MuldvarpDBHelper dbHelper;  

    public MuldvarpDataSource(Context context) {
        dbHelper = new MuldvarpDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();    
    }

    public void close() {
        dbHelper.close();
    }
    
    public long getTimeStamp() {        
        return (System.currentTimeMillis() / 1000L);
    }
    
    public long createRelation(String tableName, String table1, String table2){
        ContentValues values = new ContentValues();
        values.put(table1 + MuldvarpTable.COLUMN_ID, "");
        values.put(table2 + MuldvarpTable.COLUMN_ID, "");
        
        return database.insert(tableName, null, values);
    }
    
    public boolean checkRecord(String table, String field, String value){
        Cursor cursor = database.rawQuery("SELECT * FROM " 
                + table + " WHERE " 
                + field + " = '" 
                + value + "'", null);
        
        return (cursor == null);
    }
    
    public long insertProgramme(Programme programme) {
        
        System.out.println("inserting " + programme.getName());

        //Get text/int value fields from Domain and insert into table
        ContentValues values = new ContentValues();
        values.put(ProgrammeTable.COLUMN_UNIQUEID, programme.getId());
        values.put(ProgrammeTable.COLUMN_NAME, programme.getName());
        values.put(ProgrammeTable.COLUMN_DESCRIPTION, programme.getDescription());
        values.put(ProgrammeTable.COLUMN_REVISION, programme.getRevision());
        values.put(ProgrammeTable.COLUMN_UPDATED, getTimeStamp());
        long insertId;
        if(checkRecord(ProgrammeTable.TABLE_NAME, ProgrammeTable.COLUMN_NAME, programme.getName())){
            insertId = database.update(ProgrammeTable.TABLE_NAME, values, null, null);
        } else {
            insertId = database.insert(ProgrammeTable.TABLE_NAME, null,
            values);
        }
        
        //Set up relation 
        String[] columns = MuldvarpDBHelper.getColumns(ProgrammeHasCourseTable.TABLE_COLUMNS);        
        ArrayList<Course> courseList = (ArrayList<Course>) programme.getCourses();
        
        if (courseList != null) {
            for (int i = 0; i < courseList.size(); i++) {
                values = new ContentValues();
                values.put(columns[1], insertId);
                values.put(columns[2], insertCourse(courseList.get(i)));
                database.insert(ProgrammeHasCourseTable.TABLE_NAME, null, values);
            }
        }
                        
        Cursor cursor = database.query(ProgrammeTable.TABLE_NAME,
            MuldvarpDBHelper.getColumns(ProgrammeTable.TABLE_COLUMNS), MuldvarpTable.COLUMN_ID + " = " + insertId, null,
            null, null, null);
        cursor.moveToFirst();
        
        Programme newprogramme = cursorToProgramme(cursor);
        cursor.close();
        return insertId;
    }

    //NYI
    public void deleteProgramme(Programme programme) {
        long id = programme.getId();
        System.out.println("programme deleted with id: " + id);
        database.delete(ProgrammeTable.TABLE_NAME, ProgrammeTable.COLUMN_ID
            + " = " + id, null);
    }

    public Programme getProgramme(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM " 
                + ProgrammeTable.TABLE_NAME 
                + " WHERE " + ProgrammeTable.COLUMN_NAME 
                + " = '"+name+"'", null);
        Programme retVal = cursorToProgramme(cursor);
        return retVal;
    }

    public ArrayList<Domain> getAllProgrammes() {
        ArrayList<Domain> programmes = new ArrayList<Domain>();

        Cursor cursor = database.query(ProgrammeTable.TABLE_NAME ,
            MuldvarpDBHelper.getColumns(ProgrammeTable.TABLE_COLUMNS),
            null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        Programme programme = cursorToProgramme(cursor);
        programmes.add(programme);
        cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return programmes;
    }

    public long insertCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_NAME, course.getName());        
        values.put(ProgrammeTable.COLUMN_UNIQUEID, course.getId());
        values.put(CourseTable.COLUMN_REVISION, course.getRevision());
        values.put(CourseTable.COLUMN_UPDATED, getTimeStamp());
        long insertId = database.insert(CourseTable.TABLE_NAME, null,
            values);
        Cursor cursor = database.query(CourseTable.TABLE_NAME,
            MuldvarpTable.getColumns(CourseTable.TABLE_COLUMNS), CourseTable.COLUMN_ID + " = " + insertId, null,
            null, null, null);
        cursor.moveToFirst();
        Programme retVal = cursorToProgramme(cursor);
        cursor.close();
        return insertId;
    }

    public void deleteCourse(Course course) {
        long id = course.getId();
        System.out.println("programme deleted with id: " + id);
        database.delete(CourseTable.TABLE_NAME, MuldvarpTable.COLUMN_ID
            + " = " + id, null);
    }

    public Programme getCourse(String name){

        Cursor cursor = database.rawQuery("SELECT * FROM tbl1 WHERE name = '"+name+"'", null);
        Programme retVal = new Programme();
        int id = (int) cursor.getLong(0);
        retVal.setId(id);
        retVal.setName(cursor.getString(1));      
        return retVal;
    }
    
    public ArrayList<Course> getCoursesByProgramme(Programme programme){
        
        Cursor cursor = database.rawQuery("SELECT " + MuldvarpTable.COLUMN_ID
                + " FROM " +  ProgrammeTable.TABLE_NAME
                + " WHERE " + ProgrammeTable.COLUMN_NAME
                + " = '" + programme.getName() + "'", null);
        
        return null;
    }

    private Programme cursorToProgramme(Cursor cursor) {
        Programme programme = new Programme();
        int id = (int) cursor.getLong(1);
        programme.setId(id);
        programme.setName(cursor.getString(2));        
        return programme;
    }

    private Course cursorToCourse(Cursor cursor) {
        Course course = new Course();
        int id = (int) cursor.getLong(0);
        course.setId(id);
        course.setName(cursor.getString(1));
        return course;
    }

    private Topic cursorToTopic(Cursor cursor) {
        Topic topic = new Topic();
        int id = (int) cursor.getLong(0);
        topic.setId(id);
        topic.setName(cursor.getString(1));
        return topic;
    }

    private Video cursorToVideo(Cursor cursor) {
        Video video = new Video();
        int id = (int) cursor.getLong(0);
        video.setId(id);
        video.setName(cursor.getString(1));
        return video;
    }
  
}
