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
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStreamReader;
import java.io.Reader;
import no.hials.muldvarp.R;
import no.hials.muldvarp.utility.DownloadUtilities;

/**
 *
 * @author kristoffer
 */
public class CourseDetailActivity extends Activity {
    Course activeCourse;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);
        
        String id = "1"; // temp greie
        System.out.println("Getting course with id " + id);
        
        String url = "http://master.uials.no:8080/muldvarp/resources/course/" + id;
        new DownloadCourse().execute(url);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    
    private class DownloadCourse extends AsyncTask<String, Void, Course> {
        protected Course doInBackground(String... urls) {
            Course c = new Course();
            try{
                Reader json = new InputStreamReader(DownloadUtilities.getJSONData(urls[0]));
                c = DownloadUtilities.buildGson().fromJson(json, Course.class);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return c;
        }

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(CourseDetailActivity.this);
            dialog.setMessage(getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
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
