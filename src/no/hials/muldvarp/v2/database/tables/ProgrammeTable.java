/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class ProgrammeTable extends MuldvarpTable {
    
    public static final String TABLE_NAME = "programme";    
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_UNIQUEID, " text "}, //removed not null req
      {COLUMN_NAME, " text not null "},
      {COLUMN_DESCRIPTION, " text "},
      {COLUMN_REVISION," integer"},
      {COLUMN_UPDATED, " text not null"}
    };
}
