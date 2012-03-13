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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.LibraryItem;

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
        Bundle b = this.getIntent().getExtras();
        final LibraryItem li = (LibraryItem)b.getSerializable("item");
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.library_detail);
        final Activity mActivity = this;
        final Button button = (Button) findViewById(R.id.readbutton);
        final Button button2 = (Button) findViewById(R.id.dlbutton);
        TextView t1 = (TextView)findViewById(R.id.texttitle);
        t1.setText(li.getTitle());
        TextView t2 = (TextView)findViewById(R.id.textalternatetitle);
        t2.setText("Alternate title: " + li.getAlternateTitle());
        TextView t3 = (TextView)findViewById(R.id.textauthor);
        t3.setText("Author: " + li.getAuthor());
        TextView t4 = (TextView)findViewById(R.id.textcoauthor);
        t4.setText("Co Author(s): " + li.getCoAuthor());
        TextView t5 = (TextView)findViewById(R.id.textpageno);
        t5.setText("Number of pages: " + li.getPageNo());
        TextView t6 = (TextView)findViewById(R.id.textpublished);
        t6.setText("Published: " + li.getPublished());
        TextView t7 = (TextView)findViewById(R.id.textuploaded);
        t7.setText("Uploaded: " + li.getUploaded());
        TextView t8 = (TextView)findViewById(R.id.textsummary);
        t8.setText("Summary: " + li.getSummary());
        try{
        System.out.println(li.getURL());}
        catch(Exception e){
            System.out.println("Failed to print URL");
        }
        try{
        System.out.println(li.getIconURL());}
        catch(Exception e){
            System.out.println("Failed to print iconURL");
        }
        
        try{
        System.out.println(li.getThumbURL());}
        catch(Exception e){
            System.out.println("Failed to print thumbURL");
        }
         
        button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                String url = li.getURL();
                final String googleDocsUrl = "http://docs.google.com/viewer?url=";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(googleDocsUrl + url), "text/html");
                startActivity(i);
             }
         });
        
        button2.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                String url = li.getURL();
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

