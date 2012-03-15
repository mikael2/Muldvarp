/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.courses.CourseListAdapter;
import no.hials.muldvarp.domain.Course;
import no.hials.muldvarp.entities.LibraryItem;
import no.hials.muldvarp.utility.DrawableManager;

/**
 *
 * @author Nospherus
 */
public class GridLibraryAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private List items;
    private Context context;
    private int resource;
    private boolean showdetails;
    private List orig_items;
    private LibraryItem l;
    
    DrawableManager dm = new DrawableManager();
    
    public GridLibraryAdapter(Context context, int resource, int textViewResourceId, List items, boolean showdetails) {
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
        System.out.println(items.size());
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GridLibraryAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
            holder = new GridLibraryAdapter.ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (GridLibraryAdapter.ViewHolder) convertView.getTag();
        }

        l = (LibraryItem)items.get(position);

        
        //set text from layout
        holder.name.setText(l.getTitle());
        
        holder.icon.setImageResource(R.drawable.ic_launcher); // default app icon
        
        if (l.getThumbURL() != null && !l.getThumbURL().equals("")) {            
            dm.fetchDrawableOnThread(l.getThumbURL(), holder.icon); 
        } else {
            holder.icon.setImageResource(R.drawable.ic_launcher); // default app icon
        }
        
        return convertView;
    }
    
     static class ViewHolder {
        ImageView icon;
        TextView name;
    }
     
     public LibraryItem getLibraryItem(){
         return l;
     }
}
