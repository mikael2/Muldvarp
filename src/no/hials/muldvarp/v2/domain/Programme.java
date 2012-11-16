/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.MuldvarpService;
import no.hials.muldvarp.v2.fragments.FrontPageFragment;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.ListFragment.ListType;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.TextFragment;
import no.hials.muldvarp.v2.utility.DummyDataProvider;
import no.hials.muldvarp.v2.utility.JSONUtilities;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author johan
 */
public class Programme extends Domain {
    List<Course> courses = new ArrayList<Course>();;
    String imageurl;
    int revision;
    String programmeId;

    public Programme() {

    }

    public Programme(JSONObject json) throws JSONException {
        super(json);
        courses = JSONUtilities.JSONArrayToCourses(json.getJSONArray("courses"));        
    }

    public Programme(String name) {
        super(name);
    }

    public void addCourse(Course c) {
        getCourses().add(c);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void removeCourse(Course c) {
        getCourses().remove(c);
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    @Override
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        fragmentList.add(new FrontPageFragment("Startside", R.drawable.stolen_smsalt));
        fragmentList.add(new TextFragment("Informasjon", R.drawable.stolen_contacts, 149));
        fragmentList.add(new ListFragment("Nyheter", R.drawable.stolen_tikl, ListFragment.ListType.NEWS));
        fragmentList.add(new ListFragment("Fag", R.drawable.stolen_smsalt, ListFragment.ListType.COURSE));
        fragmentList.add(new ListFragment("Video", R.drawable.stolen_youtube, ListFragment.ListType.VIDEO));
        fragmentList.add(new ListFragment("Quiz", R.drawable.stolen_calculator, DummyDataProvider.getQuizList(), ListType.QUIZ));
        fragmentList.add(new ListFragment("Dokumenter", R.drawable.stolen_dictonary, ListFragment.ListType.DOCUMENT));
        fragmentList.add(new TextFragment("Datoer", R.drawable.stolen_calender, 35));
    }
}
