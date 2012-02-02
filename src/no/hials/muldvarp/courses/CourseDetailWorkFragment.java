/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
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
        
        
        //adapter = new MyExpandableListAdapter();
        TaskAdapter adapter = new TaskAdapter(this.getActivity().getApplicationContext(), groupNames, tasks);
        listview.setAdapter(adapter);
        
        registerForContextMenu(listview);
        
        return fragmentView;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
        menu.add(0, 0, 0, "Test");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
//            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
//                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
//            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
    
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups = { "Tema 1", "Tema 2", "Tema 3", "Tema 4" };
        private String[][] children = {
                { "Les eBook blabla", "Se video blabla", "Gjør tutorial blabla" },
                { "Les eBook blabla", "Se video blabla", "Gjør tutorial blabla" },
                { "Les eBook blabla", "Se video blabla", "Gjør tutorial blabla" },
                { "Les eBook blabla", "Se video blabla", "Gjør tutorial blabla" }
        };

        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            TextView textView = new TextView(getActivity().getApplicationContext());
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(66, 0, 0, 0);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
    
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
        Task t = (Task)getChild( groupPosition, childPosition );
		TextView name = (TextView)v.findViewById( R.id.childname );
		if( name != null ) {
                    name.setText(t.getName());
                }		
		CheckBox cb = (CheckBox)v.findViewById( R.id.checkbox );
        cb.setChecked( t.getDone() );
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
