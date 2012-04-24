package no.hials.muldvarp.directory;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.desktop.DesktopFragment;
import no.hials.muldvarp.domain.Person;
import no.hials.muldvarp.view.FragmentPager;


/**
 *
 * @author Lena
 */
public class DirectoryActivity extends FragmentActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);
        
        ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        FragmentPager pager = (FragmentPager) findViewById(R.id.pager);
        pager.initializeAdapter(getSupportFragmentManager(), bar);     
        pager.addTab("Campus", DirectoryCampus.class, null);
        pager.addTab("People", DirectoryPeople.class, null);
        
        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.menu_search);
        //searchView.setIconifiedByDefault(false);
        
    }
    
      
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.directory_activity, menu);
    return true;
}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            // app icon in action bar clicked; go home
            Intent intent = new Intent(this, DesktopFragment.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
            default:
            return super.onOptionsItemSelected(item);
    }
}

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }*/

    void viewPerson(Person person) {
        FragmentPager pager = (FragmentPager) findViewById(R.id.pager);
                  
  
    }
}