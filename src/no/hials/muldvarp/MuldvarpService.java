package no.hials.muldvarp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import java.io.*;
import no.hials.muldvarp.utility.DownloadUtilities;

/**
 *
 * @author kristoffer
 */
public class MuldvarpService extends Service {
    public static final String ACTION_PEOPLE_UPDATE       = "no.hials.muldvarp.ACTION_PEOPLE_UPDATE";
    public static final String ACTION_COURSE_UPDATE       = "no.hials.muldvarp.ACTION_COURSE_UPDATE";
    public static final String ACTION_SINGLECOURSE_UPDATE = "no.hials.muldvarp.ACTION_SINGLECOURSE_UPDATE";
    
    
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    int mStartMode;       // indicates how to behave if the service is killed
    boolean mAllowRebind; // indicates whether onRebind should be used
    Integer courseId;
    SharedPreferences preferences;
    LocalBroadcastManager mLocalBroadcastManager;

    @Override
    public void onCreate() {
        // The service is being created
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

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

    private String getURL(int path) {
        return getString(R.string.serverPath) + getString(path);
    }
    
    public void requestCourses() {
        new DownloadTask(new Intent(ACTION_COURSE_UPDATE))
                .execute(getURL(R.string.courseResPath), getString(R.string.cacheCourseList));
    }

    public void requestCourse(Integer id) {
        new DownloadTask(new Intent(ACTION_SINGLECOURSE_UPDATE))
                .execute(getURL(R.string.courseResPath) + id.toString(),
                         getString(R.string.cacheCourseSingle));        
    }

    private void cacheThis(Reader json, File f) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            char[] buffer = new char[1024];
            int length;
            while ((length = json.read(buffer)) != -1) {
                writer.write(buffer, 0, length);
            }
            writer.flush();
            writer.close();
            json.close();
        } catch (IOException ex) {
            Log.e("MuldvarpService","Failed to cache " + f.getName(), ex);
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, Void> {
        Intent intent;

        public DownloadTask(Intent intent) {
            this.intent = intent;
        }
                
        
        @Override
        protected Void doInBackground(String... params) {
            File f = new File(getCacheDir(), params[1]);
            if (System.currentTimeMillis() - f.lastModified() > getResources().getInteger(R.integer.cacheTime)) { // funka ditta skikkelig?
                try {
                    cacheThis(new InputStreamReader(DownloadUtilities.getJSONData(params[0])), f);
                } catch (Exception ex) {                    
                    Log.e("MuldvarpService","Failed to cache " + f.getName(), ex);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void retVal) {            
            mLocalBroadcastManager.sendBroadcast(intent);
        }        
    }

    /**
     * 
     */
    private class DownloadCourses extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            File f = new File(getCacheDir(), getString(R.string.cacheCourseList));
            if (System.currentTimeMillis() - f.lastModified() > getResources().getInteger(R.integer.cacheTime)) { // funka ditta skikkelig?
                try {
                    cacheThis(new InputStreamReader(DownloadUtilities.getJSONData(urls[0])), f);
                } catch (Exception ex) {                    
                    Log.e("MuldvarpService","Failed to cache " + f.getName(), ex);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void retVal) {
            
            mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_COURSE_UPDATE));
        }
    }

    
    /**
     * 
     */
    private class DownloadCourse extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            File f = new File(getCacheDir(), getString(R.string.cacheCourseSingle));
            if (System.currentTimeMillis() - f.lastModified() > getResources().getInteger(R.integer.cacheTime)) {
                try {
                    cacheThis(new InputStreamReader(DownloadUtilities.getJSONData(urls[0])), f);
                } catch (Exception ex) {
                    Log.e("MuldvarpService","Failed to cache " + f.getName(), ex);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void retVal) {
            mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_SINGLECOURSE_UPDATE));
        }
    }

    
    /**
     * Class for clients to access. Because we know this service always runs in
     * the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public MuldvarpService getService() {
            return MuldvarpService.this;
        }
    }
}
