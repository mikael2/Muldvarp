/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.news;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 * @author Lena
 */
public class NewsCategoryAction extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here   
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("url");
        }

    }
}
