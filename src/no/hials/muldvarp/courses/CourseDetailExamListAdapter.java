/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import no.hials.muldvarp.v2.domain.Exam;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseDetailExamListAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private ArrayList items;
    private Context context;
    private int resource;
    
    public CourseDetailExamListAdapter(Context context, int resource, int textViewResourceId, ArrayList items) {
        super(context, textViewResourceId, items);
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.context = context;
        this.resource = resource;
    }
    
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Exam e = (Exam)items.get(position);
        //Date date = new Date(e.getExamDate());
        Date date = e.getExamDate();
        if(date==null) {
            date = new Date(0);
        }

        holder.name.setText(e.getName() + " Tid: " + date.getDay() + "." + date.getMonth() + " kl: " + date.getHours() + ":" + date.getMinutes());
        
        return convertView;
    }

    static class ViewHolder {
        TextView name;
    }
}
