package no.hials.muldvarp.desktop;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import no.hials.muldvarp.R;
import no.hials.muldvarp.courses.CourseActivity;
import no.hials.muldvarp.library.LIBMainscreen;

/**
 *
 * @author mikael
 */
public class DesktopFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        
        View retVal = inflater.inflate(R.layout.desktop_fragment, container, false);
        
        Button courseButton = (Button) retVal.findViewById(R.id.coursesbutton);
        courseButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CourseActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        Button libraryButton = (Button) retVal.findViewById(R.id.libraryButton);
        libraryButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), LIBMainscreen.class);
                startActivityForResult(myIntent, 0);
            }

        });

        
        return retVal;
    }
}