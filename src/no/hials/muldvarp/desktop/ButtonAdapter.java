package no.hials.muldvarp.desktop;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import java.util.List;

/**
 *
 * @author mikael
 */
public class ButtonAdapter extends BaseAdapter {
    
    List<IntentLink> links;
    Context context;
    
    public ButtonAdapter(Context ctx, List<IntentLink> links) {
        this.links = links;
        this.context = ctx;
    }

    
    public int getCount() {
        return links.size();
    }

    public Object getItem(int index) {
        return links.get(index);
    }

    public long getItemId(int index) {
        return index;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes  
            btn = new Button(context);
            btn.setLayoutParams(new GridView.LayoutParams(100, 55));
            btn.setPadding(8, 8, 8, 8);
        } else {
            btn = (Button) convertView;
        }
        
        btn.setText(links.get(position).getNameStringResource());
        // filenames is an array of strings  
        btn.setTextColor(Color.WHITE);
        //btn.setBackgroundResource(R.drawable.button);
        btn.setId(position);

        return btn;

    }

}
