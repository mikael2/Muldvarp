/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import no.hials.muldvarp.v2.deprecated.MuldvarpActivity;
import android.app.Fragment;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Date;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Help;
import no.hials.muldvarp.v2.domain.Info;
import no.hials.muldvarp.v2.domain.News;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.domain.Requirement;
import no.hials.muldvarp.v2.domain.Video;
import no.hials.muldvarp.v2.fragments.deprecated.FrontPageFragment;
import no.hials.muldvarp.v2.fragments.deprecated.ListFragment;
import no.hials.muldvarp.v2.fragments.QuizFragment;
import no.hials.muldvarp.v2.fragments.TextFragment;

/**
 *
 * @author kristoffer
 */
public class ProgrammeActivity extends MuldvarpActivity {
    public List<Fragment> fragmentList = new ArrayList<Fragment>();
    private Programme selectedProgramme;
    private Info info;
    private Requirement req;
    private Help help;
    private Date date;
    public List<News> newsList = new ArrayList<News>();
    public List<Video> videoList = new ArrayList<Video>();
    public List<Document> documentList = new ArrayList<Document>();
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // testdata
        info = new Info("Informasjon", "Blablablabl");
        req = new Requirement("Opptakskrav", "blablabla");
        help = new Help("Hjelpeside", "Dette gjør du blablabla");
        date = new Date("Datoer", "<h2>Test</h2><p>babvavasvas</p>");
        newsList.add(new News("Tittel", "Tekst"));
        videoList.add(new Video("Videotittel", "Beskrivelse"));
        documentList.add(new Document("Dokumenttittel", "Beskrivelse"));
        selectedProgramme = new Programme("Dataingeniør");
        selectedProgramme.addCourse(new Course("Programmering"));
        
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
        
        if(fragmentList.isEmpty()) {
            fragmentList.add(new FrontPageFragment(FrontPageFragment.Type.PROGRAMME));
            fragmentList.add(new TextFragment(TextFragment.Type.INFO));
            fragmentList.add(new ListFragment(ListFragment.Type.NEWS));
            fragmentList.add(new ListFragment(ListFragment.Type.COURSES));
            fragmentList.add(new ListFragment(ListFragment.Type.VIDEO));
            fragmentList.add(new QuizFragment());
            fragmentList.add(new ListFragment(ListFragment.Type.DOCUMENTS));
            fragmentList.add(new TextFragment(TextFragment.Type.REQUIREMENT));
            fragmentList.add(new TextFragment(TextFragment.Type.DATE));
            fragmentList.add(new TextFragment(TextFragment.Type.HELP));
        }
        
        getSpinnerList(this, fragmentList, R.array.programme_list, android.R.layout.simple_spinner_dropdown_item);
    }
    
    @Override
    public List<Course> getCourseList() {
        return selectedProgramme.getCourses();
    }

    @Override
    public Programme getSelectedProgramme() {
        return selectedProgramme;
    }

    @Override
    public Info getInfo() {
        return info;
    }

    @Override
    public Help getHelp() {
        return help;
    }

    @Override
    public List<News> getNewsList() {
        return newsList;
    }

    @Override
    public List<Document> getDocumentList() {
        return documentList;
    }

    @Override
    public Requirement getRequirement() {
        return req;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public List<Video> getVideoList() {
        return videoList;
    }
}
