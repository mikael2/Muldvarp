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
    
    DrawableManager dm = new DrawableManager();
    
    ImageView icon;
    TextView name;
    TextView detail;
    
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
        //ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
            //holder = new ViewHolder();
            icon = (ImageView) convertView.findViewById(R.id.icon);
            name = (TextView) convertView.findViewById(R.id.name);
            detail = (TextView) convertView.findViewById(R.id.detail);

            //convertView.setTag(holder);
        } else {
            //holder = (ViewHolder) convertView.getTag();
        }

        Course c = (Course)items.get(position);

        name.setText(c.getName());
        
        if (showdetails == true) {
            detail.setText(c.getDetail());
        }
        
        if (c.getImageurl() != null) {            
//            Drawable image = dm.fetchDrawable(c.getImageurl());
//            icon.setImageDrawable(image);
            
            dm.fetchDrawableOnThread(c.getImageurl(), icon);
            
//            new DownloadImageTask().execute(c.getDetail());
            
//            holder.icon.setImageURI(c.getIcon());
        } else {
            icon.setImageResource(R.drawable.ic_launcher); // default app icon
        }
        
        return convertView;
    }

//    static class ViewHolder {
//        
//    }
//    
//    private Bitmap getImageBitmap(String url) {
//            Bitmap bm = null; 
//            try { 
//                URL aURL = new URL(url); 
//                URLConnection conn = aURL.openConnection(); 
//                conn.connect(); 
//                InputStream is = conn.getInputStream(); 
//                BufferedInputStream bis = new BufferedInputStream(is); 
//                bm = BitmapFactory.decodeStream(bis);
//                if (is != null) {
//                    is.close();
//                }
//                if (bis != null) {
//                    bis.close();
//                }
//           } catch (IOException e) { 
//               Log.e("Error getting image", e.toString()); 
//           } 
//           return bm; 
//        }
//    
//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//         protected Bitmap doInBackground(String... urls) {
//             return getImageBitmap(urls[0]);
//         }
//
//         protected void onPostExecute(Bitmap result) {
//             icon.setImageBitmap(result);
////             setImage(result);
//         }
//    }
}
