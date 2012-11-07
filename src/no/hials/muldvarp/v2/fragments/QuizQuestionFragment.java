/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Alternative;
import no.hials.muldvarp.v2.domain.Question;

/**
 * This fragment defines a single question sheet.
 * @author johan
 */
public class QuizQuestionFragment extends MuldvarpFragment {
    
    //Global variables
    View fragmentView;
    ListView listView;    
    //Quiz
    Question question;
    int questionNo;
    int questionAmount;
    
    /**
     * Constructor for the class.
     * @param question Question
     */
    public QuizQuestionFragment(Question question){
        this.question = question;        
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }
    
    /**
     * Function to return the Question variable in this class. Returns null if not set.
     * @return Question, null
     */
    public Question getQuestion(){
        return question;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        //Get ListView and set layout mode if not null
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.activity_quiz_question, container, false);
            listView = (ListView)fragmentView.findViewById(R.id.QuizListView);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);            
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3){
                }
        });
            getCurrentQuestion();
        }
        return fragmentView;        
    }
    
    public void  getCurrentQuestion(){
        
        ArrayList items = new ArrayList();
        if ((question == null) || (question.getName() == null)) {            
            items.add("For fun");
            items.add("For the money");
            items.add("No reason");
            items.add("None of the above");
            makeQuizData("Why are we here?", items, "No reason");  
        } else {
            items = getStringListFromQuestion(question);
        }
        
        TextView textView = (TextView) fragmentView.findViewById(R.id.QuestionText);
        textView.setText(questionNo +"/"+questionAmount + ": " + question.getName());
        
        listView.setAdapter(new ArrayAdapter<String>(fragmentView.getContext(),
                android.R.layout.simple_list_item_checked, items));
    }
    
    public ArrayList<String> getStringListFromQuestion(Question question){
        
        ArrayList retVal = new ArrayList();
        ArrayList<Alternative> alternatives = (ArrayList) question.getAlternatives();
        for (int i = 0; i < alternatives.size(); i++) {
            retVal.add(alternatives.get(i).getName());
        }
        return retVal;
    }

    
    public void makeQuizData(String questionString, List<String> alternatives, String correctAlternative) {
        ArrayList<Alternative> list = new ArrayList();
        Alternative correct = null;
        int i = 0;
        for (String s : alternatives) {
            Alternative alt = new Alternative(s);
            alt.setId(i);
            if (alt.getName().equalsIgnoreCase(correctAlternative)) {
                correct = alt;
            }
            list.add(alt);
            i++;
        }
        question = new Question(questionString, list, correct);
    }
    
    
}
