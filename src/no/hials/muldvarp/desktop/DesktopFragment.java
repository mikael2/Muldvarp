package no.hials.muldvarp.desktop;

import android.app.Fragment;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.MuldvarpService.LocalBinder;
import no.hials.muldvarp.R;
import no.hials.muldvarp.courses.CourseActivity;
import no.hials.muldvarp.directory.DirectoryActivity;
import no.hials.muldvarp.library.LIBMainscreen;
import no.hials.muldvarp.news.NewsActivity;
import no.hials.muldvarp.video.VideoMainActivitySwipe;

/**
 *
 * @author mikael
 */

public class DesktopFragment extends Fragment {
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver     mReceiver;
    
    MuldvarpService service;
    boolean bound;
    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been established,
            // giving us the object we can use to interact with the service. 
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            DesktopFragment.this.service = binder.getService();
            bound = true;
            System.out.println("We are connected");
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            service = null;
            bound = false;
        }
    };
    
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View retVal = inflater.inflate(R.layout.desktop_fragment, container, false);        
        
        //Directory, News NYI, redirects to Video for now
        createButton(retVal,R.id.directorybutton, DirectoryActivity.class);        
        createButton(retVal,R.id.newsbutton,      NewsActivity.class);          
        createButton(retVal,R.id.coursesbutton,   CourseActivity.class);        
        createButton(retVal,R.id.libraryButton,   LIBMainscreen.class);        
        createButton(retVal,R.id.videoButton,     VideoMainActivitySwipe.class);
        
        
        // We use this to send broadcasts within our local process.
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
         // We are going to watch for interesting local broadcasts.
        IntentFilter filter = new IntentFilter();
        filter.addAction(MuldvarpService.ACTION_PEOPLE_UPDATE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
                if (intent.getAction().compareTo(MuldvarpService.ACTION_PEOPLE_UPDATE) == 0) {                    
                    System.out.println("Toasting" + intent.getAction());
                    Toast.makeText(context, "Got async broadcast", Toast.LENGTH_LONG).show();
                } 
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
        
        System.out.println("Binding service");
        getActivity().bindService(new Intent(getActivity(), MuldvarpService.class), mConnection, Context.BIND_AUTO_CREATE);

        
        return retVal;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unbind from the service
        if (bound) {
            getActivity().unbindService(mConnection);
            bound = false;
        }

    }

    private void createButton(View view, int buttonid, final Class action) {
        Button button = (Button) view.findViewById(buttonid);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), action);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
