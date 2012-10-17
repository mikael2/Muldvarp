/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.v2.domain.Alternative;
import no.hials.muldvarp.v2.domain.Question;

/**
 * This class defines an Activity used for Quiz-functionality. Should encapsulate a fragment, but NYI
 *
 * @author johan
 */
public class QuizActivity extends Activity {

    //Global Variables
    List<Question> questions = new ArrayList<Question>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Not in use
        //setContentView(R.layout.activity_quiz);

        makeTestData();

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
        for(int i = 0; i < questions.size(); i++) {

            //Create TextView to hold question string
            TextView text = new TextView(this);
            text.setText(questions.get(i).getName());
            text.setTextSize(22);
            linearLayout.addView(text);
            final Question question = questions.get(i);

            //Loop through the alternatives in the array
            for(int k = 0; k < questions.get(i).getAlternatives().size(); k++) {

                //Get alternatives and create a checkbox for each alternative with ID and text
                Alternative alternative = questions.get(i).getAlternatives().get(k);
                CheckBox checkBox = new CheckBox(this);
                checkBox.setId(alternative.getId());
                checkBox.setText(alternative.getName() + checkAnswer(question, alternative.getId()));
                checkBox.setTextSize(16);
                linearLayout.addView(checkBox);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on clicks, depending on whether it's now checked
                        if (((CheckBox) v).isChecked()) {
                            if(checkAnswer(question, v.getId())) {
                                Toast.makeText(v.getContext(), "Correct!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(v.getContext(), "Wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }); //end onclick listener

            } //end nested for loop
        } //end main for loop

        scrollView.addView(linearLayout);
        mainLayout.addView(scrollView);
        setContentView(mainLayout);

    }

    private boolean checkAnswer(Question q, int altid) {
        if(q.getAnswer() != null) {
            if(q.getAnswer().getId() == altid) {
                return true;
            }
        }
        return false;
    }

    public void makeTestData(){

        for (int i = 1; i < 4; i++) {

            List<Alternative> alternatives = new ArrayList<Alternative>();
            for (int n = 1; n < 6; n++) {
                Alternative currentAlternative = new Alternative("Svaralternativ " + n);
                currentAlternative.setId(i);
                alternatives.add(currentAlternative);
            }

            Alternative correctAnswer = new Alternative("Svaralternativ " + i);

            Question currentQuestion = new Question("Hvilket svaralternativ er riktig?", alternatives, correctAnswer);
            questions.add(currentQuestion);

        }

    }
}
