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
import no.hials.muldvarp.v2.domain.*;

/**
 * This class is responsible for managing the SQLITE data source.
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

    public void createRelation(String table, String id1, String id2){
        ContentValues values = new ContentValues();
    }

    public void deleteRelation(String table, String id1, String id2){

    }


    public long insertProgramme(Programme programme) {

        //Get text/int value fields from Domain and insert into table
        ContentValues values = new ContentValues();
        values.put(MuldvarpDBHelper.COLUMN_NAME, programme.getName());
        values.put(MuldvarpDBHelper.COLUMN_REVISION, 15);
        values.put(MuldvarpDBHelper.COLUMN_UPDATED, "sadasd");
        long insertId = database.insert(MuldvarpDBHelper.TABLE_PROGRAMME, null,
            values);
        
        //Set up relation 
        String[] columns = MuldvarpDBHelper.getColumns(MuldvarpDBHelper.TABLE_PROGRAMME_HAS_COURSE_COLUMNS);
        values = new ContentValues();
        
        ArrayList<Course> courseList = new ArrayList<Course>();
        courseList = (ArrayList<Course>) programme.getCourses();
        
        if (courseList != null) {
            for (int i = 0; i < courseList.size(); i++) {
                values.put(columns[0], insertId);
                values.put(columns[1], insertCourse(courseList.get(i)));
            }
        }
                        
        Cursor cursor = database.query(MuldvarpDBHelper.TABLE_PROGRAMME,
            MuldvarpDBHelper.getColumns(MuldvarpDBHelper.TABLE_PROGRAMME_COLUMNS), MuldvarpDBHelper.COLUMN_ID + " = " + insertId, null,
            null, null, null);
        cursor.moveToFirst();
        
        Programme newprogramme = cursorToProgramme(cursor);
        cursor.close();
        return insertId;
    }


    public void deleteProgramme(Programme programme) {
        long id = programme.getId();
        System.out.println("programme deleted with id: " + id);
        database.delete(MuldvarpDBHelper.TABLE_PROGRAMME, MuldvarpDBHelper.COLUMN_ID
            + " = " + id, null);
    }

    public Programme getProgramme(String name){

        Cursor cursor = database.rawQuery("SELECT * FROM " + MuldvarpDBHelper.TABLE_PROGRAMME + " WHERE name = '"+name+"'", null);
        Programme retVal = new Programme();
        retVal = cursorToProgramme(cursor);
        return retVal;
    }

    public ArrayList<Domain> getAllProgrammes() {
        ArrayList<Domain> programmes = new ArrayList<Domain>();

        Cursor cursor = database.query(MuldvarpDBHelper.TABLE_PROGRAMME,
            MuldvarpDBHelper.getColumns(MuldvarpDBHelper.TABLE_PROGRAMME_COLUMNS),
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
        values.put(MuldvarpDBHelper.COLUMN_NAME, course.getName());
        values.put(MuldvarpDBHelper.COLUMN_REVISION, 15);
        values.put(MuldvarpDBHelper.COLUMN_UPDATED, "sadasd");
        long insertId = database.insert(MuldvarpDBHelper.TABLE_PROGRAMME, null,
            values);
        Cursor cursor = database.query(MuldvarpDBHelper.TABLE_PROGRAMME,
            MuldvarpDBHelper.getColumns(MuldvarpDBHelper.TABLE_PROGRAMME_COLUMNS), MuldvarpDBHelper.COLUMN_ID + " = " + insertId, null,
            null, null, null);
        cursor.moveToFirst();
        Programme retVal = cursorToProgramme(cursor);
        cursor.close();
        return insertId;
    }


    public void deleteCourse(Course course) {
        long id = course.getId();
        System.out.println("programme deleted with id: " + id);
        database.delete(MuldvarpDBHelper.TABLE_PROGRAMME, MuldvarpDBHelper.COLUMN_ID
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
        
        Cursor cursor = database.rawQuery("SELECT "+ MuldvarpDBHelper.COLUMN_ID
                +" FROM "+  MuldvarpDBHelper.TABLE_PROGRAMME
                +" WHERE "+ MuldvarpDBHelper.COLUMN_NAME
                +" = '"+programme.getName()+"'", null);
        
        return null;
    }

    private Programme cursorToProgramme(Cursor cursor) {
        Programme programme = new Programme();
        int id = (int) cursor.getLong(0);
        programme.setId(id);
        programme.setName(cursor.getString(1));
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
