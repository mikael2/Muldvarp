/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *
 * @author johan
 */
public class ArticleTable extends MuldvarpTable {

    public static final String TABLE_NAME = "article";
    public static final String COLUMN_INGRESS = "ingress";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_CATEGORY = "ingress";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_DATE = "date";
    public static final String[][] TABLE_COLUMNS = {
      {COLUMN_ID, " integer primary key autoincrement"},
      {COLUMN_NAME, " text not null "},
      {COLUMN_DATE, " text "},
      {COLUMN_AUTHOR, " text "},
      {COLUMN_INGRESS, " text "},
      {COLUMN_TEXT, " text "},
      {COLUMN_CATEGORY, " text "},
      {COLUMN_UPDATED, " text not null"}
    };

}
