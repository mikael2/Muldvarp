package no.hials.muldvarp.news;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

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
        //GUI initialization code
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl("http://master.uials.no/muldvarp/faces/news.xhtml");
    }
}
