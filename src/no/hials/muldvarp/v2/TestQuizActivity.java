/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Alternative;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Question;
import no.hials.muldvarp.v2.domain.Quiz;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.fragments.QuizQuestionFragment;

/**
 * This class defines an Activity used for Quiz-functionality. Should
 * encapsulate a fragment, but NYI
 *
 * @author johan
 */
public class TestQuizActivity extends MuldvarpActivity{
    
    //Global Variables
    ListView listView;
    Quiz quiz;
    List<Question> questions = new ArrayList<Question>();
    int currentQuestionNumber;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set Layout from XML-file
        setContentView(R.layout.quiz_activity_main);
        
        //See if the Activity was started with an Intent that included a Domain object
        if(getIntent().hasExtra("Domain")) {
            domain = (Domain) getIntent().getExtras().get("Domain");
            activityName = domain.getName();
            quiz = (Quiz) domain;
            
            TextView quizName = (TextView) findViewById(R.id.QuizNameText);
            quizName.setText(quiz.getName());
            setOnClickListeners();
        } 
       

    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getActionBar().setSubtitle("QUIZ!");
    }
    
    private void setOnClickListeners(){
        
        Button startQuizButton = (Button) findViewById(R.id.StartQuizButton);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startQuiz();
            }
        });
        
    }
    
    public void startQuiz(){
        setContentView(R.layout.activity_quiz_question_holder);
        Button nextQuestionButton = (Button) findViewById(R.id.QuizNextButton);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addFragmentToStack();
            }
        });
        
        Button prevQuestionButton = (Button) findViewById(R.id.QuizPreviousButton);
        prevQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startQuiz();
            }
        });
        //Get ListView and set layout mode
        MuldvarpFragment newFragment = new QuizQuestionFragment(new Question("dera", null, null));
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.QuizQuestionFragmentHolder, newFragment).commit();
        
    }
    
    public void addFragmentToStack() {

        // Instantiate a new fragment.
        MuldvarpFragment newFragment = new QuizQuestionFragment(new Question("dera", null, null));
                
        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_right_exit);
        ft.replace(R.id.QuizQuestionFragmentHolder, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    
}
