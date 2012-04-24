package no.hials.muldvarp.utility;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.asyncutilities.AsyncFileIOUtility;
import no.hials.muldvarp.asyncutilities.WebResourceUtilities;
import no.hials.muldvarp.entities.ListItem;

/**
 * This class extends AsyncTask, and is tasked with setting a bookmark tied to a User.
 * The user is represented by a string in the filename for now. The bookmarks file is
 * stored in the private file directory belonging to the application. The filename is a combination
 * of the user it belongs to, and the type of data.
 * 
 * @author johan
 */
public class BookMarkTask extends AsyncTask<String, Void, Boolean> {
    Intent successIntent;
    Intent failedIntent; //NYI
    Context applicationContext;
    ListItem listItemToAdd;

    public BookMarkTask(Context ctx, Intent intent, ListItem listItem) {
        this.successIntent = intent;
        this.applicationContext = ctx;
        this.listItemToAdd = listItem;
        //Hardcoded fail 
        this.failedIntent = new Intent("BookMarkTaskFailedTempNeedsRemoval");
    }

    /**
     * 
     * 
     * @param params String User, String type
     * @return boolean
     */
    @Override
    protected Boolean doInBackground(String... params) {       
        
        //Get file from supplied user plus the application context's private file dir
        File file = new File(applicationContext.getFilesDir().getPath() + params[0], params[1]);
        System.out.println("bookmark filepath" + file.getPath());
        
        //Check if file is readable, and that the File represents a file and not anything else.
        //If there already exists a list of bookmarks
        if (file.isFile()) { 
            
            ArrayList<ListItem> listItems = WebResourceUtilities.createListItemsFromJSONString(readFile(file), params[1], applicationContext);
            writeFile(file, WebResourceUtilities.createJSONStringFromListItem(addListItemToBookmarks(listItems), params[1], applicationContext));
            
            System.out.println("BookMarkTask: Successfully wrote bookmark.");
            return true;       
            
        } else {
            
            ArrayList listItems = new ArrayList<ListItem>();
            writeFile(file, WebResourceUtilities.createJSONStringFromListItem(addListItemToBookmarks(listItems), params[1], applicationContext));
            
            System.out.println("BookMarkTask: Successfully wrote bookmark.");
            return true;
        } 
    }

    @Override
    protected void onPostExecute(Boolean retVal) {
        
        if (retVal) {
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(successIntent);
        } else {
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(failedIntent);
        }
    }
    
    public ArrayList<ListItem> addListItemToBookmarks(ArrayList<ListItem> listItems){

        Set<ListItem> bookmarkSet = new HashSet<ListItem>(listItems);
        
        if(bookmarkSet.add(listItemToAdd)){
            
            System.out.println("BookMarkTask: Successfully added bookmark.");
        } else {
            
            System.out.println("BookMarkTask: Failed to add bookmark, possibly because of duplicate.");
        }
        System.out.println("BOOKMARKSET SIZE" + bookmarkSet.size());
        
        return new ArrayList<ListItem>(bookmarkSet);
    }
    
    
    public String readFile(File file){
        
        String retVal = "";
        try {
                AsyncFileIOUtility asyncFileIO = new AsyncFileIOUtility();
                asyncFileIO.readFile(file);
                retVal = asyncFileIO.readParallel();
                System.out.println("BookMarkTask readfile" + retVal );
            } catch (IOException ex) {
                Logger.getLogger(BookMarkTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return retVal;
    }
    
    public boolean writeFile(File file, String string){
        
        try {
            AsyncFileIOUtility asyncFileIO = new AsyncFileIOUtility();
            asyncFileIO.writeString(file.getParent(), file.getName(), string);
            asyncFileIO.writeParalell();
                        
            return true;
        } catch (IOException ex) {
            Logger.getLogger(BookMarkTask.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
