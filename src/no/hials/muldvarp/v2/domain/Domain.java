/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.io.Serializable;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.fragments.FrontPageFragment;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.TextFragment;
import no.hials.muldvarp.v2.utility.DummyDataProvider;

public class Domain implements Serializable {
    Integer id;
    String name;
    String detail;
    String description;
    int icon;
    Class activity;

    public Domain() {

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

    /**
     * Populates the fragment view list with class/view/layer specific content
     *
     * @param fragmentList
     * @param context
     */
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        //Fragments that are considered "default"
        fragmentList.add(new FrontPageFragment("Startside", R.drawable.stolen_smsalt));
        fragmentList.add(new TextFragment("Informasjon", TextFragment.Type.INFO, R.drawable.stolen_contacts));
        fragmentList.add(new ListFragment("Nyheter", R.drawable.stolen_tikl));

        if(this.getClass().getSuperclass() == Object.class) { // if this class has no superclass (except object)
            defaultList(fragmentList, context);
        }
    }

    /**
     * Standard frontpage view
     *
     * @param fragmentList
     * @param context
     */
    public void defaultList(List<MuldvarpFragment> fragmentList, Context context) {
        ListFragment gridFragmentList = new ListFragment("Studier", R.drawable.stolen_smsalt);
        gridFragmentList.setListItems(DummyDataProvider.getProgrammeList(context));
        fragmentList.add(gridFragmentList);
        fragmentList.add(new ListFragment("Video", R.drawable.stolen_youtube));
        fragmentList.add(new ListFragment("Quiz", R.drawable.stolen_calculator, DummyDataProvider.getQuizList()));
        fragmentList.add(new ListFragment("Dokumenter", R.drawable.stolen_dictonary));
        fragmentList.add(new TextFragment("Opptak", TextFragment.Type.REQUIREMENT, R.drawable.stolen_notes));
        fragmentList.add(new TextFragment("Datoer", TextFragment.Type.DATE, R.drawable.stolen_calender));
        fragmentList.add(new TextFragment("Hjelp", TextFragment.Type.HELP, R.drawable.stolen_help));
    }
}
