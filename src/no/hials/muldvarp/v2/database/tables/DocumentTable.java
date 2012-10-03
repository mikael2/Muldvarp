/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class DocumentTable extends MuldvarpTable {
    
    public static final String TABLE_NAME = "document";   
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_REVISION," integer not null"},
      {COLUMN_DESCRIPTION," text not null"},
      {COLUMN_URI," text not null"},
      {COLUMN_UPDATED, " text not null"}
    };
    
}
