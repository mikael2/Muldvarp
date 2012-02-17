/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import no.hials.muldvarp.utility.TabListener;
import no.hials.muldvarp.domain.Course;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import java.io.File;
import java.io.FileReader;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.utility.DownloadUtilities;

/**
 *
 * @author kristoffer
 */
public class CourseDetailActivity extends Activity {
    Course activeCourse;
    ProgressDialog dialog;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver     mReceiver;
    String SingleCourseCache = "SingleCourseCache";
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);
        
        dialog = new ProgressDialog(CourseDetailActivity.this);
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
                    new getCourseFromCache().execute(SingleCourseCache);
                } 
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
        
        Intent intent = new Intent(this, MuldvarpService.class);
        Integer id = 1; // temp greie
        System.out.println("Getting course with id " + id);
        intent.putExtra("id", id);
        intent.putExtra("whatToDo", 2);
        //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    
    private class getCourseFromCache extends AsyncTask<String, Void, Course> {
        protected Course doInBackground(String... urls) {
            Course c = new Course();
            try{
//                String url = "http://master.uials.no:8080/muldvarp/resources/course/1";
//                Reader json = new InputStreamReader(DownloadUtilities.getJSONData(url));
//                c = DownloadUtilities.buildGson().fromJson(json, Course.class);
                
                File f = new File(getCacheDir(), urls[0]);
                c = DownloadUtilities.buildGson().fromJson(new FileReader(f), Course.class);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return c;
        }
        
        @Override
        protected void onPostExecute(Course c) {            
            activeCourse = c;
            ActionBar actionBar = getActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.setDisplayShowTitleEnabled(false);  
            actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE); // ??

            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(R.string.work)
               .setTabListener(new TabListener<CourseDetailWorkFragment>(
               CourseDetailActivity.this, "Tema", CourseDetailWorkFragment.class));
            actionBar.addTab(tab);        

            tab = actionBar.newTab();
            tab.setText(R.string.handins)
               .setTabListener(new TabListener<CourseDetailHandinsFragment>(
               CourseDetailActivity.this, "Obligatorisk", CourseDetailHandinsFragment.class));
            actionBar.addTab(tab);

            tab = actionBar.newTab();
            tab.setText(R.string.exam)
               .setTabListener(new TabListener<CourseDetailExamFragment>(
               CourseDetailActivity.this, "Eksamen", CourseDetailExamFragment.class));
            actionBar.addTab(tab);
            dialog.dismiss();
        }
    }

    public Course getActiveCourse() {
        return activeCourse;
    }
}
