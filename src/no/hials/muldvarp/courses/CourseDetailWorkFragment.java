/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import java.util.ArrayList;
import no.hials.muldvarp.R;

/**
 *
 * @author kristoffer
 */
public class CourseDetailWorkFragment extends Fragment {
    //MyExpandableListAdapter adapter = null;
    
    ExpandableListView listview;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.course_work, container, false);
        
        listview = (ExpandableListView)fragmentView.findViewById(R.id.listview);
        
        //testdata
        ArrayList<String> groupNames = new ArrayList<String>();
        groupNames.add( "Tema 1" );
	    groupNames.add( "Tema 2" );
	    groupNames.add( "Tema 3" );
	    groupNames.add( "Tema 4" );
        ArrayList<ArrayList<Task>> tasks = new ArrayList<ArrayList<Task>>();
        
        ArrayList<Task> task = new ArrayList<Task>();
                task.add( new Task( "eBook", true) ); 
		task.add( new Task( "Video") ); 
		task.add( new Task( "Tutorial") );
        tasks.add( task );
        
        task = new ArrayList<Task>();
                task.add( new Task( "eBook", true) ); 
		task.add( new Task( "Video") ); 
		task.add( new Task( "Tutorial") );
        tasks.add( task );
        
        task = new ArrayList<Task>();
                task.add( new Task( "eBook", true) ); 
		task.add( new Task( "Video") ); 
		task.add( new Task( "Tutorial") );
        tasks.add( task );
        
        task = new ArrayList<Task>();
                task.add( new Task( "eBook", true) ); 
		task.add( new Task( "Video") ); 
		task.add( new Task( "Tutorial") );
        tasks.add( task );
        
        
        TaskAdapter adapter = new TaskAdapter(this.getActivity().getApplicationContext(), groupNames, tasks);
        listview.setAdapter(adapter);
        
//        registerForContextMenu(listview);
        
        return fragmentView;
    }
    
//    
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//        menu.setHeaderTitle("Sample menu");
//        menu.add(0, 0, 0, "Test");
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
//
//        String title = ((TextView) info.targetView).getText().toString();
//
//        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
//        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
//            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
//            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
//            Toast.makeText(this.getActivity().getApplicationContext(), title + ": Child " + childPos + " clicked in group " + groupPos,
//                    Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
//            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
//            Toast.makeText(this.getActivity().getApplicationContext(), title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return false;
//    }
    
    public class TaskAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> themes;
    private ArrayList<ArrayList<Task>> tasks;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, 
                        ArrayList<String> themes,
			ArrayList<ArrayList<Task>> tasks ) { 
        this.context = context;
		this.themes = themes;
        this.tasks = tasks;
        inflater = LayoutInflater.from( context );
    }

    public Object getChild(int groupPosition, int childPosition) {
        return tasks.get( groupPosition ).get( childPosition );
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long)( groupPosition*1024+childPosition );  // Max 1024 children per group
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = inflater.inflate(R.layout.course_work_child, parent, false); 
        final Task t = (Task)getChild( groupPosition, childPosition );
		TextView name = (TextView)v.findViewById( R.id.childname );
		if( name != null ) {
                    name.setText(t.getName());
                }		
		CheckBox cb = (CheckBox)v.findViewById( R.id.checkbox );
                cb.setChecked(t.getDone());
                
                v.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    Intent myIntent = new Intent(v.getContext(), CoursePublicDetailActivity.class);
                    startActivityForResult(myIntent, 0);
                }
            });
                
            cb.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // Perform action on clicks, depending on whether it's now checked
                    if (((CheckBox) v).isChecked()) {
                        t.setDone(true);
                    } else {
                        t.setDone(false);
                    }
                }
            });
        
        return v;
    }

    public int getChildrenCount(int groupPosition) {
        return tasks.get( groupPosition ).size();
    }

    public Object getGroup(int groupPosition) {
        return themes.get( groupPosition );        
    }

    public int getGroupCount() {
        return themes.size();
    }

    public long getGroupId(int groupPosition) {
        return (long)( groupPosition*1024 );  // To be consistent with getChildId
    } 

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = inflater.inflate(R.layout.course_work_group, parent, false); 
        String gt = (String)getGroup( groupPosition );
		TextView colorGroup = (TextView)v.findViewById( R.id.childname );
		if( gt != null )
			colorGroup.setText( gt );
        return v;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    } 

    public void onGroupCollapsed (int groupPosition) {} 
    public void onGroupExpanded(int groupPosition) {}


    }
}
