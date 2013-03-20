/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.AlertDialog;
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
public class QuizMainActivity extends MuldvarpActivity{
    
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

            TextView quizDescription = (TextView) findViewById(R.id.quizNameDescription);
            if((quiz.getDescription() != null) 
                    && !quiz.getDescription().equalsIgnoreCase("")
                    && !quiz.getDescription().equalsIgnoreCase("null")){
                quizDescription.setText(quiz.getDescription());
            } else {
                quizDescription.setText(R.string.nodescription);
            }
            TextView questionAmount = (TextView) findViewById(R.id.questionAmount);
            questionAmount.setText(R.string.quizNumberOfQuestionsText);
            TextView questionNumber = (TextView) findViewById(R.id.questionAmountNo);
            questionNumber.setText(String.valueOf(quiz.getQuestions().size()));
            TextView quizResultText = (TextView) findViewById(R.id.quizResultText);
            quizResultText.setText(R.string.quizResultText);
            TextView quizResult = (TextView) findViewById(R.id.quizResultText2);
            quizResult.setText(getQuizStatus());
            Button startQuizButton = (Button) findViewById(R.id.startQuizButton);           
            
            if (quiz.getQuestions() != null
                    && quiz.getQuestions().isEmpty()) {
                showErrorDialog(getString(R.string.quizNoQuestionsText));                startQuizButton.setClickable(false);
                
            } else if (quiz.validateFormat()){
                startQuizButton.setText(R.string.quizStartButtonText);
                setOnClickListeners();
            } else {
                showErrorDialog(getString(R.string.quizInvalidFormatText));
                startQuizButton.setClickable(false);
            }
        }        
    }
    
    /**
     * Void method containing functionality to construct a dialog.
     */
    public void showErrorDialog(String message){
        System.out.println("showing dialog with message " + message);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(R.string.warning);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               finish();
           }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
        
    public String getQuizStatus(){        
        return getString(R.string.quizResultNotTaken);
    }
    
    private void setOnClickListeners(){        
        Button startQuizButton = (Button) findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent quizActivityIntent = new Intent(getApplicationContext(), QuizActivity.class);
                quizActivityIntent.putExtra("Domain", quiz);
                startActivity(quizActivityIntent);
            }
        });        
    }    
}
