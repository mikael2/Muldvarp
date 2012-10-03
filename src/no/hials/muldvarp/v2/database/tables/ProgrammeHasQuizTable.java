/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class ProgrammeHasQuizTable extends MuldvarpTable {
    
    public static final String TABLE_NAME = "programme_has_quiz";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement not null"},
      {ProgrammeTable.TABLE_NAME + COLUMN_ID, " integer not null"},
      {QuizTable.TABLE_NAME + COLUMN_ID, " integer not null"}
    };
    
}