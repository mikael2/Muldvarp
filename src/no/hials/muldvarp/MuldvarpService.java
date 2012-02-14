/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import no.hials.muldvarp.courses.Course;
import no.hials.muldvarp.courses.SearchResults;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author kristoffer
 */
public class MuldvarpService extends Service {
    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    
    private ServiceHandler mServiceHandler;
    
    String server = "http://master.uials.no:8080/muldvarp/";
    String course = server + "resources/course";
    
    ArrayList<Course> courses = new ArrayList<Course>();
    
    
    SharedPreferences preferences;
    
    // Handler that receives messages from the thread
      private final class ServiceHandler extends Handler {
          public ServiceHandler(Looper looper) {
              super(looper);
          }
          @Override
          public void handleMessage(Message msg) {
              
              stopSelf(msg.arg1);
          }
      }
    
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

//              Message msg = mServiceHandler.obtainMessage();
//              msg.arg1 = startId;
//              mServiceHandler.sendMessage(msg);
        
        return mStartMode;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
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
    
    public InputStream getJSONData(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return data;
    }
    
    private class DownloadCourses extends AsyncTask<String, Void, ArrayList<Course>> {
        ProgressDialog dialog;
        @Override
//        protected void onPreExecute() {
//            dialog = new ProgressDialog(CourseActivity.this);
//            dialog.setMessage(getString(R.string.loading));
//            dialog.setIndeterminate(true);
//            dialog.setCancelable(false);
//            dialog.show();
//        }
        
        protected ArrayList<Course> doInBackground(String... urls) {
            ArrayList<Course> items = new ArrayList<Course>();
            try{
                Reader json = new InputStreamReader(getJSONData(urls[0]));
                Gson gson = new Gson();
                SearchResults result = gson.fromJson(json, SearchResults.class);
                items = (ArrayList<Course>)result.course;
            }catch(Exception ex){
                ex.printStackTrace();
                dialog.setCancelable(true);
                dialog.setMessage(getString(R.string.cannot_connect));
            }
            return items;
        } 
        
        @Override
        protected void onPostExecute(ArrayList<Course> items) {
            courses = items;
            dialog.dismiss();
        }
    }
}
