/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class QuizTable extends MuldvarpTable {
    
    public static final String TABLE_NAME = "quiz";
    public static final String COLUMN_TYPE = "type";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key "},
      {COLUMN_NAME, " text not null "},
      {COLUMN_TYPE," text not null"},
      {COLUMN_DESCRIPTION," text "},
      {COLUMN_UPDATED, " text not null"}
    };
    
}
