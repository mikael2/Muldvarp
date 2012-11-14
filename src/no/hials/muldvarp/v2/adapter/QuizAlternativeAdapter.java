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
import no.hials.muldvarp.v2.domain.Alternative;

public class QuizAlternativeAdapter extends ArrayAdapter<Alternative> {
    private LayoutInflater mInflater;
    private List<Alternative> alternatives;
    private Context context;
    private int resource;

    public QuizAlternativeAdapter(Context context, int resource, int textViewResourceId, List<Alternative> questions) {
        super(context, textViewResourceId, questions);
        mInflater = LayoutInflater.from(context);
        this.alternatives = questions;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return alternatives.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuizAlternativeAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
            holder = new QuizAlternativeAdapter.ViewHolder();
            holder.questionText = (TextView) convertView.findViewById(R.id.resultQuestionText);
            holder.questionResultText = (TextView) convertView.findViewById(R.id.resultText);
            holder.questionAnswerText = (TextView) convertView.findViewById(R.id.resultAnswerText);

            convertView.setTag(holder);
        } else {
            holder = (QuizAlternativeAdapter.ViewHolder) convertView.getTag();
        }

        //Get question based on position from the getView call
        Alternative alternative = alternatives.get(position);
        //Don't set anything if the question ain't there
        if (alternative != null) {
            //Set name of the question
            if (alternative.getName() != null) {
                holder.questionText.setText(alternative.getName());
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