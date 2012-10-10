/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class Topic extends Domain {
    ArrayList<Task> tasks;

    public Topic() {

    }

    public Topic(JSONObject json) throws JSONException {
        super(json);
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
