package no.hials.muldvarp.courses;

import no.hials.muldvarp.domain.Course;
import no.hials.muldvarp.domain.SearchResults;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
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
import no.hials.muldvarp.utility.DownloadUtilities;

/**
 *
 * @author kristoffer
 */
public class CourseActivity extends Activity {
    private Fragment currentFragment;
    String listform = "list";
    Fragment CourseListFragment;
    Fragment CourseGridFragment;
    ArrayList<Course> courseList;
    
    MuldvarpService mService;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver     mReceiver;
    boolean mBound;
    ProgressDialog dialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getIntent().getStringExtra("listform") != null) {
            listform = getIntent().getStringExtra("listform");
        }
        System.out.println("CourseActivity onCreate()");
        setContentView(R.layout.course_main);
            CourseListFragment = new CourseListFragment();
            CourseGridFragment = new CourseGridFragment();
        
        if(listform.equals("list")) {
                addDynamicFragment(CourseListFragment);
            } else if(listform.equals("grid")) {
                addDynamicFragment(CourseGridFragment);
            }
        
       // if(savedInstanceState == null) {
            
            dialog = new ProgressDialog(CourseActivity.this);
            dialog.setMessage(getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            // We use this to send broadcasts within our local process.
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
             // We are going to watch for interesting local broadcasts.
            IntentFilter filter = new IntentFilter();
            filter.addAction(MuldvarpService.ACTION_COURSE_UPDATE);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
                    if (intent.getAction().compareTo(MuldvarpService.ACTION_COURSE_UPDATE) == 0) {                    
                        System.out.println("Toasting" + intent.getAction());
                        Toast.makeText(context, "Courses updated", Toast.LENGTH_LONG).show();
                        new getItemsFromCache().execute(getString(R.string.cacheCourseList));
                    } 
                }
            };
            mLocalBroadcastManager.registerReceiver(mReceiver, filter);

            Intent intent = new Intent(this, MuldvarpService.class);
    //        intent.putExtra("whatToDo", 1);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            //startService(intent);
        //}
        
    }
    
    @Override
    public void onStart() {
        super.onStart();
        System.out.println("CourseActivity onStart()");
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
                intent = new Intent(this, CourseActivity.class);
                if(listform.equals("list")) {
                    intent.putExtra("listform", "grid");
                    System.out.println("Showing grid");
                } else if(listform.equals("grid")) {
                    intent.putExtra("listform", "list");
                    System.out.println("Showing list");
                }
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void addDynamicFragment(Fragment fg) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        System.out.println("Changing fragment to " + fg.toString());
        if (currentFragment != null) {
            ft.detach(currentFragment);
            //ft.addToBackStack(null);
        }

        ft.attach(fg);
        ft.add(R.id.course_layout, fg);
        ft.commit();
        
        currentFragment = fg;
        System.out.println("Fragment changed");
    }
    
    private class getItemsFromCache extends AsyncTask<String, Void, ArrayList<Course>> {       
        
        protected ArrayList<Course> doInBackground(String... urls) {
            ArrayList<Course> items = new ArrayList<Course>();
            try {
                File f = new File(getCacheDir(), urls[0]);
                SearchResults result = DownloadUtilities.buildGson().fromJson(new FileReader(f), SearchResults.class);
                items = (ArrayList<Course>)result.course;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CourseActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            return items;
        } 
        
        @Override
        protected void onPostExecute(ArrayList<Course> items) {
//            courseList = new ArrayList<Course>();
//            courseList.addAll(items);
            courseList = items;
            if(listform.equals("list")) {
                CourseListFragment fragment = (CourseListFragment) getFragmentManager().findFragmentById(R.id.course_layout);
                fragment.itemsReady();
            } else if(listform.equals("grid")) {
                CourseGridFragment fragment = (CourseGridFragment) getFragmentManager().findFragmentById(R.id.course_layout);
                //fragment.itemsReady();
            }
            
            
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
            mService.giveMeCourses();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            mBound = false;
        }
    };
    
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("CourseActivity onStop()");
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
