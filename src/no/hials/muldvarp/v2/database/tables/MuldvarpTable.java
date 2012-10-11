/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.database.tables;

/**
 *  This class describes a general table for the database in the Muldvarp application.
 *  This class also includes methods for getting table creation strings, and so on.
 * 
 * @author johan
 */
public class MuldvarpTable {
        
    //Fields
    public static final String TABLE_NAME = "muldvarptable";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_UNIQUEID = "uniqueid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_REVISION = "revision";
    public static final String COLUMN_UPDATED = "updated";    
    
    public static final String[][] TABLE_COLUMNS = {
    };
            
    /**
     * This method returns the field names of a table.
     * 
     * @param tableColumns
     * @return 
     */
    public static String[] getColumns(String[][] tableColumns){
        
        String[] retVal = new String[tableColumns.length];
        
        for (int i = 0; i < tableColumns.length; i++) {
            retVal[i] = tableColumns[i][0];
        }
        
        return retVal;
    }
        
    /**
     * This function creates a SQL-creation statement based on a String name for the
     * table, and a multi-dimensional array of String values containing field names
     * and their attributes.
     * 
     * @param tableName
     * @param fields
     * @return 
     */
    public static String getTableCreationString(String tableName, String[][] fields){
        
        String retVal = "CREATE TABLE " + tableName + "(";
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                retVal += fields[i][j];                
            }
            if(i < (fields.length -1)){
                retVal +=",";
            }
        }
        retVal += ");";
        
        return retVal;
    }
    
    /**
     * This function creates a SQL-creation statement based on a String name for the
     * table, and a multi-dimensional array of String values containing field names
     * and their attributes.
     * 
     * @return 
     */
    public static String getTableCreationString(){
        
        String retVal = "CREATE TABLE " + TABLE_NAME + "(";
        for (int i = 0; i < TABLE_COLUMNS.length; i++) {
            for (int j = 0; j < TABLE_COLUMNS[i].length; j++) {
                retVal += TABLE_COLUMNS[i][j];                
            }
            if(i < (TABLE_COLUMNS.length -1)){
                retVal +=",";
            }
        }
        retVal += ");";
        
        return retVal;
    }
}
