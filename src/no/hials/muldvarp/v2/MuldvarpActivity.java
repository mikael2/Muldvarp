/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.Activity;
import android.os.Bundle;
import java.util.List;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Date;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Help;
import no.hials.muldvarp.v2.domain.News;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.domain.Requirement;
import no.hials.muldvarp.v2.domain.Video;

/**
 *
 * @author kristoffer
 */
public class MuldvarpActivity extends Activity {
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
}
