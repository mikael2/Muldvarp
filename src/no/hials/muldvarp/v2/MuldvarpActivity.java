/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.desktop.MainPreferenceActivity;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Date;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Help;
import no.hials.muldvarp.v2.domain.Info;
import no.hials.muldvarp.v2.domain.News;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.domain.Requirement;
import no.hials.muldvarp.v2.domain.Video;
import no.hials.muldvarp.v2.utility.utils;

/**
 *
 * @author kristoffer
 */
public class MuldvarpActivity extends Activity implements iRibbonMenuCallback {
    Bundle savedInstanceState;
    public int icons[];
    public RibbonMenuView rbmView;
    
    @Override
    public void onBackPressed() {
        if(rbmView.isMenuVisible())
            rbmView.hideMenu();
        if(getActionBar().getSelectedNavigationIndex() > 0)
            getActionBar().setSelectedNavigationItem(0);
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        
        setContentView(R.layout.main);
        
        rbmView = (RibbonMenuView) findViewById(R.id.ribbonMenuView1);
        rbmView.setMenuClickCallback(this);
        rbmView.setMenuItems(R.menu.ribbon_menu);
        TextView loginname = (TextView) findViewById(R.id.loginname);
        loginname.setText("Ola Nordmann");
        
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getIntExtra("tab", 0) > 0)
            getActionBar().setSelectedNavigationItem(getIntent().getIntExtra("tab", 0));
        else if(savedInstanceState != null)
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt("tab"));
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
    
    public List<Programme> getProgrammeList() {
        return null;
    }
    
    public List<Course> getCourseList() {
        return null;
    }
    
    public Programme getSelectedProgramme() {
        return null;
    }

    public Requirement getRequirement() {
        return null;
    }

    public Help getHelp() {
        return null;
    }

    public Date getDate() {
        return null;
    }

    public List<News> getNewsList() {
        return null;
    }

    public List<Video> getVideoList() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Document> getDocumentList() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Info getInfo() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()) {
            case android.R.id.home:
                rbmView.toggleMenu();
                return true;
            case R.id.menu_settings:
                Intent prefs = new Intent(this, MainPreferenceActivity.class);
                prefs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(prefs);
                return true;
            case R.id.login:
                showDialog(0);
                return true;    
            case R.id.test:
                intent = new Intent(this, DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;        
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");
        return dialog;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }
    
    @Override
    public void RibbonMenuItemClick(int itemId) {
        switch(itemId) {
            case 0:
                Intent intent = new Intent(this, ProgrammeActivity.class);
                intent.putExtra("tab", 3);
                startActivity(intent);
                break;
        }
    }
    
    public void getSpinnerList(final Activity activity, final List<Fragment> fragmentList, int strings, int layout) {
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, strings, layout);
        ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                if(rbmView.isMenuVisible())
                    rbmView.hideMenu();
                return utils.changeFragment(activity, fragmentList, position);
            }
        };
        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
    }
}
