/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class CourseHasDocumentTable extends MuldvarpTable {
    
    public static final String TABLE_NAME = "course_has_document";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement not null"},
      {CourseTable.TABLE_NAME + COLUMN_ID, " integer not null"},
      {DocumentTable.TABLE_NAME + COLUMN_ID, " integer not null"}
    };


    
    
}
