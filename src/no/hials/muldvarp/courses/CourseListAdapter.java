/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseListAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private ArrayList items;
    private Context context;
    private int resource;
    private boolean showdetails;
    
    public CourseListAdapter(Context context, int resource, int textViewResourceId, ArrayList items, boolean showdetails) {
        super(context, textViewResourceId, items);
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.context = context;
        this.resource = resource;
        this.showdetails = showdetails;
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
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.detail = (TextView) convertView.findViewById(R.id.detail);

                convertView.setTag(holder);
        } else {
                holder = (ViewHolder) convertView.getTag();
        }

        Course c = (Course)items.get(position);

        holder.name.setText(c.getName());
        
        if (showdetails == true) {
            holder.detail.setText(c.getDetail());
        }
                
//        holder.icon.setImageBitmap(c.getThumb());
        
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView detail;
    }
}
