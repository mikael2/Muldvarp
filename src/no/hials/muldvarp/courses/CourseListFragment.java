/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
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
public class CourseListFragment extends Fragment {
    private EditText filterText = null;
    CourseListAdapter adapter = null;
    ArrayList<Course> array = new ArrayList<Course>();
    String url = "http://master.uials.no:8080/muldvarp/resources/course";
    ListView listview;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View fragmentView = inflater.inflate(R.layout.course_list, container, false);
        listview = (ListView)fragmentView.findViewById(R.id.listview);
        
        new DownloadItems().execute(url);
        
        filterText = (EditText)fragmentView.findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
               
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        boolean loggedin = prefs.getBoolean("debugloggedin", false);
        
        if(loggedin == true) {
            listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Course selectedItem = (Course)array.get(position);
                    Intent myIntent = new Intent(view.getContext(), CourseDetailActivity.class);
                    myIntent.putExtra("id", selectedItem.getName());
                    startActivityForResult(myIntent, 0);
                }  
            });
        } else {
            listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Course selectedItem = (Course)array.get(position);
                    Intent myIntent = new Intent(view.getContext(), CoursePublicDetailActivity.class);
                    myIntent.putExtra("id", selectedItem.getName());
                    startActivityForResult(myIntent, 0);
                }  
            }); 
        }
        
        return fragmentView;
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
    
    private class DownloadItems extends AsyncTask<String, Void, ArrayList<Course>> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(CourseListFragment.this.getActivity());
            dialog.setMessage(getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }
        
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
            if(items == null) {
                System.out.println("No items");
            } else {
                array.clear(); // kb note erstatt med lokal array variabel?
                for(Course course : items){
                    Course newCourse = new Course(course.getName(),course.getDetail());
                    if(course.getImageurl() != null) {
                        newCourse.setImageurl(course.getImageurl());
                    }
                    array.add(newCourse);
                }
                dialog.dismiss();
            }
            listview.setAdapter(
                new CourseListAdapter(
                        CourseListFragment.this.getActivity().getApplicationContext(), 
                        R.layout.course_list_item, 
                        R.id.courselisttext, 
                        array,
                        true
                        ));
        
            adapter = (CourseListAdapter)listview.getAdapter();
            
        }
    }
    
    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            adapter.filter(s);
        }

    };
}
