/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.news;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

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
        //GUI initialization code
        WebView webview = new WebView(this);
        setContentView(webview);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("uri");
            webview.loadUrl(value);
        }

    }
}
