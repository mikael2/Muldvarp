
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author johan
 */
public class ScheduleDay extends Domain {
        String dayName;
        String date;
        List<ScheduleLecture> lectures;

        public ScheduleDay(JSONObject json) throws JSONException{
            this.dayName = json.getString("day");
            this.date = json.getString("date");
            this.lectures = JSONArrayToLectures(json.getJSONArray("lectures"));            
        }
        
        public static List<ScheduleLecture> JSONArrayToLectures(JSONArray jsonArray) throws JSONException {
            List<ScheduleLecture> retVal = new ArrayList<ScheduleLecture>();        
            for (int i = 0; i < jsonArray.length(); i++) {
                retVal.add(new ScheduleLecture(jsonArray.getJSONObject(i)));
            }        
            return retVal;
        }

        public String getDayName() {
            return dayName;
        }

        public void setDayName(String day) {
            this.dayName = day;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<ScheduleLecture> getLectures() {
            return lectures;
        }

        public void setLectures(List<ScheduleLecture> lectures) {
            this.lectures = lectures;
        }
    }
