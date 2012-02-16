/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author kristoffer
 */
public class MuldvarpService extends Service {
    int mStartMode;       // indicates how to behave if the service is killed
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    boolean mAllowRebind; // indicates whether onRebind should be used
        
    String server = "http://master.uials.no:8080/muldvarp/";
    String course = server + "resources/course";
    String courseCache = "CourseCache";
        
    SharedPreferences preferences;
    
    @Override
    public void onCreate() {
        // The service is being created
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        
        
        Bundle extra = intent.getExtras();
        if(extra != null){
            switch (extra.getInt("whatToDo")) {
                case 1: // get all courses
                    new DownloadCourses().execute(course);
                    break;
                case 2: // get single course
                    break;
            }
            
        }

        return mStartMode;
    }
    
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public MuldvarpService getService() {
            return MuldvarpService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return mAllowRebind;
    }
    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }
    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
    }
    
    private InputStream getJSONData(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception ex) {
            Logger.getLogger(MuldvarpService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    private class DownloadCourses extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... urls) {
            try{
                cacheThis(
                        new InputStreamReader(getJSONData(urls[0])), 
                        new File(getCacheDir(), courseCache));
                return true;
            }catch(Exception ex){
                Logger.getLogger(MuldvarpService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        } 
        
        @Override
        protected void onPostExecute(Boolean retVal) {
            // callback here
        }
    }
    
    private void cacheThis(Reader json, File f) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            char[] buffer = new char[1024];
            int length;
            while((length = json.read(buffer)) != -1) {
                writer.write(buffer, 0, length);
            }
            writer.flush();
            writer.close();
            json.close();
        } catch (IOException ex) {
            Logger.getLogger(MuldvarpService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
