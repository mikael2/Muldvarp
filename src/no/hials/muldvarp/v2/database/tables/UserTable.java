/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class UserTable extends MuldvarpTable {
   
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_PASSWORD = "password";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_PASSWORD," text not null"},
    };
    
}
