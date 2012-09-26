package no.hials.muldvarp.v2;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import java.io.InputStream;
import java.io.InputStreamReader;
import no.hials.muldvarp.*;
import no.hials.muldvarp.asyncutilities.CachedWebRequest;
import no.hials.muldvarp.utility.DownloadTask;
import no.hials.muldvarp.utility.DownloadUtilities;
import no.hials.muldvarp.v2.domain.Person_v2;
import no.hials.muldvarp.v2.utility.ServerConnection;

/**
 *
 * @author kristoffer
 */
public class MuldvarpService extends Service {
    public static final String ACTION_PEOPLE_UPDATE       = "no.hials.muldvarp.ACTION_PEOPLE_UPDATE";
    public static final String ACTION_COURSE_UPDATE       = "no.hials.muldvarp.ACTION_COURSE_UPDATE";
    public static final String ACTION_COURSEVIDEO_UPDATE       = "no.hials.muldvarp.ACTION_COURSEVIDEO_UPDATE";
    public static final String ACTION_SINGLECOURSE_UPDATE = "no.hials.muldvarp.ACTION_SINGLECOURSE_UPDATE";
    public static final String ACTION_LIBRARY_UPDATE      = "no.hials.muldvarp.ACTION_LIBRARY_UPDATE";
    public static final String ACTION_VIDEOFAVOURITES_ADD  = "no.hials.muldvarp.ACTION_VIDEOFAVOURITES_ADD";
    public static final String ACTION_VIDEOFAVOURITES_LOAD  = "no.hials.muldvarp.ACTION_VIDEOFAVOURITES_LOAD";
    public static final String ACTION_SINGLEVIDEO_UPDATE  = "no.hials.muldvarp.ACTION_SINGLEVIDEO_UPDATE";
    public static final String ACTION_VIDEOCOURSE_UPDATE  = "no.hials.muldvarp.ACTION_VIDEOCOURSE_UPDATE";
    public static final String ACTION_VIDEOCOURSE_LOAD    = "no.hials.muldvarp.ACTION_VIDEOCOURSE_LOAD";
    public static final String ACTION_VIDEOSTUDENT_UPDATE = "no.hials.muldvarp.ACTION_VIDEOSTUDENT_UPDATE";
    public static final String ACTION_VIDEOSTUDENT_LOAD   = "no.hials.muldvarp.ACTION_VIDEOSTUDENT_LOAD";
    public static final String ACTION_PROGRAMMES_UPDATE   = "no.hials.muldvarp.ACTION_PROGRAMMES_UPDATE";
    public static final String ACTION_PROGRAMMES_LOAD     = "no.hials.muldvarp.ACTION_PROGRAMMES_LOAD";
    public static final String ACTION_UPDATE_FAILED       = "no.hials.muldvarp.ACTION_UPDATE_FAILED";
    public static final String SERVER_NOT_AVAILABLE       = "no.hials.muldvarp.SERVER_NOT_AVAILABLE";
    private Person_v2 user;
    
    
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    int mStartMode;       // indicates how to behave if the service is killed
    boolean mAllowRebind; // indicates whether onRebind should be used
    Integer courseId;
    SharedPreferences preferences;
    LocalBroadcastManager mLocalBroadcastManager;
    ServerConnection server;
    private String header;

    @Override
    public void onCreate() {
        // The service is being created
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        server = new ServerConnection(this);
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
    
    private String getYoutubeUserUploadsURL(String user){
        
        return getString(R.string.youtubeAPIPath) + "users/" + user + "/uploads?alt=json";
    }
    
    public void requestCourses() {
        new DownloadTask(this,new Intent(ACTION_COURSE_UPDATE),getHttpHeader())
                .execute(getURL(R.string.programmeCourseResPath),
                        getString(R.string.cacheCourseList));
    }
    
    public void requestCourses(Integer progid) {
        new DownloadTask(this,new Intent(ACTION_COURSE_UPDATE),getHttpHeader())
                .execute(getURL(R.string.programmeCourseResPath) + progid.toString(),
                        getString(R.string.cacheCourseList));
    }

    public void requestPeople() {
        new DownloadTask(this,new Intent(ACTION_PEOPLE_UPDATE),getHttpHeader())
                .execute(getURL(R.string.peopleResPath), getString(R.string.cachePeopleCache));
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
                                                                                getString(R.string.cacheVideoCourseList),
                                                                                CachedWebRequest.CACHEDWEBREQ_MULDVARP);
        asyncCachedWebRequest.setHeader("Authorization", getHttpHeader());
        asyncCachedWebRequest.startRequest();        
    }
    
    public void requestStudentVideos(){
        
        CachedWebRequest asyncCachedWebRequest = new CachedWebRequest(new Intent(ACTION_VIDEOSTUDENT_UPDATE),
                                                                                this,
                                                                                getYoutubeUserUploadsURL(getString(R.string.youtubeHialsUser)),
                                                                                getString(R.string.cacheVideoStudentList),
                                                                                CachedWebRequest.CACHEDWEBREQ_YOUTUBE);
        asyncCachedWebRequest.startRequest();        
    }
            
    public void requestVideo(String videoID){
        
        CachedWebRequest asyncCachedWebRequest = new CachedWebRequest(new Intent(ACTION_SINGLEVIDEO_UPDATE),
                                                                                this,
                                                                                getURL(R.string.videoResPath) + videoID,
                                                                                getString(R.string.cacheVideoCourseList),
                                                                                CachedWebRequest.CACHEDWEBREQ_MULDVARP);
        asyncCachedWebRequest.setHeader(getString(R.string.authString), getHttpHeader());
        asyncCachedWebRequest.startRequest();
        
    }
    
    public void requestProgrammes(){
        
        CachedWebRequest asyncCachedWebRequest = new CachedWebRequest(new Intent(ACTION_PROGRAMMES_UPDATE),
                                                                                this,
                                                                                getURL(R.string.programmesResPath),
                                                                                getString(R.string.cacheProgrammeList),
                                                                                CachedWebRequest.CACHEDWEBREQ_MULDVARP);
        asyncCachedWebRequest.setHeader(getString(R.string.authString), getHttpHeader());
        asyncCachedWebRequest.startRequest();         
    }
    
    public void requestVideosInCourse(Integer id){
        
        CachedWebRequest asyncCachedWebRequest = new CachedWebRequest(new Intent(ACTION_COURSEVIDEO_UPDATE),
                                                                                this,
                                                                                getURL(R.string.videoCourseResPath) + id,
                                                                                getString(R.string.cacheCourseVideoList),
                                                                                CachedWebRequest.CACHEDWEBREQ_MULDVARP);
        asyncCachedWebRequest.setHeader(getString(R.string.authString), getHttpHeader());
        asyncCachedWebRequest.startRequest();
    }
        
    public String getHttpHeader() {
        return "Basic " + Base64.encodeToString(loadLogin().getBytes(), Base64.NO_WRAP);
    }
    
    public String loadLogin() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        return user.getName() + ":" + user.getPassword(); 
    }
    
    
    //----------------Everything belov ithis lineis new in Muldvarp mk. II----------------------\\
    
    
    /**
     * Method login, of class MuldvarpService.
     * This method is used to login to the server remotely. Input the username and password, and the method returns a boolean which is true if the info is correct.
     * Note: pr. 26.09.2012, this method returns true regardless of what info you input.
     * @param name
     * @param password
     * @return authentification
     */
        public boolean login(String name, String password){
        if(checkCredentials(name, password)){
            user = new Person_v2(name, password);
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * implement security here!
     * @param id
     * @param password
     * @return 
     */
    public boolean checkCredentials(String username, String password){
        return true;
    }
    
   /**
    * Method getUser, of class MuldvarpService.
    * This method returns the user object reference when called.
    * Note that this can only be called when the user is already logged in, or the method will return null.
    * @return Person_v2
    */
    public Person_v2 getUser(){
        return user;
    }
    
    /**
     * Method requested, of class MuldvarpService.
     * This method updates the requested part of the local database from the server.
     * The request argument indicates which part of the database will be updated.
     * 1: The entire database.
     * 2: Courses
     * 3: Videos
     * 4: Documents
     * 5: Programs
     * @param requested 
     */
    public void update(int requested){
        switch(requested){
            case 1:
                break;
                
            case 2:
                if(server.checkServer()){
                    InputStreamReader is = new InputStreamReader(DownloadUtilities.getJSONData(getURL(R.string.programmeCourseResPath),header));
                    //need to handle this inputstream. Put it in the database...
                }
                break;
                
            case 3:
                break;
                
            case 4:
                break;
                
            case 5:
                break;
        }
    }

}
