/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class QuestionHasAlternativeTable extends MuldvarpTable {    
    public static final String TABLE_NAME = "question_has_alternative";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement not null"},
      {QuestionTable.TABLE_NAME + COLUMN_ID, " integer not null"},
      {AlternativeTable.TABLE_NAME + COLUMN_ID, " integer not null"}
    };        
}
