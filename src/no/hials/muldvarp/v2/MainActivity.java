/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.LoginActivity;
import no.hials.muldvarp.R;
import no.hials.muldvarp.desktop.MainPreferenceActivity;
import no.hials.muldvarp.v2.domain.Date;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Help;
import no.hials.muldvarp.v2.domain.Info;
import no.hials.muldvarp.v2.domain.News;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.domain.Requirement;
import no.hials.muldvarp.v2.domain.Video;
import no.hials.muldvarp.v2.fragments.FrontPageFragment;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.QuizFragment;
import no.hials.muldvarp.v2.fragments.TextFragment;
import no.hials.muldvarp.v2.utility.utils;

public class MainActivity extends MuldvarpActivity {
    public List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<Programme> programmeList = new ArrayList<Programme>();
    private Activity activity = this;
    public ActionBar actionBar;
    public List<News> newsList = new ArrayList<News>();
    public List<Video> videoList = new ArrayList<Video>();
    public List<Document> documentList = new ArrayList<Document>();
    Info info;
    Requirement req;
    Help help;
    Date date;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        icons = new int[] {
            R.drawable.stolen_contacts,
            R.drawable.stolen_tikl,
            R.drawable.stolen_smsalt,
            R.drawable.stolen_youtube,
            R.drawable.stolen_calculator,
            R.drawable.stolen_dictonary,
            R.drawable.stolen_notes,
            R.drawable.stolen_calender,
            R.drawable.stolen_help
        };
        
        // testdata
        programmeList.add(new Programme("Bachelor Dataingeniør"));
        newsList.add(new News("Tittel", "Tekst"));
        videoList.add(new Video("Videotittel", "Beskrivelse"));
        documentList.add(new Document("Dokumenttittel", "Beskrivelse"));
        info = new Info("Informasjon", "Blablablabl");
        req = new Requirement("Opptakskrav", "blablabla");
        help = new Help("Hjelpeside", "Dette gjør du blablabla");
        date = new Date("Datoer", "<h2>Test</h2><p>babvavasvas</p>");
        
        fragmentList.add(new FrontPageFragment(FrontPageFragment.Type.MAIN));
        fragmentList.add(new TextFragment(TextFragment.Type.INFO));
        fragmentList.add(new ListFragment(ListFragment.Type.NEWS));
        fragmentList.add(new ListFragment(ListFragment.Type.PROGRAMME));
        fragmentList.add(new ListFragment(ListFragment.Type.VIDEO));
        fragmentList.add(new QuizFragment());
        fragmentList.add(new ListFragment(ListFragment.Type.DOCUMENTS));
        fragmentList.add(new TextFragment(TextFragment.Type.REQUIREMENT));
        fragmentList.add(new TextFragment(TextFragment.Type.DATE));
        fragmentList.add(new TextFragment(TextFragment.Type.HELP));
        
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.main_list,
          android.R.layout.simple_spinner_dropdown_item);

        OnNavigationListener mOnNavigationListener = new OnNavigationListener() {
            String[] strings = getResources().getStringArray(R.array.main_list);

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                return utils.changeFragment(activity, fragmentList, position);
            }
        };

        actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

//    private void createTabs(ActionBar actionBar) {
//        actionBar.addTab(actionBar.newTab()
//                .setText("Simple")
//                .setTabListener(new TabListener<CourseListFragment>(
//                        this,
//                        "Tema",
//                        CourseListFragment.class)));
//        
//        actionBar.addTab(actionBar.newTab()
//                .setText("Simple")
//                .setTabListener(new TabListener<CourseListFragment>(
//                        this,
//                        "Tema",
//                        CourseListFragment.class)));
//    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, no.hials.muldvarp.v2.MainActivity.class);
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
            case R.id.test:
                intent = new Intent(this, QuizActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;        
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public List<Programme> getProgrammeList() {
        return programmeList;
    }

    @Override
    public List<News> getNewsList() {
        return newsList;
    }

    @Override
    public List<Video> getVideoList() {
        return videoList;
    }

    @Override
    public List<Document> getDocumentList() {
        return documentList;
    }

    @Override
    public Info getInfo() {
        return info;
    }

    @Override
    public Requirement getRequirement() {
        return req;
    }

    @Override
    public Help getHelp() {
        return help;
    }

    @Override
    public Date getDate() {
        return date;
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }
}
