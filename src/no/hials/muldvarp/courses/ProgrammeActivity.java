/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MainActivity;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.utility.DownloadUtilities;

/**
 *
 * @author kristoffer
 */
public class ProgrammeActivity extends Activity {
    private Fragment currentFragment;
    ProgrammeListFragment ProgrammeListFragment;
    List<Programme> programmeList;
    Boolean isGrid;
    
    MuldvarpService mService;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver     mReceiver;
    boolean mBound;
    ProgressDialog dialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isGrid = getIntent().getBooleanExtra("isGrid", false);
        
        setContentView(R.layout.course_main);
        ProgrammeListFragment = new ProgrammeListFragment();        
        addDynamicFragment(ProgrammeListFragment);
        
        if(savedInstanceState == null) {
            dialog = new ProgressDialog(ProgrammeActivity.this);
            dialog.setMessage(getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            // We use this to send broadcasts within our local process.
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
             // We are going to watch for interesting local broadcasts.
            IntentFilter filter = new IntentFilter();
            filter.addAction(MuldvarpService.ACTION_PROGRAMMES_UPDATE);
            filter.addAction(MuldvarpService.SERVER_NOT_AVAILABLE);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
                    if (intent.getAction().compareTo(MuldvarpService.ACTION_PROGRAMMES_UPDATE) == 0) {                    
                        System.out.println("Toasting" + intent.getAction());
                        Toast.makeText(context, "Programmes updated", Toast.LENGTH_LONG).show();
                        new ProgrammeActivity.getItemsFromCache().execute(getString(R.string.cacheProgrammeList));
                    } else if (intent.getAction().compareTo(MuldvarpService.SERVER_NOT_AVAILABLE) == 0) {
                        Toast.makeText(context, "Server not available", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }
            };
            mLocalBroadcastManager.registerReceiver(mReceiver, filter);

            Intent intent = new Intent(this, MuldvarpService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
        
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
                intent = new Intent(this, ProgrammeActivity.class);
                if(!isGrid) {
                    intent.putExtra("isGrid", true);
                } else {
                    intent.putExtra("isGrid", false);
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
    
    private class getItemsFromCache extends AsyncTask<String, Void, List<Programme>> {       
        
        protected List<Programme> doInBackground(String... urls) {
            List<Programme> items = null;
            try {
                File f = new File(getCacheDir(), urls[0]);
                Type collectionType = new TypeToken<List<Programme>>(){}.getType();
                items = DownloadUtilities.buildGson().fromJson(new FileReader(f), collectionType);                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProgrammeActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            return items;
        } 
        
        @Override
        protected void onPostExecute(List<Programme> items) {
            programmeList = items;
            ProgrammeListFragment.itemsReady();
            
            dialog.dismiss();
        }
    }

    public List<Programme> getProgrammeList() {
        return programmeList;
    }
    
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.requestProgrammes();
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
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public Boolean getIsGrid() {
        return isGrid;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocalBroadcastManager != null)
            mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }
}
