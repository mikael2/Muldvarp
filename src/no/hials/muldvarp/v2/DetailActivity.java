/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.os.Bundle;
import android.widget.TextView;
import no.hials.muldvarp.R;

/**
 * This class defines a Activity used for displaying information about an item, 
 * as well as providing functionality for interacting with it.
 * 
 * @author johan
 */
public class DetailActivity extends MuldvarpActivity {
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metadata);
        
        TextView textItemTitle = (TextView) findViewById(R.id.item_title);
        textItemTitle.setText("Hardkodet Tittel");

        TextView textItemDescription = (TextView) findViewById(R.id.item_description);
        textItemDescription.setText("Hardkodet Tittel");
    }
}
