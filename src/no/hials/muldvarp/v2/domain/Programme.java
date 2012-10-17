/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.TextFragment;
import no.hials.muldvarp.v2.utility.DummyDataProvider;
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
        this.id = json.getInt("id");
        this.name = json.getString("name");
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



    @Override
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        super.populateList(fragmentList, context);
        ListFragment gridFragmentList = new ListFragment("Fag", R.drawable.stolen_smsalt, ListFragment.ListType.COURSE);
        gridFragmentList.setListItems(DummyDataProvider.requestCoursesbyProgrammeFromDB(context, this));
        fragmentList.add(gridFragmentList);
        fragmentList.add(new ListFragment("Video", R.drawable.stolen_youtube, ListFragment.ListType.VIDEO));
        fragmentList.add(new ListFragment("Quiz", R.drawable.stolen_calculator, DummyDataProvider.getQuizList()));
        fragmentList.add(new TextFragment("Datoer", TextFragment.Type.DATE, R.drawable.stolen_calender));
    }
}
