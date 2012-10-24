/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Alternative;
import no.hials.muldvarp.v2.domain.Question;

/**
 * This class defines an Activity used for Quiz-functionality. Should
 * encapsulate a fragment, but NYI
 *
 * @author johan
 */
public class TestQuizActivity extends MuldvarpActivity{
    
    //Global Variables
    ListView listView;
    List<Question> questions = new ArrayList<Question>();
    int currentQuestion;
    
    private String[] mStrings = {"hurr", "durr", "murr"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set Layout from XML-file
        setContentView(R.layout.quiz_activity_test);
       
        //Get ListView and set layout mode
        listView = (ListView) findViewById(R.id.QuizListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        getCurrentQuestion();
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getActionBar().setSubtitle("QUIZ!");
    }
    
    public void  getCurrentQuestion(){
        
        ArrayList items = new ArrayList();
        items.add("For fun");
        items.add("For the money");
        items.add("No reason");
        items.add("None of the above");
        makeQuizData("Why are we here?", items, "No reason");  
        
        TextView textView = (TextView) findViewById(R.id.QuestionText);
        textView.setText(questions.get(0).getName());
        
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, items));
    }
    
    public void makeQuizData(String question, List<String> alternatives, String correctAlternative) {
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
        Question q = new Question(question, list, correct);
        questions.add(q);
    }
    
}
