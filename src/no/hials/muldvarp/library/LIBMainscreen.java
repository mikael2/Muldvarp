/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.app.ActionBar.Tab;
import android.app.*;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.courses.CourseActivity;
import no.hials.muldvarp.desktop.DesktopFragment;
import no.hials.muldvarp.directory.DirectoryCampus;
import no.hials.muldvarp.directory.DirectoryPeople;
import no.hials.muldvarp.entities.LibraryItem;
import no.hials.muldvarp.utility.DownloadUtilities;
import no.hials.muldvarp.view.FragmentPager;

/**
 *
 * @author Nospherus
 */
public class LIBMainscreen extends FragmentActivity {
    
    Fragment activeFragment = null;
    MuldvarpService mService;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver     mReceiver;
    boolean mBound = false;
    ProgressDialog dialog;
    private List<LibraryItem> libraryList;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_main);
        
        ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        FragmentPager pager = (FragmentPager) findViewById(R.id.pager);
        pager.initializeAdapter(getSupportFragmentManager(), bar);     
        pager.addTab("All", LIBmainAll.class);
        pager.addTab("Favourites", LIBmainFavourites.class);
        
        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.menu_search);
//
//        bar.addTab(bar.newTab()
//                .setText("All")
//                .setTabListener(new TabListener<no.hials.muldvarp.library.LIBmainAll>(
//                        this, "all", no.hials.muldvarp.library.LIBmainAll.class)));
//        bar.addTab(bar.newTab()
//                .setText("Downloaded")
//                .setTabListener(new TabListener<no.hials.muldvarp.library.LIBmainDownloaded>(
//                        this, "downloaded", no.hials.muldvarp.library.LIBmainDownloaded.class)));
//         bar.addTab(bar.newTab()
//                .setText("Favoirites")
//                .setTabListener(new TabListener<no.hials.muldvarp.library.LIBmainFavourites>(
//                        this, "favourites", no.hials.muldvarp.library.LIBmainFavourites.class)));
//
//
//        if (savedInstanceState != null) {
//            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
//        }
        
        //searchView.setIconifiedByDefault(false);
        
        
        
//        dialog = new ProgressDialog(LIBMainscreen.this);
//            dialog.setMessage(getString(R.string.loading));
//            dialog.setIndeterminate(true);
//            dialog.setCancelable(false);
//            dialog.show();

            // We use this to send broadcasts within our local process.
//            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
//             // We are going to watch for interesting local broadcasts.
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(MuldvarpService.ACTION_COURSE_UPDATE);
//            mReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
//                    if (intent.getAction().compareTo(MuldvarpService.ACTION_LIBRARY_UPDATE) == 0) {                    
//                        System.out.println("Toasting" + intent.getAction());
//                        Toast.makeText(context, "Library updated", Toast.LENGTH_LONG).show();
//                        new LIBMainscreen.getItemsFromCache().execute(getString(R.string.cacheLibraryPath));
//                    } 
//                }
//            };
//            mLocalBroadcastManager.registerReceiver(mReceiver, filter);
//
//            Intent intent = new Intent(this, MuldvarpService.class);
//    //        intent.putExtra("whatToDo", 1);
//            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//            //startService(intent);
        }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, LIBmainAll.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
            default:
            return super.onOptionsItemSelected(item);
    }
}
    
    
      
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.library_activity, menu);
    return true;
}
    
    

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
//    }
//
//    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
//        private final Activity mActivity;
//        private final String mTag;
//        private final Class<T> mClass;
//        private final Bundle mArgs;
//        private Fragment mFragment;
//
//        public Fragment getActiveFragment(){
//            return mFragment;
//        }
//        
//        public TabListener(Activity activity, String tag, Class<T> clz) {
//            this(activity, tag, clz, null);
//        }
//
//        public TabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
//            mActivity = activity;
//            mTag = tag;
//            mClass = clz;
//            mArgs = args;
//
//            // Check to see if we already have a fragment for this tab, probably
//            // from a previously saved state.  If so, deactivate it, because our
//            // initial state is that a tab isn't shown.
//            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
//            if (mFragment != null && !mFragment.isDetached()) {
//                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
//                ft.detach(mFragment);
//                ft.commit();
//            }
//        }
//
//        public void onTabSelected(Tab tab, FragmentTransaction ft) {
//            if (mFragment == null) {
//                mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
//                ft.add(android.R.id.content, mFragment, mTag);
//            } else {
//                ft.attach(mFragment);
//            }
//        }
//
//        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//            if (mFragment != null) {
//                ft.detach(mFragment);
//            }
//        }
//
//        public void onTabReselected(Tab tab, FragmentTransaction ft) {
//            Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private ServiceConnection mConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName className,
//                IBinder service) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
//            MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
//            mService = binder.getService();
//            mBound = true;
//            mService.requestCourses();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            mService = null;
//            mBound = false;
//        }
//    };
//    
//        private class getItemsFromCache extends AsyncTask<String, Void, List<LibraryItem>> {       
//        
//        protected List<LibraryItem> doInBackground(String... urls) {
//            //ArrayList<Course> items = new ArrayList<Course>();
//            List<LibraryItem> items = null;
//            try {
//                File f = new File(getCacheDir(), urls[0]);
//                Type collectionType = new TypeToken<List<LibraryItem>>(){}.getType();
//                items = DownloadUtilities.buildGson().fromJson(new FileReader(f), collectionType);
//                //items = (ArrayList<Course>)result.course;
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(CourseActivity.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return items;
//        } 
//        }
//        
//        protected void onPostExecute(List<LibraryItem> items) {
////            courseList = new ArrayList<Course>();
////            courseList.addAll(items);
//            libraryList = items;
////            .itemsReady();
//            dialog.dismiss();
//        }
    }
