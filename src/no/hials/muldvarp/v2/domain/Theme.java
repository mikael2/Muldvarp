/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.util.ArrayList;

/**
 *
 * @author kristoffer
 */
public class Theme {
    Integer id;
    String name;
    ArrayList<Topic> tasks;
    
    public Theme() {
        
    }

    public Theme(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCompletion() {
        Integer numberOfTasks = 0;
        Integer completedTasks = 0;
        Integer completion = 100;
        for(Topic task : tasks) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Topic> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Topic> tasks) {
        this.tasks = tasks;
    }
}
