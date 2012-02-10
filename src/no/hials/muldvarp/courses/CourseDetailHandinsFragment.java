/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
public class CourseDetailHandinsFragment extends Fragment {
    // Inflate the layout for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.course_handin, container, false);
        
        ListView listview = (ListView)fragmentView.findViewById(R.id.listview);
        
        ArrayList array = new ArrayList();
        
        // testdata
        Handin h = new Handin("Obligatorisk 1");
        array.add(h);
        h = new Handin("Obligatorisk 2");
        array.add(h);
        
        
        listview.setAdapter(
                new CourseDetailHandinListAdapter(
                        this.getActivity().getApplicationContext(),
                        R.layout.course_handin_list_item,
                        R.id.name,
                        array));

        listview.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                    Intent myIntent = new Intent(view.getContext(), CourseDetailActivity.class);
//                    startActivityForResult(myIntent, 0);
                }  
            });
        
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
            dialog = new ProgressDialog(CourseDetailHandinsFragment.this.getActivity());
            dialog.setMessage(getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }
        
        @Override
        protected void onPostExecute(Course c) {
            
            dialog.dismiss();
        }
    }
}


