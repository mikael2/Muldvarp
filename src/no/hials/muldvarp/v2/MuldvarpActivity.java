/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;
import java.util.List;
import no.hials.muldvarp.LoginActivity;
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

/**
 *
 * @author kristoffer
 */
public class MuldvarpActivity extends Activity implements iRibbonMenuCallback {
    Bundle savedInstanceState;
    public int icons[];
    
    @Override
    public void onBackPressed() {
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

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(savedInstanceState != null)
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
    
    public RibbonMenuView rbmView;

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
                intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
    public void RibbonMenuItemClick(int itemId) {
    }
}
