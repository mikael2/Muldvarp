/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class UserHasCoursesTable extends MuldvarpTable {
    
    public static final String TABLE_NAME = "user_has_course";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement not null"},
      {UserTable.TABLE_NAME + COLUMN_ID, " integer not null"},
      {CourseTable.TABLE_NAME + COLUMN_ID, " integer not null"}
    };
}
