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
import no.hials.muldvarp.v2.database.tables.*;
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
        System.out.println("cursor size " + cursor.getCount());
        return (cursor.getCount() > 0);
    }
    
    public long insertProgramme(Programme programme) {
        
        //Get text/int value fields from Domain and insert into table
        ContentValues values = new ContentValues();
        values.put(ProgrammeTable.COLUMN_UNIQUEID, programme.getId());
        values.put(ProgrammeTable.COLUMN_NAME, programme.getName());
        values.put(ProgrammeTable.COLUMN_DESCRIPTION, programme.getDescription());
        values.put(ProgrammeTable.COLUMN_REVISION, programme.getRevision());
        values.put(ProgrammeTable.COLUMN_UPDATED, getTimeStamp());
        long insertId;
        if(checkRecord(ProgrammeTable.TABLE_NAME, ProgrammeTable.COLUMN_NAME, programme.getName())){
            System.out.println("updating " + programme.getName());
            insertId = database.update(ProgrammeTable.TABLE_NAME, values,
                    ProgrammeTable.COLUMN_ID + "='" + getProgrammeId(programme) + "'",
                    null);
        } else {
            System.out.println("inserting " + programme.getName());
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
    
    public long getProgrammeId(Programme programme){
                
        System.out.println(programme.getName());
        Cursor cursor = database.rawQuery("SELECT "
                + "*" + " FROM " 
                + ProgrammeTable.TABLE_NAME 
                + " WHERE " + ProgrammeTable.COLUMN_NAME 
                + " = '"+programme.getName()+"'", null);
        
        cursor.moveToFirst();
        System.out.println("size " + cursor.getCount());
        long retVal = cursor.getLong(0);
        cursor.close();
        
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
        values.put(CourseTable.COLUMN_UNIQUEID, course.getId());
        values.put(CourseTable.COLUMN_REVISION, course.getRevision());
        values.put(CourseTable.COLUMN_UPDATED, getTimeStamp());
        long insertId;
        if(checkRecord(CourseTable.TABLE_NAME, CourseTable.COLUMN_NAME, course.getName())){
            insertId = database.update(CourseTable.TABLE_NAME, values, null, null);
                    System.out.println("updating " + course.getName());

        } else {
                    System.out.println("inserting " + course.getName());

            insertId = database.insert(CourseTable.TABLE_NAME, null,
            values);
        }
        
        //Set up relation 
        String[] columns = MuldvarpDBHelper.getColumns(CourseHasTopicTable.TABLE_COLUMNS);        
        ArrayList<Topic> topicList = (ArrayList<Topic>) course.getTopics();
        
        if (topicList != null) {
            for (int i = 0; i < topicList.size(); i++) {
                values = new ContentValues();
                values.put(columns[1], insertId);
                values.put(columns[2], 1);
//                values.put(columns[2], insertTask(topicList.get(i)));
                database.insert(CourseHasTopicTable.TABLE_NAME, null, values);
            }
        }
        
        
        Cursor cursor = database.query(CourseTable.TABLE_NAME,
            MuldvarpTable.getColumns(CourseTable.TABLE_COLUMNS), CourseTable.COLUMN_ID + " = " + insertId, null,
            null, null, null);
        cursor.moveToFirst();
        Course retVal = cursorToCourse(cursor);
        cursor.close();
        return insertId;
    }

    public void deleteCourse(Course course) {
        long id = course.getId();
        System.out.println("Course deleted with id: " + id);
        database.delete(CourseTable.TABLE_NAME, MuldvarpTable.COLUMN_ID
            + " = " + id, null);
    }

    public Course getCourse(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM " 
                + CourseTable.TABLE_NAME 
                + " WHERE " + CourseTable.COLUMN_NAME 
                + " = '"+name+"'", null);
        Course retVal = cursorToCourse(cursor);
        return retVal;
    }
    
    public ArrayList<Domain> getCoursesByProgramme(Programme programme){
        
        System.out.println("getprogrammeid " + getProgrammeId(programme));
        
        ArrayList<Domain> courses = new ArrayList<Domain>();
        Cursor cursor = database.rawQuery("SELECT " + CourseTable.TABLE_NAME + CourseTable.COLUMN_ID 
                + " FROM " +  ProgrammeHasCourseTable.TABLE_NAME
                + " WHERE " + ProgrammeTable.TABLE_NAME + ProgrammeTable.COLUMN_ID
                + " = '" + getProgrammeId(programme) + "'", null);
        cursor.moveToFirst();
        System.out.println("GET COURSES BY PROGRAM SIZE " + cursor.getCount());
        while (!cursor.isAfterLast()) {
            Cursor tableCursor = database.rawQuery("SELECT * FROM " 
                + CourseTable.TABLE_NAME 
                + " WHERE " + CourseTable.COLUMN_ID
                + " = '"+cursor.getFloat(0)+"'", null);
            Course course = cursorToCourse(tableCursor);
            courses.add(course);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return courses;
    }
    
    public long insertUser(User user) {        
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_NAME, user.getName());        
        values.put(UserTable.COLUMN_PASSWORD, user.getPassword());
        
        long insertId;
        if(checkRecord(UserTable.TABLE_NAME, UserTable.COLUMN_NAME, user.getName())){
            insertId = database.update(UserTable.TABLE_NAME, values, null, null);
                    System.out.println("updating " + user.getName());
        } else {
                    System.out.println("inserting " + user.getName());
            insertId = database.insert(UserTable.TABLE_NAME, null,
            values);
        }
        String[] columns = MuldvarpDBHelper.getColumns(UserHasCourseTable.TABLE_COLUMNS);        
        ArrayList<Course> courseList = (ArrayList<Course>) user.getUserCourses();
        
        if (courseList != null) {
            for (int i = 0; i < courseList.size(); i++) {
                values = new ContentValues();
                values.put(columns[1], insertId);
                values.put(columns[2], 1);
                database.insert(UserHasCourseTable.TABLE_NAME, null, values);
            }
        }
        
        return insertId;
    }
    
    public void deleteUser(User user) {
        long id = user.getId();
        System.out.println("User deleted with id: " + id);
        database.delete(UserTable.TABLE_NAME, UserTable.COLUMN_ID
            + " = " + id, null);
    }
    
    public User getUser(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM " 
                + UserTable.TABLE_NAME 
                + " WHERE " + ProgrammeTable.COLUMN_NAME 
                + " = '"+name+"'", null);
        User retVal = cursorToUser(cursor);
        return retVal;
    }
    
    public ArrayList<Course> getCoursesFromUser(int userId){
        
        String[] columns = new String[] { CourseTable.TABLE_NAME + CourseTable.COLUMN_ID };
        String[] selectionArgs = new String[] {
        String.valueOf(userId) };
        Cursor cursor = database.query(UserHasCourseTable.TABLE_NAME, columns,
        UserTable.TABLE_NAME + UserTable.COLUMN_ID + "= ?", selectionArgs, null,
        null, null);        
        
        ArrayList<Course> courses = new ArrayList<Course>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Course course = cursorToCourse(cursor);
            courses.add(course);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        
        
        return courses;
        
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
        int id = (int) cursor.getLong(1);
        course.setId(id);
        course.setName(cursor.getString(2));
        return course;
    }

    private Task cursorToTopic(Cursor cursor) {
        Task topic = new Task();
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
    
    private User cursorToUser(Cursor cursor) {
        User user = new User();
        int id = (int) cursor.getLong(0);
        user.setId(id);
        user.setName(cursor.getString(1));        
        user.setPassword(cursor.getString(2));
        
        return user; 
    }
}
