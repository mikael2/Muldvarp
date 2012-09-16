/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Alternative;
import no.hials.muldvarp.v2.domain.Question;

/**
 * This class defines an Activity used for Quiz-functionality.
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
        
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        
        
        for(int i = 0; i < questions.size(); i++) {
            LinearLayout question_layout = new LinearLayout(this);
            question_layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            TextView text = new TextView(this);
            text.setText(questions.get(i).getName());
            layout.addView(text);
            final Question question = questions.get(i);
            
            LinearLayout alts = new LinearLayout(this);
            for(int k = 0; k < questions.get(i).getAlternatives().size(); k++) {
                LinearLayout answ = new LinearLayout(this);
                answ.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 300));
                answ.setOrientation(LinearLayout.HORIZONTAL);
                
                Alternative alternative = questions.get(i).getAlternatives().get(k);
                TextView quest = new TextView(this);
                quest.setText(alternative.getName());
                CheckBox cb = new CheckBox(this);
                cb.setId(alternative.getId());
                
                cb.setOnClickListener(new View.OnClickListener() {
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
                });
                
                answ.addView(quest);
                answ.addView(cb);
                alts.addView(answ);
            }
            
            question_layout.addView(alts);
            layout.addView(question_layout);
        }
        
        setContentView(layout);
        
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
        
        List<Alternative> alt = new ArrayList<Alternative>();
        Alternative a1 = new Alternative("HEEEEYAAAA");
        a1.setId(1);
        alt.add(a1);
        Alternative a2 = new Alternative("HEEEEYAAAA");
        a2.setId(2);
        alt.add(a2);
        Alternative a3 = new Alternative("murr");
        a3.setId(3);
        alt.add(a3);
        Alternative a4 = new Alternative("derf");
        a4.setId(4);
        alt.add(a4);
        Alternative a5 = new Alternative("Herf");
        a5.setId(5);
        alt.add(a5);
        
        Question q = new Question("Hvilket svaralternativ er riktig?", alt, a2);
        questions.add(q);
    }
}
