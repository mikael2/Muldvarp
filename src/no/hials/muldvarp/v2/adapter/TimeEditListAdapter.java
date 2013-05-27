/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.ScheduleDay;
import no.hials.muldvarp.v2.domain.ScheduleLecture;
import no.hials.muldvarp.v2.domain.TimeEditSchedule;

/**
 * Adapter for displaying schedules derived from TimeEdit.
 * @author johan
 */
public class TimeEditListAdapter extends ArrayAdapter {
    
    private LayoutInflater mInflater;
    private Context context;
    private TimeEditSchedule timeEdit;
    private List<ScheduleDay> items;
    private List<ScheduleLecture> lectures;
    private int resource;
    
    public TimeEditListAdapter(Context context, int resource, int textViewResourceId, List<ScheduleDay> items) {
        super(context, textViewResourceId);
        this.items = items;
        this.context = context;
        this.resource = resource;  
        System.out.println("daba");
        setData(setupScheduleDayItems());
        mInflater = LayoutInflater.from(context);
              
    }
    
    public TimeEditListAdapter(Context context, int resource, int textViewResourceId, TimeEditSchedule timeEdit) {
        super(context, textViewResourceId);
        if(timeEdit.isSimpleFormat()) {
            setData(timeEdit.getDays());
        } else {
            setData(timeEdit.getWeeks());
        }
        mInflater = LayoutInflater.from(context);
        this.timeEdit = timeEdit;
        this.context = context;
        this.resource = resource;
    }
    
    public void setupTimeEditItems(){
        
    }
    
    public List<ScheduleLecture> setupScheduleDayItems(){
        lectures = new ArrayList<ScheduleLecture>();
        if(items != null){
            for(int i = 0; i < items.size(); i ++){
                lectures.addAll(items.get(i).getLectures());
            }
            return lectures;
        }  else {
            System.out.println("fail");
            return null;
        }
        
    }
    
    @TargetApi(11)
    public void setData(Object... items) {
        clear();
        if (items != null) {
            //If the platform supports it, use addAll, otherwise add in loop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                addAll(items);
            }else{
                for(Object obj: items){
                    add(obj);
                }
            }
        }
    }

    @Override
    public int getCount() {
//        int count = 0;
//        if(timeEdit != null){
//            
//        } else {
//            for(int i = 0; i < items.size(); i++){
//                count += items.get(i).getLectures().size();
//            }
//        }
        if(lectures == null || lectures.size() < 0){
            setupScheduleDayItems();
            System.out.println("RABARABA");
        }
        System.out.println("MADAMAD");
        return lectures.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.scheduleClassNames = (TextView) convertView.findViewById(R.id.scheduleClassNames);
            holder.scheduleCourseName = (TextView) convertView.findViewById(R.id.scheduleCourseName);
            holder.scheduleDay = (TextView) convertView.findViewById(R.id.scheduleDay);
            holder.scheduleEndTime = (TextView) convertView.findViewById(R.id.scheduleEndTime);
            holder.scheduleLectureType = (TextView) convertView.findViewById(R.id.scheduleLectureType);
            holder.scheduleRoomName = (TextView) convertView.findViewById(R.id.scheduleRoomName);
            holder.scheduleStartTime = (TextView) convertView.findViewById(R.id.scheduleStartTime);
            holder.scheduleWeek= (TextView) convertView.findViewById(R.id.scheduleWeek);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        if(timeEdit != null && timeEdit.isSimpleFormat()){
            holder.scheduleWeek.setVisibility(View.GONE);
            ScheduleDay day = timeEdit.getDays().get(position);
            holder.scheduleDay.setText(day.getDayName());
            ScheduleLecture lecture;
            if(!day.getLectures().isEmpty()){
                lecture = day.getLectures().get(0);
                holder.scheduleClassNames.setText(lecture.getClassId());
                holder.scheduleLectureType.setText(lecture.getType());
                holder.scheduleRoomName.setTag(lecture.getRoom());
            }
        } else {                       
            ScheduleLecture currentLecture = lectures.get(position);
            holder.scheduleWeek.setVisibility(View.GONE);
            holder.scheduleClassNames.setText(currentLecture.getClassId());
            holder.scheduleCourseName.setText(currentLecture.getName());
            holder.scheduleDay.setText("Man");
            holder.scheduleStartTime.setText(currentLecture.getLectureStart());
            holder.scheduleEndTime.setText(currentLecture.getLectureEnd());
            holder.scheduleLectureType.setText(currentLecture.getType());
            holder.scheduleRoomName.setText(currentLecture.getRoom());            
            holder.scheduleWeek.setText("Uke 34");
//            holder.scheduleClassNames.setText("BIO3");
//            holder.scheduleCourseName.setText("Data, Auto");
//            holder.scheduleDay.setText("Man");
//            holder.scheduleStartTime.setText("10:00");
//            holder.scheduleEndTime.setText("12:00");
//            holder.scheduleLectureType.setText("Foreles");
//            holder.scheduleRoomName.setText("B333");            
//            holder.scheduleWeek.setText("Uke 34");
        }
            
        return convertView;
    }

    static class ViewHolder {
        TextView scheduleWeek;
        TextView scheduleDay;
        TextView scheduleStartTime;
        TextView scheduleEndTime;
        TextView scheduleCourseName;
        TextView scheduleClassNames;
        TextView scheduleRoomName;
        TextView scheduleLectureType;
    }
}
