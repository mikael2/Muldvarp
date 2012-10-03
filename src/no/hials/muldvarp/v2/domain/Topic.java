/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.utility.DummyDataProvider;

/**
 *
 * @author johan
 */
public class Topic extends Domain {
    Boolean done = false;
    String content_url;
    String contentType;
    List<Question> questions;

    public Topic(){
        
    }
    
    public Topic(String name) {
        super(name);
    }

    public void acceptTask() {
        done = true;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        super.populateList(fragmentList, context);
//        ListFragment gridFragmentList = new ListFragment("Tutorials", R.drawable.stolen_smsalt);
//        gridFragmentList.setListItems(DummyDataProvider.getProgrammeList(this));
//        fragmentList.add(gridFragmentList);
        fragmentList.add(new ListFragment("Video", R.drawable.stolen_youtube));
        fragmentList.add(new ListFragment("Quiz", R.drawable.stolen_calculator, DummyDataProvider.getQuizList()));
    }

}
