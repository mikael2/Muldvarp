/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import no.hials.muldvarp.R;
import org.xml.sax.Parser;

/**
 *
 * @author Nospherus
 */
public class LibraryDetail extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here     
        setContentView(R.layout.library_detail);
        final Activity mActivity = this;
        final Button button = (Button) findViewById(R.id.readbutton);
         button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                String url = "http://www.hials.no/nor/content/download/49530/977307/file/PB01%20ETABLERING%20AV%20NYE%20STUDIETILBUD%20TIL%20OG%20MED%2030%20STUDIEPOENG.pdf";
                final String googleDocsUrl = "http://docs.google.com/viewer?url=";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(googleDocsUrl + url), "text/html");
                startActivity(i);
             }
         });}
    }

