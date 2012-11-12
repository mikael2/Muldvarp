/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class TopHasQuizTable extends MuldvarpTable {    
    public static final String TABLE_NAME = "top_has_quiz";
    public static final String[][] TABLE_COLUMNS = {
      {QuizTable.TABLE_NAME + COLUMN_ID, " integer primary key not null"}
    };        
}
