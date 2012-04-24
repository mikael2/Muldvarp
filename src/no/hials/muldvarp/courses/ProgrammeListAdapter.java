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
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.domain.Course;
import no.hials.muldvarp.domain.Programme;
import no.hials.muldvarp.utility.DrawableManager;

/**
 *
 * @author kristoffer
 */
public class ProgrammeListAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private List items;
    private Context context;
    private int resource;
    private boolean showdetails;
    private List orig_items;
    
    DrawableManager dm = new DrawableManager();
    
    public ProgrammeListAdapter(Context context, int resource, int textViewResourceId, List items, boolean showdetails) {
        super(context, textViewResourceId, items);
        mInflater = LayoutInflater.from(context);
        this.orig_items = items;
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
        ProgrammeListAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
            holder = new ProgrammeListAdapter.ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.detail = (TextView) convertView.findViewById(R.id.detail);

            convertView.setTag(holder);
        } else {
            holder = (ProgrammeListAdapter.ViewHolder) convertView.getTag();
        }

        Programme c = (Programme)items.get(position);

        
        //set text from layout
        holder.name.setText(c.getName());
        
        holder.icon.setImageResource(R.drawable.ic_launcher); // default app icon
        
        if (showdetails == true) {
            holder.detail.setText(c.getDetail());
        }
        
        if (c.getImageurl() != null && !c.getImageurl().equals("")) {            
            dm.fetchDrawableOnThread(c.getImageurl(), holder.icon); 
        } else {
            holder.icon.setImageResource(R.drawable.ic_launcher); // default app icon
        }
        
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView detail;
    }
    
    public void filter(CharSequence filter)
    {
        ArrayList filtered = new ArrayList();
        
        for (Programme c : (ArrayList<Programme>)orig_items)
        {
            if (c.getName().toLowerCase().contains(filter.toString().toLowerCase()))
            {
                filtered.add(c);
            }
        }
        
        this.items = filtered;
        
        if("".equals(filter.toString())) {
            this.items = this.orig_items;
        }
        
        notifyDataSetChanged();  
    }
}
