/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import no.hials.muldvarp.R;

/**
 *
 * @author Nospherus
 */
public class LibraryDetail extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ToDo add your GUI initialization code here 
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.library_detail);
        final Activity mActivity = this;
        final Button button = (Button) findViewById(R.id.readbutton);
        final Button button2 = (Button) findViewById(R.id.dlbutton);
        TextView t1 = (TextView)findViewById(R.id.texttitle);
        t1.setText("Dette er en beskrivende tittel");
        TextView t2 = (TextView)findViewById(R.id.textalternatetitle);
        t2.setText("Alternate title: How to make money");
        TextView t3 = (TextView)findViewById(R.id.textauthor);
        t3.setText("Author: Robert Downey Jr.");
        TextView t4 = (TextView)findViewById(R.id.textcoauthor);
        t4.setText("Co Author(s): Clark Kent");
        TextView t5 = (TextView)findViewById(R.id.textpageno);
        t5.setText("Number of pages: " + 233);
        TextView t6 = (TextView)findViewById(R.id.textpublished);
        t6.setText("Published: 24.03.1965");
        TextView t7 = (TextView)findViewById(R.id.textuploaded);
        t7.setText("Uploaded: 30.01.2012");
        TextView t8 = (TextView)findViewById(R.id.textsummary);
        t8.setText("Summary: The Revelation of Jesus Christ, which God gave unto him, to shew unto his servants things which must shortly come to pass; and he sent and signified it by his angel unto his servant John: Who bare record of the word of God, and of the testimony of Jesus Christ, and of all things that he saw. Blessed is he that readeth, and they that hear the words of this prophecy, and keep those things which are written therein: for the time is at hand.");
         
        button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                String url = "http://www.hials.no/nor/content/download/49530/977307/file/PB01%20ETABLERING%20AV%20NYE%20STUDIETILBUD%20TIL%20OG%20MED%2030%20STUDIEPOENG.pdf";
                final String googleDocsUrl = "http://docs.google.com/viewer?url=";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(googleDocsUrl + url), "text/html");
                startActivity(i);
             }
         });
        
        button2.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                String url = "http://www.hials.no/nor/content/download/49530/977307/file/PB01%20ETABLERING%20AV%20NYE%20STUDIETILBUD%20TIL%20OG%20MED%2030%20STUDIEPOENG.pdf";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(url), "text/html");
                startActivity(i);
             }
         });
         
         final ToggleButton togglebutton = (ToggleButton) findViewById(R.id.togglebutton);
         togglebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (togglebutton.isChecked()) {
              togglebutton.setButtonDrawable(R.drawable.star_full);
              Toast.makeText(LibraryDetail.this, "Added to favourites", Toast.LENGTH_SHORT).show();
         } else {
                togglebutton.setButtonDrawable(R.drawable.star_empty);
                Toast.makeText(LibraryDetail.this, "Removed from favourites", Toast.LENGTH_SHORT).show();
         }
            }
    });
    }
}

