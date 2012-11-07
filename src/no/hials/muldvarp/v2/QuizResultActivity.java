/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.adapter.QuizResultAdapter;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Quiz;

/**
 * This activity handles review and/or submission of Quiz results data.
 * 
 * @author johan
 */
public class QuizResultActivity extends MuldvarpActivity {
    
    //Global variables
    Quiz quiz;
    View mainQuizView;
    View holderQuizView;
    ListView listView;    
    ListView answerView; //ListView holding answers supplied by user
    ListView resultView;//ListView holding actual answers
    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();
    
    /**
     * Below is test stuff
     */
    private static final String[] LIST_ANS = new String[] {
            "Answer 1",
            "Answer 2",
            "Answer 3",
            "Answer 4",
            "Answer 5",
            "Answer 6"
    };
    private static final String[] LIST_RES = new String[] {
            "Result 1",
            "Result 2",
            "Result 3",
            "Result 4",
            "Result 5",
            "Result 6"
    };
    
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set Layout from XML-file
        setContentView(R.layout.quiz_activity_main);
        
        //See if the Activity was started with an Intent that included a Domain object
        if(getIntent().hasExtra("Quiz")) {
            domain = (Domain) getIntent().getExtras().get("Quiz");
            activityName = domain.getName();
            quiz = (Quiz) domain;
            
            setContentView(R.layout.activity_quiz_question_ver);
            TextView resultTextView = (TextView) findViewById(R.id.QuizResultsText);
            resultTextView.setText("Dette er dine svar. Du kan bekrefte de i listen under, og scrolle ned for å vise/sende resultat.");
            answerView = (ListView) findViewById(R.id.list_answer);
            resultView = (ListView) findViewById(R.id.list_results);
            final ArrayAdapter<String> adapterAns = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, LIST_ANS);
            final ArrayAdapter<String> adapterRes = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_quizresult, LIST_RES);
            answerView.setAdapter(adapterAns);
            resultView.setAdapter(new QuizResultAdapter(this,
                    R.layout.layout_quizresult,
                    R.id.text,
                    quiz.getQuestions()));
            resultView.setRotationY(-90f);
            Button starter = (Button) findViewById(R.id.revealAnswerButton);
            starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flipit();
            }
        });
            
        }        
    }
    
    /**
     * This function animates the list.
     */
    private void flipit() {
        final ListView visibleList;
        final ListView invisibleList;
        if (answerView.getVisibility() == View.GONE) {
            visibleList = resultView;
            invisibleList = answerView;
        } else {
            invisibleList = resultView;
            visibleList = answerView;
        }
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }
}
