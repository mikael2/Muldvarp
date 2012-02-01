package no.hials.muldvarp.desktop;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import no.hials.muldvarp.R;
import no.hials.muldvarp.courses.CourseActivity;
import no.hials.muldvarp.library.LIBMainscreen;
import no.hials.muldvarp.news.NewsActivity;
import no.hials.muldvarp.video.VideoMainActivity;

/**
 *
 * @author mikael
 */

public class DesktopFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View retVal = inflater.inflate(R.layout.desktop_fragment, container, false);        
        
        //Directory, News NYI, redirects to Video for now
        createButton(retVal,R.id.directorybutton, VideoMainActivity.class);        
        createButton(retVal,R.id.newsbutton,      NewsActivity.class);          
        createButton(retVal,R.id.coursesbutton,   CourseActivity.class);        
        createButton(retVal,R.id.libraryButton,   LIBMainscreen.class);        
        createButton(retVal,R.id.videoButton,     VideoMainActivity.class);
        
        return retVal;
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
