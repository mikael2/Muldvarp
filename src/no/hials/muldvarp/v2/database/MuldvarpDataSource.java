/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
        dbHelper = MuldvarpDBHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * This method generates unix time.
     * @return long unix time
     */
    public long getTimeStamp() {
        return (System.currentTimeMillis() / 1000L);
    }

    public long createRelation(String tableName, String table1, long id1, String table2, long id2){
        ContentValues values = new ContentValues();
        values.put(table1, id1);
        values.put(table2, id2);

        return database.insert(tableName, null, values);
    }

    /**
     * This method implements createRelation for CourseTable and ProgrammeTable.
     * @param id1
     * @param id2
     * @return long id
     */
    public long createProgrammeCourseRelation(long id1, long id2){
//        System.out.println("CREATING PRGRAMME COURSE RELATION");
//        System.out.println("BETWEEN ID " + id1 + " AND " + id2);
        return createRelation(ProgrammeHasCourseTable.TABLE_NAME,
                ProgrammeTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id1,
                CourseTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id2);
    }

    /**
     * This method implements createRelation for CourseTable and TopicTable.
     * @param id1
     * @param id2
     * @return long id
     */
    public long createCourseTopicRelation(long id1, long id2){
        return createRelation(CourseHasTopicTable.TABLE_NAME,
                CourseTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id1,
                TopicTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id2);
    }
    
    /**
     * This method implements createRelation for Programme and DocumentTable.
     * @param id1
     * @param id2
     * @return long id
     */
    public long createProgrammeDocumentRelation(long id1, long id2){
        return createRelation(ProgrammeHasDocumentTable.TABLE_NAME,
                ProgrammeTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id1,
                DocumentTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id2);
    }

    /**
     * This method implements createRelation for Course and DocumentTable.
     * @param id1
     * @param id2
     * @return long id
     */
    public long createCourseDocumentRelation(long id1, long id2){
        return createRelation(CourseHasDocumentTable.TABLE_NAME,
                CourseTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id1,
                DocumentTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id2);
    }
    
     /**
     * This method implements createRelation for CourseTable and ArticleTable.
     * @param id1
     * @param id2
     * @return long id
     */
    public long createCourseArticleRelation(long id1, long id2){
        return createRelation(CourseHasArticleTable.TABLE_NAME,
                CourseTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id1,
                ArticleTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id2);
    }

     /**
     * This method implements createRelation for CourseTable and ArticleTable.
     * @param id1
     * @param id2
     * @return long id
     */
    public long createProgrammeArticleRelation(long id1, long id2){
        return createRelation(ProgrammeHasArticleTable.TABLE_NAME,
                ProgrammeTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id1,
                ArticleTable.TABLE_NAME + MuldvarpTable.COLUMN_ID, id2);
    }

    /**
     * This function checks if a given value exists in a given field in a given table.
     * Returns true if the record(s) exist, returns false if not.
     *
     * @param table
     * @param field
     * @param value
     * @return
     */
    public boolean checkRecord(String table, String field, String value){
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + table + " WHERE "
                + field + " = '"
                + value + "'", null);
//        System.out.println("cursor size " + cursor.getCount());
        return (cursor.getCount() > 0);
    }

    
    /**
     * This function inserts a Programme into the SQLITE database, and if the database
     * record already exists, updates the table instead.
     *
     * @param programme
     * @return primary key id
     */
    public long insertProgramme(Programme programme) {
        //Get text/int value fields from Domain and insert into table
        ContentValues values = new ContentValues();
        values.put(ProgrammeTable.COLUMN_ID, programme.getId());
        values.put(ProgrammeTable.COLUMN_UNIQUEID, programme.getId());
        values.put(ProgrammeTable.COLUMN_NAME, programme.getName());
        values.put(ProgrammeTable.COLUMN_DESCRIPTION, programme.getDescription());
        values.put(ProgrammeTable.COLUMN_REVISION, programme.getRevision());
        values.put(ProgrammeTable.COLUMN_UPDATED, getTimeStamp());
        long insertId;
        if(checkRecord(ProgrammeTable.TABLE_NAME, ProgrammeTable.COLUMN_ID, String.valueOf(programme.getId()))){
            System.out.println("updating " + programme.getName());
            String id[] = {programme.getId().toString()};
            insertId = database.update(ProgrammeTable.TABLE_NAME, values,
                    ProgrammeTable.COLUMN_ID + "=?",
                    id);
            System.out.println("Updated entry with ID " + insertId);
        } else {
            System.out.println("inserting " + programme.getName());
            insertId = database.insert(ProgrammeTable.TABLE_NAME, null,
            values);
        }
        //Inserts the courses in the Programme and sets up relations
        ArrayList<Course> courseList = (ArrayList<Course>) programme.getCourses();
        if (courseList != null) {
            for (int i = 0; i < courseList.size(); i++) {
                createProgrammeCourseRelation(insertId, insertCourse(courseList.get(i)));
            }
        }
        return insertId;
    }

    public void deleteProgramme(Programme programme) {

        //Now deletes based on name
        String[] name = {programme.getName()};
        System.out.println("programme deleted with name: " + name);
        database.delete(ProgrammeTable.TABLE_NAME, ProgrammeTable.COLUMN_NAME
            + "=?", name);
    }

    public Programme getProgrammeByName(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + ProgrammeTable.TABLE_NAME
                + " WHERE " + ProgrammeTable.COLUMN_NAME
                + " = '"+name+"'", null);
        Programme retVal = cursorToProgramme(cursor);
        return retVal;
    }
    
    public Programme getProgrammeById(String name){
        System.out.println("SELECT * FROM "
                + ProgrammeTable.TABLE_NAME
                + " WHERE " + ProgrammeTable.COLUMN_ID
                + " = '"+name+"'");
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + ProgrammeTable.TABLE_NAME
                + " WHERE " + ProgrammeTable.COLUMN_ID
                + " = '"+name+"'", null);
        Programme retVal = cursorToProgramme(cursor);
        return retVal;
    }

    public Programme getProgrammeByUniqueId(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + ProgrammeTable.TABLE_NAME
                + " WHERE " + ProgrammeTable.COLUMN_UNIQUEID
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

    public ArrayList<Domain> getAllCourses() {
        ArrayList<Domain> courses = new ArrayList<Domain>();

        Cursor cursor = database.query(CourseTable.TABLE_NAME ,
            MuldvarpDBHelper.getColumns(CourseTable.TABLE_COLUMNS),
            null, null, null, null, null);

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
    
    /**
     * This function inserts a Programme into the SQLITE database, and if the database
     * record already exists, updates the table instead.
     *
     * @param programme
     * @return primary key id
     */
    public long insertDocument(Document document) {

        //Get text/int value fields from Domain and insert into table
        ContentValues values = new ContentValues();
        values.put(DocumentTable.COLUMN_ID, document.getId());
        values.put(DocumentTable.COLUMN_NAME, document.getName());
        values.put(DocumentTable.COLUMN_DESCRIPTION, document.getDescription());
        values.put(DocumentTable.COLUMN_URI, document.getURI());
        long insertId;
        if(checkRecord(DocumentTable.TABLE_NAME, DocumentTable.COLUMN_ID, String.valueOf(document.getId()))){
            System.out.println("updating " + document.getName());
            String id[] = {document.getId().toString()};
            insertId = database.update(DocumentTable.TABLE_NAME, values,
                    DocumentTable.COLUMN_ID + "=?",
                    id);
        } else {
            System.out.println("inserting " + document.getName());
            insertId = database.insert(DocumentTable.TABLE_NAME, null,
            values);
        }
        return insertId;
    }
    
    public ArrayList<Domain> getAllDocuments() {
        ArrayList<Domain> documents = new ArrayList<Domain>();

        Cursor cursor = database.query(DocumentTable.TABLE_NAME ,
            MuldvarpDBHelper.getColumns(DocumentTable.TABLE_COLUMNS),
            null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        Document document = cursorToDocument(cursor);
        documents.add(document);
        cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return documents;
    }

    public ArrayList<Domain> getArticlesByCategory(String name){
        ArrayList<Domain> retVal = new ArrayList<Domain>();
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + ArticleTable.TABLE_NAME
                + " WHERE " + ArticleTable.COLUMN_CATEGORY
                + " = '"+name+"'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Article article = cursorToArticle(cursor);
            if(article.getCategory() != null && article.getCategory().equals(name)) {
                retVal.add(article);
                cursor.moveToNext();
            }
        }
        // Make sure to close the cursor
        cursor.close();
        return retVal;
    }

    public long insertCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_ID, course.getCourseId());
        values.put(CourseTable.COLUMN_UNIQUEID, course.getCourseId());
        values.put(CourseTable.COLUMN_NAME, course.getName());
        values.put(CourseTable.COLUMN_REVISION, course.getRevision());
        values.put(CourseTable.COLUMN_UPDATED, getTimeStamp());
        long insertId;
        if(checkRecord(CourseTable.TABLE_NAME, CourseTable.COLUMN_ID, String.valueOf(course.getId()))){
//            insertId = database.update(CourseTable.TABLE_NAME, values,
//                    CourseTable.COLUMN_ID + "='" + getCourseId(course) + "'",
//                    null);
            String id[] = {course.getCourseId()};
            insertId = database.update(CourseTable.TABLE_NAME, values,
            CourseTable.COLUMN_ID + "=?",
            id);
            System.out.println("updating " + course.getName());

        } else {
            System.out.println("inserting " + course.getName());
            insertId = database.insert(CourseTable.TABLE_NAME, null,
            values);
        }

        //Set up relation etc
        ArrayList<Topic> topicList = (ArrayList<Topic>) course.getTopics();

        if (topicList != null) {
            for (int i = 0; i < topicList.size(); i++) {
                createCourseTopicRelation(insertId, insertTopic(topicList.get(i)));
            }
        }
        return insertId;
    }

    public void deleteCourse(Course course) {
        //Now deletes based on name
        String[] name = {course.getName()};
        System.out.println("Course deleted with name: " + name[0]);
        database.delete(CourseTable.TABLE_NAME, CourseTable.COLUMN_NAME
            + "=?", name);
    }

    public Course getCourseByName(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + CourseTable.TABLE_NAME
                + " WHERE " + CourseTable.COLUMN_NAME
                + " = '"+name+"'", null);
        Course retVal = cursorToCourse(cursor);
        return retVal;
    }

    public Course getCourseByUniqueId(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + CourseTable.TABLE_NAME
                + " WHERE " + CourseTable.COLUMN_UNIQUEID
                + " = '"+name+"'", null);
        Course retVal = cursorToCourse(cursor);
        return retVal;
    }

    public long getCourseId(Course course){
        System.out.println(course.getName());
        Cursor cursor = database.rawQuery("SELECT "
                + "*" + " FROM "
                + CourseTable.TABLE_NAME
                + " WHERE " + CourseTable.COLUMN_NAME
                + " = '"+course.getName()+"'", null);

        cursor.moveToFirst();
        System.out.println("size " + cursor.getCount());
        long retVal = cursor.getLong(0);
        cursor.close();

        return retVal;
    }

    public ArrayList<Domain> getCoursesByProgramme(Programme programme){
        System.out.println("GET COURSES BY PROGRAMME");
        System.out.println("THE ID IS " + programme.getId());
        System.out.println("SELECT * FROM " + ProgrammeHasCourseTable.TABLE_NAME);
        Cursor cursorTest = database.query(ProgrammeHasCourseTable.TABLE_NAME, null, null, null, null, null, null);
        System.out.println("asdasdasdasdsada " + cursorTest.getColumnCount());
        System.out.println("derrrrrrr " + cursorTest.getCount());


        //Course ID column is the second one
        String[] column = new String[] { ProgrammeHasCourseTable.TABLE_COLUMNS[2][0] };
        String[] selectionArgs = new String[] {
        String.valueOf(programme.getId()) };
        //Query for course ID's in programme
        Cursor cursor = database.query(ProgrammeHasCourseTable.TABLE_NAME,
                column,
                ProgrammeHasCourseTable.TABLE_COLUMNS[1][0] + "= ?",
                selectionArgs,
                null, null, null);

        cursor.moveToFirst();
        System.out.println("GET COURSES BY PROGRAM SIZE " + cursor.getCount());
        ArrayList<Domain> courses = new ArrayList<Domain>();
        while (!cursor.isAfterLast()) {

            System.out.println("SELECT * FROM "
                    + CourseTable.TABLE_NAME
                    + " WHERE " + CourseTable.COLUMN_ID
                    + "='" + cursor.getString(0) + "'");

            Cursor currentCursor = database.rawQuery("SELECT * FROM "
                    + CourseTable.TABLE_NAME
                    + " WHERE " + CourseTable.COLUMN_ID
                    + "='" + cursor.getString(0) + "'",
                    null);
            currentCursor.moveToFirst();
            Course course = cursorToCourse(currentCursor);
            courses.add(course);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return courses;
    }

    /**
     * This function inserts a Topic into the SQLITE database, and if the database
     * record already exists, updates the table instead.
     *
     * @param programme
     * @return primary key id
     */
    public long insertTopic(Topic topic) {

        //Get text/int value fields from Domain and insert into table
        ContentValues values = new ContentValues();
        values.put(TopicTable.COLUMN_ID, topic.getId());
        values.put(TopicTable.COLUMN_NAME, topic.getName());
        values.put(TopicTable.COLUMN_DESCRIPTION, topic.getDescription());
        values.put(TopicTable.COLUMN_UPDATED, getTimeStamp());
        long insertId;
        if(checkRecord(TopicTable.TABLE_NAME, TopicTable.COLUMN_ID, String.valueOf(topic.getId()))){
            System.out.println("updating " + topic.getName());
            insertId = database.update(TopicTable.TABLE_NAME, values,
                    TopicTable.COLUMN_ID + "='" + getTopicId(topic) + "'",
                    null);
        } else {
            System.out.println("inserting " + topic.getName());
            insertId = database.insert(TopicTable.TABLE_NAME, null,
            values);
        }

        return insertId;
    }

        public Topic getTopic(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + TopicTable.TABLE_NAME
                + " WHERE " + TopicTable.COLUMN_NAME
                + " = '"+name+"'", null);
        Topic retVal = cursorToTopic(cursor);
        return retVal;
    }

    public long getTopicId(Topic topic){

        System.out.println(topic.getName());
        Cursor cursor = database.rawQuery("SELECT "
                + "*" + " FROM "
                + TopicTable.TABLE_NAME
                + " WHERE " + TopicTable.COLUMN_NAME
                + " = '"+topic.getName()+"'", null);

        cursor.moveToFirst();
        System.out.println("size " + cursor.getCount());
        long retVal = cursor.getLong(0);
        cursor.close();

        return retVal;
    }

    public long insertArticle(Article article) {
        System.out.println("SUPERDEBUGGGGGGG: " + article.getCategory());
        ContentValues values = new ContentValues();
        values.put(ArticleTable.COLUMN_NAME, article.getName());
        values.put(ArticleTable.COLUMN_UNIQUEID, article.getId());
        values.put(ArticleTable.COLUMN_DATE, article.getDate());
        values.put(ArticleTable.COLUMN_INGRESS, article.getDetail());
        values.put(ArticleTable.COLUMN_TEXT, article.getContent());
        values.put(ArticleTable.COLUMN_AUTHOR, article.getAuthor());
        values.put(ArticleTable.COLUMN_CATEGORY, article.getCategory());
        values.put(ArticleTable.COLUMN_UPDATED, getTimeStamp());
        long insertId;
        if(checkRecord(ArticleTable.TABLE_NAME, ArticleTable.COLUMN_NAME, article.getName())){
//            insertId = database.update(CourseTable.TABLE_NAME, values,
//                    CourseTable.COLUMN_ID + "='" + getCourseId(course) + "'",
//                    null);
            String id[] = {String.valueOf(getArticleId(article))};
            insertId = database.update(ArticleTable.TABLE_NAME, values,
            CourseTable.COLUMN_ID + "=?",
            id);
            System.out.println("updating " + article.getName());

        } else {
            System.out.println("inserting " + article.getName());
            insertId = database.insert(ArticleTable.TABLE_NAME, null,
            values);
        }
        return insertId;
    }

    public void deleteArticle(Article article) {

        if(article.getId() != null){
            String[] id = {String.valueOf(article.getId())};
            database.delete(ArticleTable.TABLE_NAME, ArticleTable.COLUMN_ID
            + "=?", id);
        }
        //Now deletes based on name
        String[] name = {article.getName()};
        System.out.println("article deleted with name: " + name);
        database.delete(ArticleTable.TABLE_NAME, ArticleTable.COLUMN_NAME
            + "=?", name);
    }

    public Article getArticleByName(String name){
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + ArticleTable.TABLE_NAME
                + " WHERE " + ArticleTable.COLUMN_NAME
                + " = '"+name+"'", null);
        Article retVal = cursorToArticle(cursor);
        return retVal;
    }

    public Article getArticleById(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + ArticleTable.TABLE_NAME
                + " WHERE " + ArticleTable.COLUMN_UNIQUEID
                + " = '"+id+"'", null);
        cursor.moveToFirst();
        Article retVal = cursorToArticle(cursor);
        return retVal;
    }

    public long getArticleId(Article article){

        System.out.println(article.getName());
        Cursor cursor = database.rawQuery("SELECT "
                + "*" + " FROM "
                + ArticleTable.TABLE_NAME
                + " WHERE " + ArticleTable.COLUMN_NAME
                + " = '"+article.getName()+"'", null);

        cursor.moveToFirst();
        System.out.println("size " + cursor.getCount());
        long retVal = cursor.getLong(0);
        cursor.close();

        return retVal;
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
        ArrayList<Domain> domainList = (ArrayList<Domain>) user.getUserDomains();

        if (domainList != null) {
            for (int i = 0; i < domainList.size(); i++) {
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
        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }
        
        Programme programme = new Programme();
        int id;
        try {
            id = (int) cursor.getLong(0);
        } catch(CursorIndexOutOfBoundsException ex) {
            Log.e("db", ex.getMessage());
            return null;
        }
        programme.setId(id);
        programme.setProgrammeId(cursor.getString(1));
        programme.setName(cursor.getString(2));
        programme.setDescription(cursor.getString(4));
        programme.setRevision(cursor.getInt(5));
        return programme;
    }

    private Course cursorToCourse(Cursor cursor) {
        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }
        Course course = new Course();
        int id;
        try {
            id = (int) cursor.getLong(1);
        } catch(CursorIndexOutOfBoundsException ex) {
            Log.e("db", ex.getMessage());
            return null;
        }
        course.setId(id);
        course.setCourseId(cursor.getString(1));
        course.setName(cursor.getString(2));
        course.setRevision(cursor.getInt(3));
        return course;
    }
    
    private Document cursorToDocument(Cursor cursor) {
        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }
        Document document = new Document();
        int id = (int) cursor.getLong(0);
        document.setId(id);
        document.setDocumentId(cursor.getString(1));
        document.setName(cursor.getString(2));
        document.setDescription(cursor.getString(4));
        document.setURI(cursor.getString(5));
        return document;
    }

    private Topic cursorToTopic(Cursor cursor) {
        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }
        Topic topic = new Topic();
        int id = (int) cursor.getLong(0);
        topic.setId(id);
        topic.setName(cursor.getString(1));
        return topic;
    }

    private Article cursorToArticle(Cursor cursor) {
        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }
        Article article = new Article();
        int id;
        try {
            id = (int) cursor.getLong(1);
        } catch(CursorIndexOutOfBoundsException ex) {
            Log.e("db", ex.getMessage());
            return null;
        }

        article.setId(id);
        article.setName(cursor.getString(2));
        article.setDate(String.valueOf(getTimeStamp()));
        article.setAuthor(cursor.getString(3));
        article.setDetail(cursor.getString(5));
        article.setContent(cursor.getString(6));
        article.setCategory(cursor.getString(7));

//        System.out.println("DEBUG 0 ---> " + cursor.getString(0));
//        System.out.println("DEBUG 1 ---> " + cursor.getString(1));
//        System.out.println("DEBUG 2 ---> " + cursor.getString(2));
//        System.out.println("DEBUG 3 ---> " + cursor.getString(3));
//        System.out.println("DEBUG 4 ---> " + cursor.getString(4));
//        System.out.println("DEBUG 5 ---> " + cursor.getString(5));
//        System.out.println("DEBUG 6 ---> " + cursor.getString(6));
//        System.out.println("DEBUG 7 ---> " + cursor.getString(7));
//        System.out.println("DEBUG 8 ---> " + cursor.getString(8));
        return article;
    }

    private Video cursorToVideo(Cursor cursor) {
        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }
        Video video = new Video();
        int id = (int) cursor.getLong(0);
        video.setId(id);
        video.setName(cursor.getString(1));
        return video;
    }

    private User cursorToUser(Cursor cursor) {
        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }
        User user = new User();
        int id = (int) cursor.getLong(0);
        user.setId(id);
        user.setName(cursor.getString(1));
        user.setPassword(cursor.getString(2));

        return user;
    }
}
