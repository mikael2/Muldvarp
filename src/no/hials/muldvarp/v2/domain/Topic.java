/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class Topic extends Domain {
    ArrayList<Task> tasks;

    public Topic(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.tasks = parseTasks(json.getJSONArray("tasks"));
    }
    
    public final ArrayList<Task> parseTasks(JSONArray jArray) throws JSONException {
        ArrayList<Task> retVal = new ArrayList<Task>();
        for(int i = 0; i < jArray.length(); i++) {
            Task t = new Task(jArray.getJSONObject(i));
            retVal.add(t);
        }
        return retVal;
    }

    public Integer getCompletion() {
        Integer numberOfTasks = 0;
        Integer completedTasks = 0;
        Integer completion = 100;
        for(Task task : tasks) {
            numberOfTasks++;
            if(task.getDone() == true) {
                completedTasks++;
            }
        }
        if(numberOfTasks != 0) {
            completion = (completedTasks*100)/numberOfTasks;
        }
        return completion;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
