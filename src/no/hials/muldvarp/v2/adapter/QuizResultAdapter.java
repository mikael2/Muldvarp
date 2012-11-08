/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.utility.DrawableManager;
import no.hials.muldvarp.v2.domain.Alternative;
import no.hials.muldvarp.v2.domain.Question;

public class QuizResultAdapter extends ArrayAdapter<Question> {
    private LayoutInflater mInflater;
    private List<Question> questions;
    private Context context;
    private int resource;
    boolean verify;

    public QuizResultAdapter(Context context, int resource, int textViewResourceId, List<Question> questions, boolean verify) {
        super(context, textViewResourceId, questions);
        mInflater = LayoutInflater.from(context);
        this.questions = questions;
        this.context = context;
        this.resource = resource;
        this.verify = verify;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuizResultAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
            holder = new QuizResultAdapter.ViewHolder();
            holder.questionText = (TextView) convertView.findViewById(R.id.resultQuestionText);
            holder.questionResultText = (TextView) convertView.findViewById(R.id.resultText);
            holder.questionAnswerText = (TextView) convertView.findViewById(R.id.resultAnswerText);

            convertView.setTag(holder);
        } else {
            holder = (QuizResultAdapter.ViewHolder) convertView.getTag();
        }

        //Get question based on position from the getView call
        System.out.println("Getting question from position " + position);
        Question question = questions.get(position);
        //Don't set anything if the question ain't there
        if (question != null) {
            //Set name of the question
            if (question.getName() != null) {
                holder.questionText.setText(question.getName());
            }
            String answerText = "";
            if(verify){
                boolean questionChecksOut = true;
                for (int i = 0; i < question.getAlternatives().size(); i++) {      
                    Alternative currentAlt = question.getAlternatives().get(i);
                    //If an alternative is chosen, but not correct it is red
                    if (currentAlt.isIsChoosen() && !currentAlt.isIsCorrect()) {
                        answerText += getHTMLColorString(currentAlt, "red") +"<br> ";
                        questionChecksOut = false;
                    //If an alternative is chosen, and correct, it is green    
                    } else if (currentAlt.isIsChoosen() && currentAlt.isIsCorrect()) {
                        answerText += getHTMLColorString(currentAlt, "green") +"<br> ";  
                    //If an alternative is not chosen, but correct, it is orange    
                    } else if (!currentAlt.isIsChoosen() && currentAlt.isIsCorrect()) {
                        answerText += getHTMLColorString(currentAlt, "#FF9900") +"<br> "; 
                        questionChecksOut = false;
                    } else {
                        answerText += getHTMLColorString(currentAlt, "grey") +"<br> ";
                    }
                    if (questionChecksOut) {
                        holder.questionResultText.setText("OK");
                    } else {
                        holder.questionResultText.setText(":(");
                    }
                    
                }
            } else {
                for (int i = 0; i < question.getAlternatives().size(); i++) {       
                    Alternative currentAlt = question.getAlternatives().get(i);
                    if (currentAlt.isIsChoosen()) {
                        answerText += getHTMLColorString(currentAlt, "#FF9900") +"<br> ";
                    } else {
                        answerText += getHTMLColorString(currentAlt, "grey") +"<br> ";
                    }
                }
            }
            holder.questionAnswerText.setText(Html.fromHtml(answerText));
            
        }

        return convertView;
    }
    public String getHTMLColorString(Alternative alternative, String color){
        String retVal = "<font color="+color+">"+alternative.getName()+"</font>";
        return retVal;
    }

    static class ViewHolder {
        ImageView icon;
        TextView questionText;
        TextView questionResultText;
        TextView questionAnswerText;
    }
}