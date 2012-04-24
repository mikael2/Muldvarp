package no.hials.muldvarp.desktop;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;
import no.hials.muldvarp.R;
import no.hials.muldvarp.video.VideoMainActivity;
import no.hials.muldvarp.courses.CourseActivity;
import no.hials.muldvarp.directory.DirectoryActivity;
import no.hials.muldvarp.library.LIBMainscreen;
import no.hials.muldvarp.news.NewsActivity;

/**
 *
 * @author mikael
 */
public class DesktopFragment extends Fragment {

    ImageAdapter adapter;
    
    public DesktopFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View retVal = inflater.inflate(R.layout.desktop_fragment, container, false);

        GridView gridview = (GridView) retVal.findViewById(R.id.gridview);
    
        adapter = new ImageAdapter(getActivity());
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentLink link = adapter.getItem(position);
                Intent myIntent = new Intent(view.getContext(), link.getAction());
                startActivityForResult(myIntent, 0);
            }
        });

        return retVal;
    }

    class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return links.length;
        }

        public IntentLink getItem(int position) {
            return links[position];
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View retVal = null;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                retVal = li.inflate(R.layout.desktop_icon, null);
                ImageView imageView = (ImageView) retVal.findViewById(R.id.grid_item_image);
                TextView  textView  = (TextView) retVal.findViewById(R.id.grid_item_label);

                imageView.setImageResource(links[position].getImageResource());
                textView.setText(links[position].getNameStringResource());
            } else {
                retVal = convertView;
            }

            return retVal;
        }
        
        private IntentLink[] links = {
            //new IntentLink(R.string.icon_directory, R.drawable.icon_directory, DirectoryActivity.class),
            new IntentLink(R.string.icon_news,      R.drawable.icon_news,      NewsActivity.class),
            new IntentLink(R.string.icon_courses,   R.drawable.icon_course,    CourseActivity.class),            
            new IntentLink(R.string.icon_video,     R.drawable.icon_video,     VideoMainActivity.class),
            new IntentLink(R.string.icon_directory, R.drawable.icon_directory, DirectoryActivity.class),
            new IntentLink(R.string.icon_library,   R.drawable.icon_library,   LIBMainscreen.class)
            
        };
     
    }        
}
