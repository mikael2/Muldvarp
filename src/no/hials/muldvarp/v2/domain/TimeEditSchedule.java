/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Domain class representing a TimeEdit Schedule
 * @author johan
 */
public class TimeEditSchedule extends Domain implements Serializable {
    
    boolean simpleFormat;
    String scheduleYear;
    String weekStart;
    String weekEnd;
    int numberOfWeeks;
    List<ScheduleWeek> weeks;
    List<ScheduleDay> days;
    
    public TimeEditSchedule(JSONObject json) throws JSONException {
        if(json.optString("scheduleYear").isEmpty()){
           simpleFormat = true;
           this.days = ScheduleWeek.JSONArrayToDays(json.getJSONArray("days"));
        } else {
            simpleFormat = false;
            this.scheduleYear = json.getString("scheduleYear");
            this.weekStart = json.getString("weekStart");
            this.weekEnd = json.getString("weekEnd");
            this.weeks= JSONArrayToWeeks(json.getJSONArray("weeks"));
        }      
    }
    
    public static List<ScheduleWeek> JSONArrayToWeeks(JSONArray jsonArray) throws JSONException {
        List<ScheduleWeek> retVal = new ArrayList<ScheduleWeek>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new ScheduleWeek(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }

    public boolean isSimpleFormat() {
        return simpleFormat;
    }

    public String getScheduleYear() {
        return scheduleYear;
    }

    public String getWeekStart() {
        return weekStart;
    }

    public String getWeekEnd() {
        return weekEnd;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public List<ScheduleWeek> getWeeks() {
        return weeks;
    }
    
    public List<ScheduleDay> getDays() {
        return days;
    }

}
