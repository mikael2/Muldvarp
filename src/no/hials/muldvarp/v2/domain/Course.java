package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.TextFragment;
import no.hials.muldvarp.v2.utility.DummyDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class Course extends Domain implements Serializable {
    String imageurl;
    Integer revision;
    ArrayList<Theme> themes;
    ArrayList<ObligatoryTask> obligatoryTasks;
    ArrayList<Exam> exams;

    public Course() {

    }

    public Course(JSONObject json) throws JSONException {
        super(json);
        this.themes = parseThemes(json.getJSONArray("themes"));
    }

    public Course(String name) {
        super(name);
    }

    public Course(String name, String detail, String url) {
        super(name);
        super.detail = detail;
        this.imageurl = url;
    }

    public final ArrayList<Theme> parseThemes(JSONArray jArray) throws JSONException {
        ArrayList<Theme> retVal = new ArrayList<Theme>();
        for(int i = 0; i < jArray.length(); i++) {
            JSONObject jsonObject = jArray.getJSONObject(i);
            Theme t = new Theme(jsonObject);
            retVal.add(t);
        }
        return retVal;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<Theme> themes) {
        this.themes = themes;
    }

    public ArrayList<ObligatoryTask> getObligatoryTasks() {
        return obligatoryTasks;
    }

    public void setObligatoryTasks(ArrayList<ObligatoryTask> obligatoryTasks) {
        this.obligatoryTasks = obligatoryTasks;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        super.populateList(fragmentList, context);
        ListFragment gridFragmentList = new ListFragment("Delemne", R.drawable.stolen_smsalt);
        gridFragmentList.setListItems(DummyDataProvider.getTopicList(context));
        fragmentList.add(gridFragmentList);
        fragmentList.add(new ListFragment("Video", R.drawable.stolen_youtube));
        fragmentList.add(new ListFragment("Quiz", R.drawable.stolen_calculator, DummyDataProvider.getQuizList()));
        fragmentList.add(new TextFragment("Datoer", TextFragment.Type.DATE, R.drawable.stolen_calender));
    }
}
