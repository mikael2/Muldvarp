/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.R.array;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author kristoffer
 */
public class CoursePublicDetailActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_public_detail);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        id = "1"; // temp greie
        System.out.println("Getting course with id " + id);
        
        String url = "http://master.uials.no:8080/muldvarp/resources/course/" + id;
        new DownloadCourse().execute(url);
        
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
            dialog = new ProgressDialog(CoursePublicDetailActivity.this);
            dialog.setMessage(getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }
        
        @Override
        protected void onPostExecute(Course c) {
            TextView name=(TextView)findViewById(R.id.name);
            name.setText(c.getName());
        
            TextView desc =(TextView)findViewById(R.id.description);
            desc.append(c.getDetail());
        
            TextView t2 =(TextView)findViewById(R.id.andreting);
            t2.append(c.getDetail());
            dialog.dismiss();
        }
    }
}
