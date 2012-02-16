package no.hials.muldvarp.courses;

import no.hials.muldvarp.domain.Course;
import no.hials.muldvarp.domain.SearchResults;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MainActivity;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.MuldvarpService.LocalBinder;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseActivity extends Activity {
    private boolean showGrid;
    private Fragment currentFragment;
    Fragment CourseListFragment;
    Fragment CourseGridFragment;
    ArrayList<Course> courseList;
    String url = "http://master.uials.no:8080/muldvarp/resources/course";
    
    MuldvarpService mService;
    boolean mBound = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.course_main);
        
        courseList = new ArrayList<Course>();
        CourseListFragment = new CourseListFragment();
        CourseGridFragment = new CourseGridFragment();
        
        Intent intent = new Intent(this, MuldvarpService.class);
        intent.putExtra("whatToDo", 1);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        
        //if (mBound) {
            new getItemsFromCache().execute("CourseCache");
        //}
    }
    
    public void calledBack() {
        
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_activity, menu);
//        MenuItem menuItem = menu.findItem(R.id.actionItem);
//        
//        menuItem.setOnActionExpandListener(new OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                // Do something when collapsed
//                return true;  // Return true to collapse action view
//            }
//
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                // Do something when expanded
//                return true;  // Return true to expand action view
//            }
//        });
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.showgrid:
                System.out.println("Fragment change button pressed");
                if(showGrid == false) {
                    addDynamicFragment(CourseGridFragment);
                    showGrid = true;
                    System.out.println("Showing grid");
                } else {
                    addDynamicFragment(CourseListFragment);
                    showGrid = false;
                    System.out.println("Showing list");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void addDynamicFragment(Fragment fg) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        
        if (currentFragment != null) {
            ft.detach(currentFragment);
        }

        ft.attach(fg);
        ft.add(R.id.course_layout, fg).commit();
        
        currentFragment = fg;
        System.out.println("Fragment changed");
    }
    
    private class getItemsFromCache extends AsyncTask<String, Void, ArrayList<Course>> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(CourseActivity.this);
            dialog.setMessage(getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }
        
        protected ArrayList<Course> doInBackground(String... urls) {
            ArrayList<Course> items = new ArrayList<Course>();
            try {
                File f = new File(getCacheDir(), urls[0]);
                SearchResults result = new Gson().fromJson(new FileReader(f), SearchResults.class);
                items = (ArrayList<Course>)result.course;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CourseActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            return items;
        } 
        
        @Override
        protected void onPostExecute(ArrayList<Course> items) {
            courseList.clear();
            courseList.addAll(items);
            addDynamicFragment(CourseListFragment); // default view
            dialog.dismiss();
        }
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
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
