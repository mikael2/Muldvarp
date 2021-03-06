package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class Course extends Domain implements Serializable {
    String imageurl;
    String courseId;
    Integer revision;
    ArrayList<Topic> themes;
    ArrayList<ObligatoryTask> obligatoryTasks;
    ArrayList<Exam> exams;

    public Course(JSONObject json) throws JSONException {
        super(json);
        this.themes = parseThemes(json.getJSONArray("themes"));
        this.exams = parseExams(json.getJSONArray("exams"));
        this.obligatoryTasks = parseObligTasks(json.getJSONArray("obligatoryTasks"));
        this.revision = json.getInt("revision");
    }

    public Course(String name, String detail, String url) {
        super(name);
        super.detail = detail;
        this.imageurl = url;
    }

    public final ArrayList<Topic> parseThemes(JSONArray jArray) throws JSONException {
        ArrayList<Topic> retVal = new ArrayList<Topic>();
        for(int i = 0; i < jArray.length(); i++) {
            Topic t = new Topic(jArray.getJSONObject(i));
            retVal.add(t);
        }
        return retVal;
    }
    
    private ArrayList<Exam> parseExams(JSONArray jsonArray) throws JSONException {
        ArrayList<Exam> retVal = new ArrayList<Exam>();
        for(int i = 0; i < jsonArray.length(); i++) {
            Exam t = new Exam(jsonArray.getJSONObject(i));
            retVal.add(t);
        }
        return retVal;
    }

    private ArrayList<ObligatoryTask> parseObligTasks(JSONArray jsonArray) throws JSONException {
        ArrayList<ObligatoryTask> retVal = new ArrayList<ObligatoryTask>();
        for(int i = 0; i < jsonArray.length(); i++) {
            ObligatoryTask t = new ObligatoryTask(jsonArray.getJSONObject(i));
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
        return 123;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public ArrayList<Topic> getTopics() {
        return themes;
    }

    public void setThemes(ArrayList<Topic> themes) {
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

//    @Override
//    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
//        fragmentList.add(new FrontPageFragment("Startside", R.drawable.stolen_smsalt));
//        fragmentList.add(new WebzViewFragment("Informasjon", R.drawable.stolen_contacts, info));
//        fragmentList.add(new ListFragment("Nyheter", R.drawable.stolen_tikl, ListFragment.ListType.NEWS));
//        fragmentList.add(new ListFragment("Delemne", R.drawable.stolen_smsalt, ListFragment.ListType.TOPIC));
//        fragmentList.add(new ListFragment("Video", R.drawable.stolen_youtube, ListFragment.ListType.VIDEO));
//        fragmentList.add(new ListFragment("Quiz", R.drawable.stolen_calculator, DummyDataProvider.getQuizList(), ListType.QUIZ));
//        fragmentList.add(new ListFragment("Dokumenter", R.drawable.stolen_dictonary, ListFragment.ListType.DOCUMENT));
//        fragmentList.add(new WebzViewFragment("Datoer", R.drawable.stolen_calender, dates));
//    }    

    
}
