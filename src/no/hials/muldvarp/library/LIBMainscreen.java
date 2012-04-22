package no.hials.muldvarp.library;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.courses.CourseActivity;
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
    BroadcastReceiver mReceiver;
    boolean mBound = false;
    ProgressDialog dialog;
    private LIBmainAll all;
    private LIBmainFavourites favourite;

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
        pager.addTab("All", LIBmainAll.class, null);
        all = (LIBmainAll) pager.getFragmentInTab(0);
        pager.addTab("Favourites", LIBmainFavourites.class, null);
        favourite = (LIBmainFavourites) pager.getFragmentInTab(1);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.menu_search);

        dialog = new ProgressDialog(LIBMainscreen.this);
        dialog.setMessage(getString(R.string.loading));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

//             We use this to send broadcasts within our local process.
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        // We are going to watch for interesting local broadcasts.
        IntentFilter filter = new IntentFilter();
        filter.addAction(MuldvarpService.ACTION_LIBRARY_UPDATE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
                if (intent.getAction().compareTo(MuldvarpService.ACTION_LIBRARY_UPDATE) == 0) {
                    System.out.println("Toasting" + intent.getAction());
                    Toast.makeText(context, "Library updated", Toast.LENGTH_SHORT).show();
                    new GetItemsFromCache().execute(getString(R.string.cacheLibraryPath));
                }
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);

        Intent intent = new Intent(this, MuldvarpService.class);
        //        intent.putExtra("whatToDo", 1);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        //startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
        unbindService(mConnection);
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
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.requestLibrary();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            mBound = false;
        }
    };

    private class GetItemsFromCache extends AsyncTask<String, Void, List<LibraryItem>> {

        protected List<LibraryItem> doInBackground(String... urls) {
            List<LibraryItem> items = null;
            try {
                File f = new File(getCacheDir(), urls[0]);
                Type collectionType = new TypeToken<List<LibraryItem>>() {}.getType();
                items = DownloadUtilities.buildGson().fromJson(new FileReader(f), collectionType);
            } catch (FileNotFoundException ex) {
                System.out.println("ERROR: an error occured in the doInBackground method");
                Logger.getLogger(CourseActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            return items;

        }

        @Override
        protected void onPostExecute(List<LibraryItem> items) {
            if(LIBMainscreen.this.isFinishing() == false) {
                FragmentPager pager = (FragmentPager) findViewById(R.id.pager);
                ((LIBmainAll) pager.getFragmentInTab(0)).itemsReady(items != null ? items : new ArrayList());
                //all.itemsReady(items);
                dialog.dismiss();
            }
        }
    }
}
