package no.hials.muldvarp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.io.File;
import no.hials.muldvarp.desktop.MainPreferenceActivity;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);  
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
                
            case R.id.menu_settings:
                Intent prefs = new Intent(this, MainPreferenceActivity.class);
                prefs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(prefs);
                return true;
            case R.id.login:
                intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;    
            default:
                return super.onOptionsItemSelected(item);
        }
    }     
}
