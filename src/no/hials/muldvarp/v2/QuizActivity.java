/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Question;
import no.hials.muldvarp.v2.domain.Quiz;
import no.hials.muldvarp.v2.fragments.QuizQuestionFragment;

/**
 * This class defines an Activity used for Quiz-functionality. 
 *
 * @author johan
 */
public class QuizActivity extends MuldvarpActivity{
    
    //Global Variables
    View mainQuizView;
    View holderQuizView;
    ListView listView;
    Quiz quiz;
    List<Question> questions = new ArrayList<Question>();
    int currentQuestionNumber;
    //Fragments
    ArrayList<QuizQuestionFragment> questionFragments;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                
        //See if the Activity was started with an Intent that included a Domain object
        if(getIntent().hasExtra("Domain")) {
            domain = (Domain) getIntent().getExtras().get("Domain");
            activityName = domain.getName();
            quiz = (Quiz) domain;
            setupMainQuizPage();
        }        
    }
    
    private void setupMainQuizPage(){
        //Set Layout from XML-file
        LayoutInflater inflator = getLayoutInflater();
        mainQuizView =  inflator.inflate(R.layout.quiz_activity_main, null, false);
        mainQuizView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in)); 
        setContentView(mainQuizView);
        TextView quizName = (TextView) findViewById(R.id.quizNameText);
        quizName.setText(quiz.getName());

        TextView quizTypeText = (TextView) findViewById(R.id.quizTypeText);
        quizTypeText.setText(R.string.quizTypeText);
        TextView quizType = (TextView) findViewById(R.id.quizTypeText2);
        if(quiz.getQuizType() != null){
            quizType.setText(quiz.getQuizType().getName());
        } else {
            quizType.setText(R.string.quizTypeUnknownText);                        
        }           

        if(quiz.getDescription() != null){
            TextView quizDescription = (TextView) findViewById(R.id.quizNameDescription);
            quizDescription.setText(quiz.getDescription());
        } 
        TextView questionAmount = (TextView) findViewById(R.id.questionAmount);
        questionAmount.setText(R.string.quizNumberOfQuestionsText);
        TextView questionNumber = (TextView) findViewById(R.id.questionAmountNo);
        questionNumber.setText(String.valueOf(quiz.getQuestions().size()));
        Button startQuizButton = (Button) findViewById(R.id.startQuizButton);
        if (quiz.getQuestions().size() > 0) {
            startQuizButton.setText(R.string.quizStartButtonText);
            setOnClickListeners();
        } else {                
            startQuizButton.setText(R.string.quizNoQuestionsText);
            startQuizButton.setClickable(false);
        }
    }
    
    private void setOnClickListeners(){        
        Button startQuizButton = (Button) findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startQuiz();
            }
        });        
    }
    
    public void startQuiz(){
        //Change content view with animation
        LayoutInflater inflator = getLayoutInflater();
        holderQuizView =  inflator.inflate(R.layout.activity_quiz_question_holder, null, false);
        holderQuizView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));        
        setContentView(holderQuizView);
        //Get fragments        
        currentQuestionNumber = 0;
        if (!quiz.getQuestions().isEmpty()) {
            questionFragments = new ArrayList<QuizQuestionFragment>();
            fillQuestionFragmentList();                        
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.QuizQuestionFragmentHolder, questionFragments.get(currentQuestionNumber)).commit();
        }
        
        Button backToMainQuizButton = (Button) findViewById(R.id.backToMainQuizActivityButton);
        backToMainQuizButton.setText(R.string.quizBackToMainQuizButtonText);
         backToMainQuizButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showReturnDialog(); 
            }
        });
        final Button prevQuestionButton = (Button) findViewById(R.id.quizPreviousButton);
        prevQuestionButton.setEnabled(false);
        prevQuestionButton.setText(R.string.quizPreviousButtonText);
        prevQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentQuestionNumber > 0) {
                    Button nextQuestionButton = (Button) findViewById(R.id.quizNextButton);
                    nextQuestionButton.setText(R.string.quizNextButtonText);
                    onBackPressed();
                    if (currentQuestionNumber == 0){
                    prevQuestionButton.setEnabled(false);
                    }     
                }            
            }
        });
        final Button nextQuestionButton = (Button) findViewById(R.id.quizNextButton);
        nextQuestionButton.setText(R.string.quizNextButtonText);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {                
                if (currentQuestionNumber < (quiz.getQuestions().size() -1)) {
                    currentQuestionNumber++;
                    prevQuestionButton.setEnabled(true);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.setCustomAnimations(R.anim.fragment_slide_left_enter,
//                            R.anim.fragment_slide_left_exit,
//                            R.anim.fragment_slide_right_enter,
//                            R.anim.fragment_slide_right_exit);
                    ft.replace(R.id.QuizQuestionFragmentHolder, questionFragments.get(currentQuestionNumber));
                    ft.addToBackStack(null);
                    ft.commit();
                    if (currentQuestionNumber >= quiz.getQuestions().size()-1){
                        nextQuestionButton.setText(R.string.quizGoToResultsButtonText);
                    }
                } else if (currentQuestionNumber >= quiz.getQuestions().size()-1){
                    Intent quizResultsIntent = new Intent(getApplicationContext(), QuizResultActivity.class);
                    quizResultsIntent.putExtra("Quiz", quiz);
                    startActivity(quizResultsIntent);
                }
            }
        }); 
    }

    @Override
    public void onBackPressed() {
        currentQuestionNumber--;
        super.onBackPressed();
    }
    
    private void fillQuestionFragmentList(){
        //Only fill question fragment list if it hasn't been filled already        
        if(questionFragments.isEmpty()){
            questionFragments = new ArrayList<QuizQuestionFragment>();            
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                Question tempQuestion = quiz.getQuestions().get(i);
                QuizQuestionFragment tempFrag = new QuizQuestionFragment(tempQuestion);
                tempFrag.setQuestionAmount(quiz.getQuestions().size());
                tempFrag.setQuestionNo(i+1);                        
                questionFragments.add(tempFrag);
            }            
        }        
    }    
    
    public void emptyQuestionFragmentList(){
        if(questionFragments != null){
            questionFragments.clear();
        }    
    }
    
    /**
     * Void method containing functionality to construct a dialog.
     */
    public void showReturnDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.quizResultBackToQuizText).setTitle(R.string.quizResultBackToQuizPrompt);        
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               //DO NOTHING
           }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               setupMainQuizPage();       
           }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
