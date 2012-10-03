/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Programme;

/**
 * This class is responsible for managing the SQLITE data source.
 * @author johan
 */
public class MuldvarpDataSource {
    
  // Database fields
  private SQLiteDatabase database;
  private MuldvarpSQLDatabaseHelper dbHelper;  
  
  public MuldvarpDataSource(Context context) {
    dbHelper = new MuldvarpSQLDatabaseHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
    
  }

  public void close() {
    dbHelper.close();
  }
  
  public void createRelation(){
      
  }
  
  public void deleteRelation(){
      
  }

  public Programme createprogramme(String programme) {
    ContentValues values = new ContentValues();
    values.put(MuldvarpSQLDatabaseHelper.COLUMN_NAME, programme);
    values.put(MuldvarpSQLDatabaseHelper.COLUMN_REVISION, 15);
    values.put(MuldvarpSQLDatabaseHelper.COLUMN_UPDATED, programme);
    long insertId = database.insert(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME, null,
        values);
    Cursor cursor = database.query(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME,
        MuldvarpSQLDatabaseHelper.getColumns(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME_COLUMNS), MuldvarpSQLDatabaseHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Programme newprogramme = cursorToProgramme(cursor);
    cursor.close();
    return newprogramme;
  }
    public Programme createProgramme(Programme programme) {
        ContentValues values = new ContentValues();
        values.put(MuldvarpSQLDatabaseHelper.COLUMN_NAME, programme.getName());
        values.put(MuldvarpSQLDatabaseHelper.COLUMN_REVISION, 15);
        values.put(MuldvarpSQLDatabaseHelper.COLUMN_UPDATED, "sadasd");
        long insertId = database.insert(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME, null,
            values);
        Cursor cursor = database.query(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME,
            MuldvarpSQLDatabaseHelper.getColumns(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME_COLUMNS), MuldvarpSQLDatabaseHelper.COLUMN_ID + " = " + insertId, null,
            null, null, null);
        cursor.moveToFirst();
        Programme newprogramme = cursorToProgramme(cursor);
        cursor.close();
    return newprogramme;
  }
  

  public void deleteProgramme(Programme programme) {
    long id = programme.getId();
    System.out.println("programme deleted with id: " + id);
    database.delete(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME, MuldvarpSQLDatabaseHelper.COLUMN_ID
        + " = " + id, null);
  }
  
  public Domain getProgrammeFromDB(String name){
      
      
      return null;
  }
  
  public ArrayList<Domain> getAllProgrammes() {
    ArrayList<Domain> programmes = new ArrayList<Domain>();

    Cursor cursor = database.query(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME,
        MuldvarpSQLDatabaseHelper.getColumns(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME_COLUMNS), null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Programme programme = cursorToProgramme(cursor);
      programmes.add(programme);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return programmes;
  }

  private Programme cursorToProgramme(Cursor cursor) {
    Programme programme = new Programme();
    int id = (int) cursor.getLong(0);
    programme.setId(id);
    programme.setName(cursor.getString(1));
    return programme;
  }
  
  
  
public String[][] getAll(String query){
        String[][] array = null;
        Cursor cursor = null;
        
        try {
            cursor = database.rawQuery(query, null);
            
            if(!cursor.isFirst()){
                cursor.moveToFirst();
            }
            
            int rowCount = cursor.getCount();
            int columnCount = cursor.getColumnCount();
            
            array = new String[rowCount][columnCount];
            
            int index =0;
            
            if(cursor.isFirst()){
                do{
                    for(int j=0;j<columnCount;j++){
                        array[index][j]= cursor.getString(j); 
                        System.out.println(cursor.getColumnName(j)+":"+cursor.getString(j));
                        System.out.println();
                    }
                    index++;
                }while(cursor.moveToNext());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(cursor!=null)
            cursor.close();
            if(database!=null)
            database.close();
        }
        return array;
    }

    
}
