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
 *
 * @author johan
 */
public class ScheduleWeek extends Domain implements Serializable{
        String weekString;
        String weekNo;
        List<ScheduleDay> days;
        
        private ScheduleWeek(String weekString){
            this.days = new ArrayList<ScheduleDay>();
            this.weekString = weekString;
        }
        
        public ScheduleWeek(JSONObject json) throws JSONException {
            this.weekString = json.getString("weekString");
            this.weekNo = json.getString("weekString");
            this.days =JSONArrayToDays(json.getJSONArray("days"));
        }
        
        public static List<ScheduleDay> JSONArrayToDays(JSONArray jsonArray) throws JSONException {
            List<ScheduleDay> retVal = new ArrayList<ScheduleDay>();        
            for (int i = 0; i < jsonArray.length(); i++) {
                retVal.add(new ScheduleDay(jsonArray.getJSONObject(i)));
            }        
            return retVal;
        }

        public String getWeekString() {
            return weekString;
        }

        public void setWeekString(String weekString) {
            this.weekString = weekString;
        }

        public String getWeekNo() {
            if((weekNo == null || weekNo.isEmpty()) && !weekString.isEmpty()){
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(weekString);
                if(matcher.find()){
                    weekNo = matcher.group();
                    return weekNo;
                }
                return "";
            }
            return weekNo;
        }

        public void setWeekNo(String weekNo) {
            this.weekNo = weekNo;
        }

        public List<ScheduleDay> getDays() {
            return days;
        }

        public void setDays(List<ScheduleDay> days) {
            this.days = days;
        }
    }
