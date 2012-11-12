/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class QuizHasQuestionTable extends MuldvarpTable {    
    public static final String TABLE_NAME = "quiz_has_question";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement not null"},
      {QuizTable.TABLE_NAME + COLUMN_ID, " integer not null"},
      {QuestionTable.TABLE_NAME + COLUMN_ID, " integer not null"}
    };        
}
