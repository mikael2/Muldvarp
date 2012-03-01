/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import no.hials.muldvarp.domain.ObligatoryTask;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseDetailHandinListAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private ArrayList items;
    private Context context;
    private int resource;
    
    public CourseDetailHandinListAdapter(Context context, int resource, int textViewResourceId, ArrayList items) {
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
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ObligatoryTask h = (ObligatoryTask)items.get(position);
        Date dueDate = new Date(h.getDueDate());
        
        holder.checkbox.setChecked(h.getDone());

        holder.name.setText(h.getName() + dueDate.getMonth() + dueDate.getDay() + dueDate.getHours() + dueDate.getMinutes());
        
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        CheckBox checkbox;
    }
}
