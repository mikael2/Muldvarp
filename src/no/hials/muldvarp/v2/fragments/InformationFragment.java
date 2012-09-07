/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class InformationFragment extends Fragment {
    InformationFragment.ImageAdapter adapter;
    Activity activity;
    public enum Type {MAIN, PROGRAMME, COURSE, TASK}
    Type type;
    int stringlist;

    public InformationFragment(Type type) {
        this.type = type;
        switch(type) {
            case MAIN:
                stringlist = R.array.main_list;
                break;
            case PROGRAMME:
                stringlist = R.array.programme_list;
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = InformationFragment.this.getActivity();
        // Inflate the layout for this fragment
        View retVal = inflater.inflate(R.layout.desktop_fragment, container, false);

        GridView gridview = (GridView) retVal.findViewById(R.id.gridview);
        TextView textView = (TextView) retVal.findViewById(R.id.textview);
        
        textView.setText(activity.getTitle());
    
        adapter = new InformationFragment.ImageAdapter(getActivity());
        gridview.setAdapter(adapter);
        
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.getActionBar().setSelectedNavigationItem(position);
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
            return icons.length;
        }

        public Object getItem(int position) {
            return icons[position];
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

                imageView.setImageResource(icons[position]);
                textView.setText(getResources().getStringArray(stringlist)[position]);
            } else {
                retVal = convertView;
            }

            return retVal;
        }
        
        private int[] icons = {
            R.drawable.stolen_contacts,
            R.drawable.stolen_tikl,
            R.drawable.stolen_smsalt,
            R.drawable.stolen_youtube,
            R.drawable.stolen_calculator,
            R.drawable.stolen_dictonary,
            R.drawable.stolen_notes,
            R.drawable.stolen_calender
        };
    }
}
