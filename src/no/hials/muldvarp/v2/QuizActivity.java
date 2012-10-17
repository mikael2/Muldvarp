/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import no.hials.muldvarp.v2.domain.Alternative;
import no.hials.muldvarp.v2.domain.Question;
import no.hials.muldvarp.v2.domain.QuizAnswers;

/**
 * This class defines an Activity used for Quiz-functionality. Should
 * encapsulate a fragment, but NYI
 *
 * @author johan
 */
public class QuizActivity extends Activity {

    //Global Variables
    List<Question> questions = new ArrayList<Question>();
    MuldvarpService mService;
    private boolean mBound;
    QuizAnswers result;
    HashMap<CheckBox, Question> boxes;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        boxes = new HashMap();
        Intent intent = new Intent(this, MuldvarpService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        //Not in use
        //setContentView(R.layout.activity_quiz);
        ArrayList a = new ArrayList();
        a.add("For fun");
        a.add("For the money");
        a.add("No reason");
        a.add("None of the above");
        makeQuizData("Why are we here?", a, "No reason");

        //Set up layout inside a ScrollView
        //Main Layout to hold the scrollview, somehow needed when doing this outside XML files
        LinearLayout mainLayout = new LinearLayout(this);
        //Set up scrollview and make sure it fills it's parent entirely
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //Loop through the questions in the array
        for (int i = 0; i < questions.size(); i++) {

            //Create TextView to hold question string
            TextView text = new TextView(this);
            text.setText(questions.get(i).getName());
            text.setTextSize(22);
            linearLayout.addView(text);
            final Question question = questions.get(i);

            //Loop through the alternatives in the array
            for (int k = 0; k < questions.get(i).getAlternatives().size(); k++) {

                //Get alternatives and create a checkbox for each alternative with ID and text
                Alternative alternative = questions.get(i).getAlternatives().get(k);
                CheckBox checkBox = new CheckBox(this);
                checkBox.setId(alternative.getId());
                checkBox.setText(alternative.getName());
                checkBox.setTextSize(16);
                boxes.put(checkBox, question);
                linearLayout.addView(checkBox);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on clicks, depending on whether it's now checked
                        if (((CheckBox) v).isChecked()) {
//                            if (checkAnswer(question, v.getId())) {
                                result.addQuestion(question, question.getAlternative(v.getId()));
                                for(CheckBox cb : boxes.keySet()){
                                    if(boxes.get(cb) == question && cb.getId() != v.getId()){
                                        cb.setChecked(false);
                                    }
                                }
//                                Toast.makeText(v.getContext(), "Correct!", Toast.LENGTH_LONG).show();
//                            } else {
//                                Toast.makeText(v.getContext(), "Wrong!", Toast.LENGTH_LONG).show();
//                            }
                        }
                        else{
                            
                        }
                    }
                }); //end onclick listener

            } //end nested for loop            
        } //end main for loop
        Button submitButton = new Button(this);
        submitButton.setText("Lagre");
        submitButton.setOnClickListener(new OnClickListener(){

            public void onClick(View view) {
                for(Question q : result.getResult().keySet()){
                    System.out.println(result.getName() + "\n" + q.getName() + ": " + result.getResult().get(q));
                }
            }
        });
        linearLayout.addView(submitButton);
        scrollView.addView(linearLayout);
        mainLayout.addView(scrollView);
        setContentView(mainLayout);

    }

    private boolean checkAnswer(Question q, int altid) {
        if (q.getAnswer() != null) {
            if (q.getAnswer().getId() == altid) {
                return true;
            }
        }
        return false;
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
    
         private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            result = new QuizAnswers(mService.getUser().getName());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
