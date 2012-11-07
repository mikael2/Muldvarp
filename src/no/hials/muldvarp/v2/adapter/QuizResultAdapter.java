/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.utility.DrawableManager;
import no.hials.muldvarp.v2.domain.Question;

public class QuizResultAdapter extends ArrayAdapter<Question> {
    private LayoutInflater mInflater;
    private List<Question> questions;
    private Context context;
    private int resource;
    DrawableManager dm = new DrawableManager();

    public QuizResultAdapter(Context context, int resource, int textViewResourceId, List<Question> questions) {
        super(context, textViewResourceId, questions);
        mInflater = LayoutInflater.from(context);
        this.questions = questions;
        this.context = context;
        this.resource = resource;
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
        Question question = questions.get(position);
        //Don't set anything if the question ain't there
        if (question != null) {
            //Set name of the question
            if (question.getName() != null) {
                holder.questionText.setText(question.getName());
            }
            holder.questionAnswerText.setText("HERRFF");
            holder.questionResultText.setText("OK!");
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView questionText;
        TextView questionResultText;
        TextView questionAnswerText;
    }
}