/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import no.hials.muldvarp.domain.Course;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.MuldvarpService.LocalBinder;
import no.hials.muldvarp.R;
import no.hials.muldvarp.utility.DownloadUtilities;

/**
 *
 * @author kristoffer
 */
public class CoursePublicDetailActivity extends Activity {
    MuldvarpService mService;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver     mReceiver;
    boolean mBound = false;
    ProgressDialog dialog;
    Integer id;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_public_detail);
        
        if(savedInstanceState == null) {
            dialog = new ProgressDialog(CoursePublicDetailActivity.this);
            dialog.setMessage(getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            // We use this to send broadcasts within our local process.
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
             // We are going to watch for interesting local broadcasts.
            IntentFilter filter = new IntentFilter();
            filter.addAction(MuldvarpService.ACTION_SINGLECOURSE_UPDATE);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
                    if (intent.getAction().compareTo(MuldvarpService.ACTION_SINGLECOURSE_UPDATE) == 0) {                    
                        System.out.println("Toasting" + intent.getAction());
                        Toast.makeText(context, "Course updated", Toast.LENGTH_LONG).show();
                        new getCourseFromCache().execute(getString(R.string.cacheCourseSingle));
                    } 
                }
            };
            mLocalBroadcastManager.registerReceiver(mReceiver, filter);

            id = getIntent().getIntExtra("id", 0);
            Intent intent = new Intent(this, MuldvarpService.class);
    //        Integer id = 1; // temp greie
    //        System.out.println("Getting course with id " + id);
    //        intent.putExtra("id", id);
    //        intent.putExtra("whatToDo", 2);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            //startService(intent);
        }
        
    }
    
    private class getCourseFromCache extends AsyncTask<String, Void, Course> {
        protected Course doInBackground(String... urls) {
            Course c = new Course();
            try{
                File f = new File(getCacheDir(), urls[0]);
                c = DownloadUtilities.buildGson().fromJson(new FileReader(f), Course.class);
            }catch(Exception ex){
                Logger.getLogger(CourseDetailActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            return c;
        }
        
        @Override
        protected void onPostExecute(Course c) {            
            TextView name=(TextView)findViewById(R.id.name);
            name.setText(c.getName());
            setTitle(c.getName());
        
            TextView desc =(TextView)findViewById(R.id.description);
            desc.append(c.getDetail());
        
            TextView t2 =(TextView)findViewById(R.id.andreting);
            t2.append(c.getDetail());
            dialog.dismiss();
        }
    }
    
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.requestCourse(id);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
