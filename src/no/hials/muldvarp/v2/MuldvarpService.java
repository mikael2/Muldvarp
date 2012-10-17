package no.hials.muldvarp.v2;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.database.MuldvarpDataSource;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.utility.JSONUtilities;
import no.hials.muldvarp.v2.domain.User;
import no.hials.muldvarp.v2.utility.DownloadTask;
import no.hials.muldvarp.v2.utility.ServerConnection;
import org.json.JSONException;

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
    private User user;

    private MuldvarpDataSource mds = new MuldvarpDataSource(this);


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
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
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

    private String getYoutubeUserUploadsURL(String user){

        return getString(R.string.youtubeAPIPath) + "users/" + user + "/uploads?alt=json";
    }

    /**
     * Method login, of class MuldvarpService.
     * This method is used to login to the server remotely. Input the username and password, and the method returns a boolean which is true if the info is correct.
     * Note: pr. 26.09.2012, this method returns true regardless of what info you input.
     * @param name
     * @param password
     * @return authentification
     */
        public boolean login(String name, String password){
        if(checkCredentials(name, password)) {
            user = new User(name, password);
            return true;
        }
        else {
            return false;
        }
    }

        public void reLog(User person){
            user = person;
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
    public User getUser(){
        return user;
    }

    /**
     * Method requested, of class MuldvarpService.
     * This method updates the requested part of the local database from the server.
     * The request argument indicates which part of the database will be updated.
     * @param requested
     */
    public enum DataTypes {ALL, COURSES, VIDEOS, DOCUMENTS, PROGRAMS}

    public synchronized void update(DataTypes type) {
        if(server.checkServer()) {
            switch(type) {
                case ALL:
                    update(DataTypes.COURSES);
                    update(DataTypes.VIDEOS);
                    update(DataTypes.DOCUMENTS);
                    update(DataTypes.PROGRAMS);
                    break;
                case COURSES:
                    new DownloadTask(this,new Intent(ACTION_COURSE_UPDATE), type)
                            .execute(getUrl(R.string.courseResPath));
                    break;
                case VIDEOS:

                    break;
                case DOCUMENTS:
                    new DownloadTask(this,new Intent(ACTION_PROGRAMMES_UPDATE), type)
                            .execute(getUrl(R.string.programmesResPath));
                    break;
                case PROGRAMS:
                    new DownloadTask(this,new Intent(ACTION_PROGRAMMES_UPDATE), type)
                            .execute(getUrl(R.string.programmesResPath));
                    break;
            }
        }
    }

    public String getUrl(int resId) {
        return getString(R.string.serverPath) + getString(resId);
    }

    public void updateDatabase(List<Domain> data){
        //Update the database
    }


//    /**
//     * This function creates an ArrayList of ListItems from a JSONArray represented
//     * by a String. Currently supports Video, Programmes, Course.
//     *
//     * @param jsonString String value of JSONArray
//     * @param type The type of JSONArray represented by it's cache name
//     * @param context The application context to get the cache String name
//     * @return ArrayList<ListItem>
//     */
//    public static ArrayList<ListItem> createListItemsFromJSONString(String jsonString, DataTypes type, Context context) {
//        ArrayList itemList = new ArrayList();
//
//        try {
//            System.out.println("WebResourceUtilities: Printing JSONString: " + jsonString);
//            JSONArray jArray = new JSONArray(jsonString);
//
//            if (type == DataTypes.COURSES
//                    || type.equals(context.getString(R.string.videoBookmarks))
//                    || type.equals(context.getString(R.string.cacheVideoStudentList))
//                    || type.equals(context.getString(R.string.cacheCourseVideoList))) {
//
//                //Video BS her
//                System.out.println("WebresourceUtilities: Array length: " + jArray.length());
//                for (int i = 0; i < jArray.length(); i++) {
//                    JSONObject currentObject = jArray.getJSONObject(i);
//                    itemList.add(new Video(currentObject.getString("id"),
//                            currentObject.getString("videoName"),
//                            currentObject.getString("videoDetail"),
//                            currentObject.getString("videoDescription"),
//                            currentObject.getString("videoType"),
//                            null,
//                            currentObject.getString("videoURI")));
//                }
//
//            } else if (type.equals(context.getString(R.string.cacheCourseList))) {
//
//                //Course BS her
//                for (int i = 0; i < jArray.length(); i++) {
//
//                    JSONObject currentObject = jArray.getJSONObject(i);
//
//                    itemList.add(new Course(currentObject.getInt("id"),
//                            currentObject.getString("name"),
//                            currentObject.getString("detail"),
//                            currentObject.getString("detail"),
//                            "Course",
//                            null));
//
//                }
//            } else if (type.equals(context.getString(R.string.cacheProgrammeList))) {
//
//                //Course BS her
//                for (int i = 0; i < jArray.length(); i++) {
//
//                    JSONObject currentObject = jArray.getJSONObject(i);
//
//                    itemList.add(new Programme(currentObject.getString("id"),
//                            currentObject.getString("name"),
//                            currentObject.getString("detail"),  //no small description
//                            currentObject.getString("detail"),
//                            "Programme", //no type
//                            null)); //no bitmap (yet)
//
//                }
//            } else {
//
//                System.out.println("WebResourceUtilities: It was null. or irrelevant");
//            }
//        } catch (Exception ex) {
//            System.out.println("WebResourceUtilities: Failed to convert String of alleged type " + type );
//            ex.printStackTrace();
//        }
//
//        return itemList;
//    }


    /**
     * Method convertToNewEntity, of class MuldvarpService.
     * This method converts a list of old entity classes(from muldvarp 1.0) to the new entityclasses in muldvarp mk. II.
     * @param datalist
     */
    private ArrayList convertToNewEntity(ArrayList datalist) {
        ArrayList retval = new ArrayList<Domain>();
        if(datalist.get(0) instanceof no.hials.muldvarp.entities.Course){
            for(Object o : datalist){
                no.hials.muldvarp.entities.Course c = (no.hials.muldvarp.entities.Course)o;
                no.hials.muldvarp.v2.domain.Course newCourse = new no.hials.muldvarp.v2.domain.Course(c.getItemName(), c.getSmallDetail(), c.getImageurl());
                retval.add(newCourse);
            }
        }
        else if(datalist.get(0) instanceof no.hials.muldvarp.entities.Programme){
            for(Object o : datalist){
                no.hials.muldvarp.entities.Programme p = (no.hials.muldvarp.entities.Programme)o;
                no.hials.muldvarp.v2.domain.Programme newProgramme = new no.hials.muldvarp.v2.domain.Programme((p.getItemName()));
                newProgramme.setCourses(convertToNewEntity(p.getCoursesInProgramme()));
                newProgramme.setDetail(p.getSmallDetail());
                retval.add(newProgramme);
            }
        }
        else if(datalist.get(0) instanceof no.hials.muldvarp.entities.Person){
            for(Object o : datalist){
                no.hials.muldvarp.entities.Person p = (no.hials.muldvarp.entities.Person)o;
                no.hials.muldvarp.v2.domain.User newPerson = new no.hials.muldvarp.v2.domain.User(p.getName(), null);
                newPerson.setId(p.getId().intValue());
            }
        }
        return retval;
    }
}