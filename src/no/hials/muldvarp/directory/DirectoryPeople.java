package no.hials.muldvarp.directory;

import android.app.ProgressDialog;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import no.hials.muldvarp.domain.Person;
import no.hials.muldvarp.utility.DownloadUtilities;

/**
 *
 * @author Lena
 */
public class DirectoryPeople extends ListFragment {

    List<Person> personList;
    MuldvarpService mService;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mReceiver;
    boolean mBound;
    ProgressDialog dialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // ToDo add your GUI initialization code here  

        this.getListView().setTextFilterEnabled(true);


        // We use this to send broadcasts within our local process.
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        // We are going to watch for interesting local broadcasts.
        IntentFilter filter = new IntentFilter();
        filter.addAction(MuldvarpService.ACTION_PEOPLE_UPDATE);
        filter.addAction(MuldvarpService.SERVER_NOT_AVAILABLE);
        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
                if (intent.getAction().compareTo(MuldvarpService.ACTION_PEOPLE_UPDATE) == 0) {
                   new GetPeopleFromCache().execute(getString(R.string.cachePeopleCache));
                } else if (intent.getAction().compareTo(MuldvarpService.SERVER_NOT_AVAILABLE) == 0) {
                    Toast.makeText(context, "Server not available", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);

        Intent intent = new Intent(getActivity(), MuldvarpService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), l.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        Person person = (Person) l.getItemAtPosition(position);                
        ((DirectoryActivity)getActivity()).viewPerson(person);
    }

    class GetPeopleFromCache extends AsyncTask<String, Void, List<Person>> {

        protected List<Person> doInBackground(String... urls) {
            List<Person> items = null;
            
            try {
                File f = new File(getActivity().getCacheDir(), urls[0]);
                Type collectionType = new TypeToken<List<Person>>() {}.getType();
                items = DownloadUtilities.buildGson().fromJson(new FileReader(f),
                        collectionType);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DirectoryPeople.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
             
            return items;
        }

        @Override
        protected void onPostExecute(List<Person> people) {
            Toast.makeText(getActivity(), "People exists!", Toast.LENGTH_LONG).show();
            if (people != null) {
                setListAdapter(new ArrayAdapter<Person>(getActivity(),
                        R.layout.directory_people, R.id.people, people));
            }
//            courseList.addAll(items);
            //personList = items;
            //CourseListFragment.itemsReady();
            //dialog.dismiss();
        }
    }

    public List<Person> getCourseList() {
        return personList;
    }
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.requestPeople();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unbind from the service
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }

        if (mLocalBroadcastManager != null) {
            mLocalBroadcastManager.unregisterReceiver(mReceiver);
        }

    }
}
