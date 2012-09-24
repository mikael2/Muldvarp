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
 * This class is responsible for managing the SQLITE datasource.
 * @author johan
 */
public class MuldvarpDataSource {
    
  // Database fields
  private SQLiteDatabase database;
  private MuldvarpSQLDatabaseHelper dbHelper;
  private String[] allColumns = { MuldvarpSQLDatabaseHelper.COLUMN_ID,
      MuldvarpSQLDatabaseHelper.COLUMN_PROGRAMME_NAME };

  public MuldvarpDataSource(Context context) {
    dbHelper = new MuldvarpSQLDatabaseHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Programme createprogramme(String programme) {
    ContentValues values = new ContentValues();
    values.put(MuldvarpSQLDatabaseHelper.COLUMN_PROGRAMME_NAME, programme);
    long insertId = database.insert(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME, null,
        values);
    Cursor cursor = database.query(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME,
        allColumns, MuldvarpSQLDatabaseHelper.COLUMN_ID + " = " + insertId, null,
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

  public ArrayList<Domain> getAllProgrammes() {
    ArrayList<Domain> programmes = new ArrayList<Domain>();

    Cursor cursor = database.query(MuldvarpSQLDatabaseHelper.TABLE_PROGRAMME,
        allColumns, null, null, null, null, null);

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
    Programme programme = new Programme(cursor.getString(1));
    int id = (int) cursor.getLong(0);
    programme.setId(id);
    programme.setName(cursor.getString(1));
    return programme;
  }
  
  
    
}
