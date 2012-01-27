/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import no.hials.muldvarp.R;
import no.hials.muldvarp.video.VideoActivity;

/**
 *
 * @author Lena
 */
public class NewsActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        // ToDo add your GUI initialization code here  
        createButton(R.id.forskning, NewsCategoryAction.class,"http://blade?category"); 
    }
    
    private void createButton(int buttonid, final Class action, final String uri) {
        Button button = (Button) findViewById(buttonid);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), action);
                myIntent.putExtra("uri", uri);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
