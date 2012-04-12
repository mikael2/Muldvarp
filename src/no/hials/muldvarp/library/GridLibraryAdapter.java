package no.hials.muldvarp.library;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.LibraryItem;
import no.hials.muldvarp.utility.DrawableManager;

/**
 *
 * @author Nospherus
 */
public class GridLibraryAdapter extends ArrayAdapter<LibraryItem> {
    private LayoutInflater mInflater;
    private Context context;
    private int resource;
    private boolean showdetails;
    private LibraryItem l;
    
    DrawableManager dm = new DrawableManager();
    
    public GridLibraryAdapter(Context context, int resource, int textViewResourceId,  boolean showdetails) {
        super(context, textViewResourceId,new ArrayList<LibraryItem>());
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.resource = resource;
        this.showdetails = showdetails;
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

        l = getItem(position);

        
        //set text from layout
        holder.name.setText(l.getTitle());
        
        holder.icon.setImageResource(R.drawable.ic_launcher); // default app icon
        
        if (l.getThumbURL() != null && !l.getThumbURL().equals("")) {    
            try {
                URL url = new URL(l.getThumbURL());
                dm.fetchDrawableOnThread(l.getThumbURL(), holder.icon); 
            } catch(Throwable t) {
               Log.w("Failed to load thumbnail", l.getThumbURL(),t);
               holder.icon.setImageResource(R.drawable.ic_launcher); // default app icon               
            }
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
