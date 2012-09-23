/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is responsible for creating the SQLITE database.
 * 
 * @author johan
 */
public class MuldvarpSQLDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_PROGRAMMES = "programmes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PROGRAMME = "programme";

    private static final String DATABASE_NAME = "muldvarp.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
        + TABLE_PROGRAMMES + "(" + COLUMN_ID
        + " integer primary key autoincrement, " + COLUMN_PROGRAMME
        + " text not null);";

    public MuldvarpSQLDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MuldvarpSQLDatabaseHelper.class.getName(),
            "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMMES);
        onCreate(db);
    }

}
