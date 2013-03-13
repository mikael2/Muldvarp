/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.fragments.FrontPageFragment;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.WebzViewFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents an object in the applications domain layer. (more to follow)
 *
 * @author johan
 */
public class Domain implements Serializable {
    Integer id;
    String name;
    String detail;
    String description;
    int icon;
    Class activity;
    List<DomainFragment> fragments;

    public Domain() {

    }

    public Domain(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        //this.detail = json.getString("detail");
//        System.out.println("ID " + id);
//        System.out.println("NAME " + name);
        try {
            this.fragments = JSONArrayToFragments(json.getJSONArray("fragmentBundle"));
        } catch(JSONException ex) {
//            Log.e("MULDVARP", name + " har ingen fragments");
//            Log.e("DOMAIN", ex.getMessage());
        }        
    }
    
    public static List<DomainFragment> JSONArrayToFragments(JSONArray jsonArray) throws JSONException {
        List<DomainFragment> retVal = new ArrayList<DomainFragment>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new DomainFragment(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }

    public Domain(String name) {
        this.name = name;
    }

    public Domain(String name, String detail, String description, int icon) {
        this.name = name;
        this.detail = detail;
        this.description = description;
        this.icon = icon;
    }

    public Domain(String name, String detail, String description, int icon, Class activity) {
        this.name = name;
        this.detail = detail;
        this.description = description;
        this.icon = icon;
        this.activity = activity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public final ArrayList<DomainFragment> parseFragments(JSONArray jArray) throws JSONException {
        ArrayList<DomainFragment> retVal = new ArrayList<DomainFragment>();
        for(int i = 0; i < jArray.length(); i++) {
            JSONObject jsonObject = jArray.getJSONObject(i);
            DomainFragment t = new DomainFragment(jsonObject);
            retVal.add(t);
        }
        return retVal;
    }
    
    /**
     * Populates the fragment view list with class/view/layer specific content
     *
     * @param fragmentList
     * @param context
     */
    @Deprecated
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
//        //Fragments that are considered "default"
//        fragmentList.add(new FrontPageFragment("Startside", R.drawable.stolen_smsalt));
//        fragmentList.add(new WebzViewFragment("Informasjon", R.drawable.stolen_contacts, 148));
//        fragmentList.add(new ListFragment("Nyheter", R.drawable.stolen_tikl, ListFragment.ListType.NEWS));
//        fragmentList.add(new ListFragment("Studier", R.drawable.stolen_smsalt, ListFragment.ListType.PROGRAMME));
//        fragmentList.add(new ListFragment("Video", R.drawable.stolen_youtube, ListFragment.ListType.VIDEO));
//        fragmentList.add(new ListFragment("Quiz", R.drawable.stolen_calculator, DummyDataProvider.getQuizList(), ListFragment.ListType.QUIZ));
//        fragmentList.add(new ListFragment("Dokumenter", R.drawable.stolen_dictonary, ListFragment.ListType.DOCUMENT));
//        fragmentList.add(new WebzViewFragment("Opptak", R.drawable.stolen_notes, 35));
//        fragmentList.add(new WebzViewFragment("Datoer", R.drawable.stolen_calender, 211));
//        fragmentList.add(new WebzViewFragment("Hjelp", R.drawable.stolen_help, 209));
    }
    
    public void constructList(List<MuldvarpFragment> fragmentList, List<DomainFragment> fragments) {
        fragmentList.clear();
        fragmentList.add(new FrontPageFragment("Startside", R.drawable.stolen_smsalt));
        for(int i = 0; i < fragments.size(); i++) {
            switch(fragments.get(i).getFragmentType()) {
                case PROGRAMME:
                    fragmentList.add(new ListFragment(fragments.get(i).getName(), R.drawable.stolen_smsalt, ListFragment.ListType.PROGRAMME));
                    break;
                case COURSE:                    
                    fragmentList.add(new ListFragment(fragments.get(i).getName(), R.drawable.stolen_smsalt, ListFragment.ListType.COURSE));
                    break;
                case NEWS:
                    fragmentList.add(new ListFragment(fragments.get(i).getName(), R.drawable.stolen_tikl, ListFragment.ListType.NEWS));
                    break;
                case QUIZ:
                    fragmentList.add(new ListFragment(fragments.get(i).getName(), R.drawable.stolen_calculator, fragments.get(i).items, ListFragment.ListType.QUIZ));
                    break;
                case DOCUMENT:
                    fragmentList.add(new ListFragment(fragments.get(i).getName(), R.drawable.stolen_dictonary, fragments.get(i).items, ListFragment.ListType.DOCUMENT));
                    break;
                case VIDEO:
                    fragmentList.add(new ListFragment(fragments.get(i).getName(), R.drawable.stolen_youtube, fragments.get(i).items, ListFragment.ListType.VIDEO));
                    break;
                case ARTICLE:
                    fragmentList.add(new WebzViewFragment(fragments.get(i).getName(), R.drawable.stolen_contacts, (int)fragments.get(i).getArticleID()));
                    break;
            }
        }
    }

    public List<DomainFragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<DomainFragment> fragments) {
        this.fragments = fragments;
    }
}
