/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import no.hials.muldvarp.domain.Course;
import no.hials.muldvarp.domain.Task;
import no.hials.muldvarp.domain.Theme;
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
    ExpandableListView listview;
    View fragmentView;
    Course c;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.course_work, container, false);
            listview = (ExpandableListView)fragmentView.findViewById(R.id.listview);
        }
        return fragmentView;
    }
    
    public void ready() {
        if(c == null) {
            CourseDetailActivity activity = (CourseDetailActivity)CourseDetailWorkFragment.this.getActivity();
            c = activity.getActiveCourse();
        }
        
        ArrayList<Theme> themes = c.getThemes();
        ArrayList<ArrayList<Task>> allTasks = new ArrayList<ArrayList<Task>>();
        for(Theme theme : themes){
            ArrayList<Task> tasks = new ArrayList<Task>();
            for(Task task : theme.getTasks()) {
                tasks.add(task);
            }
            allTasks.add(tasks);
        }

        TaskAdapter adapter = new TaskAdapter(fragmentView.getContext(), themes, allTasks);
        listview.setAdapter(adapter);
        
//        registerForContextMenu(listview);
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
    private ArrayList<Theme> themes;
    private ArrayList<ArrayList<Task>> tasks;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, 
                        ArrayList<Theme> themes,
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
                    if(t.getDone() == true) {
                        name.setAlpha((float)0.5);
                    } else {
                        name.setAlpha(1);
                    }
                }		
		CheckBox cb = (CheckBox)v.findViewById( R.id.checkbox );
                cb.setChecked(t.getDone());
                
                v.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    Intent myIntent = new Intent(v.getContext(), CoursePublicDetailActivity.class);
                    myIntent.putExtra("id", t.getName());
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
                    notifyDataSetChanged();
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
        Theme gt = (Theme)getGroup( groupPosition );
		TextView themeGroup = (TextView)v.findViewById( R.id.childname );
		if( gt != null ) {
                    themeGroup.setText( gt.getName() + " " + gt.getCompletion() + "%" );
                        if( gt.getCompletion() == 100) {
                            themeGroup.setAlpha((float)0.5);
                        } else {
                            themeGroup.setAlpha(1);
                        }
                }           
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
