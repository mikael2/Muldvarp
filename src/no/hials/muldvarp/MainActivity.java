package no.hials.muldvarp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import no.hials.muldvarp.courses.CourseActivity;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button testbutton = (Button) findViewById(R.id.coursesbutton);
        testbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CourseActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });
    }
    
}
