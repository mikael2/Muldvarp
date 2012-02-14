/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import no.hials.muldvarp.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
    
    private class DownloadCourse extends AsyncTask<String, Void, Course> {
        protected Course doInBackground(String... urls) {
            Course c = new Course();
            try{
                Reader json = new InputStreamReader(getJSONData(urls[0]));
                Gson gson = new Gson();
                c = gson.fromJson(json, Course.class);
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
