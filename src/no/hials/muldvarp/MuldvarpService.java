package no.hials.muldvarp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import java.io.File;
import no.hials.muldvarp.asyncutilities.CachedWebRequest;
import no.hials.muldvarp.utility.DownloadTask;

/**
 *
 * @author kristoffer
 */
public class MuldvarpService extends Service {
    public static final String ACTION_PEOPLE_UPDATE       = "no.hials.muldvarp.ACTION_PEOPLE_UPDATE";
    public static final String ACTION_COURSE_UPDATE       = "no.hials.muldvarp.ACTION_COURSE_UPDATE";
    public static final String ACTION_SINGLECOURSE_UPDATE = "no.hials.muldvarp.ACTION_SINGLECOURSE_UPDATE";
    public static final String ACTION_LIBRARY_UPDATE      = "no.hials.muldvarp.ACTION_LIBRARY_UPDATE";
    public static final String ACTION_VIDEOCOURSE_UPDATE  = "no.hials.muldvarp.ACTION_VIDEOCOURSE_UPDATE";
    public static final String ACTION_VIDEOCOURSE_LOAD    = "no.hials.muldvarp.ACTION_VIDEOCOURSE_LOAD";
    public static final String ACTION_VIDEOSTUDENT_UPDATE = "no.hials.muldvarp.ACTION_VIDEOSTUDENT_UPDATE";
    public static final String ACTION_VIDEOSTUDENT_LOAD   = "no.hials.muldvarp.ACTION_VIDEOSTUDENT_LOAD";
    public static final String ACTION_PROGRAMMES_UPDATE   = "no.hials.muldvarp.ACTION_PROGRAMMES_UPDATE";
    public static final String ACTION_PROGRAMMES_LOAD     = "no.hials.muldvarp.ACTION_PROGRAMMES_LOAD";
    public static final String SERVER_NOT_AVAILABLE       = "no.hials.muldvarp.SERVER_NOT_AVAILABLE";    
    
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

    /**
     * Class for clients to access. Because we know this service always runs in
     * the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public MuldvarpService getService() {
            return MuldvarpService.this;
        }
    }
    
    private String getURL(int path) {
        //return getString(R.string.serverPath) + getString(path);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        
        return "http://" + settings.getString("url", "") + ":8080/muldvarp/" + getString(path);
    }
    
    public void requestCourses() {
        new DownloadTask(this,new Intent(ACTION_COURSE_UPDATE),getHttpHeader())
                .execute(getURL(R.string.courseResPath), getString(R.string.cacheCourseList));
    }

    public void requestCourse(Integer id) {
        new DownloadTask(this,new Intent(ACTION_SINGLECOURSE_UPDATE),getHttpHeader())
                .execute(getURL(R.string.courseResPath) + id.toString(),
                         getString(R.string.cacheCourseSingle), id.toString());        
    }
    
    public void requestLibrary(){
        new DownloadTask(this, new Intent(ACTION_LIBRARY_UPDATE),getHttpHeader())
                .execute(getURL(R.string.libraryResPath), getString(R.string.cacheLibraryPath));
    }
    
    public void requestVideos(){
        
        CachedWebRequest asyncCachedWebRequest = new CachedWebRequest(new Intent(ACTION_VIDEOCOURSE_UPDATE),
                                                                                this,
                                                                                getURL(R.string.videoResPath),
                                                                                getString(R.string.cacheVideoCourseList));
        asyncCachedWebRequest.setHeader("Authorization", getHttpHeader());
        asyncCachedWebRequest.startRequest();        
    }
            
    public void requestVideo(Integer id){        
        
    }
    
    public void requestBookmarkedVideos(String user){
        
        
    }
    
    public void requestDownloadedVideos(){
        
    }
    
    public void requestProgrammes(){
        
        CachedWebRequest asyncCachedWebRequest = new CachedWebRequest(new Intent(ACTION_PROGRAMMES_UPDATE),
                                                                                this,
                                                                                getURL(R.string.programmesResPath),
                                                                                getString(R.string.cacheProgrammeList));
        asyncCachedWebRequest.setHeader(getString(R.string.authString), getHttpHeader());
        asyncCachedWebRequest.startRequest();         
    }
        
    public String getHttpHeader() {
        return "Basic " + Base64.encodeToString(loadLogin().getBytes(), Base64.NO_WRAP);
    }
    
    public String loadLogin() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        String username = settings.getString("username", "");
        String password = settings.getString("password", "");
        
        return username + ":" + password; 
    }
    
    
}
