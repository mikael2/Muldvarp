/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class QuestionTable extends MuldvarpTable {    
    public static final String TABLE_NAME = "question";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SHUFFLE = "shuffle";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key "},
      {COLUMN_NAME, " text not null "},
      {COLUMN_TYPE," text not null"},
      {COLUMN_SHUFFLE," integer not null"},
      {COLUMN_UPDATED, " text not null"}
    };    
}
