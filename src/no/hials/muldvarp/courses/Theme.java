/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import java.util.ArrayList;

/**
 *
 * @author kristoffer
 */
public class Theme {
    String name;
    ArrayList<Task> tasks;
    Integer completion;
    
    public Theme() {
        
    }

    public Theme(String name) {
        this.name = name;
    }

    public Integer getCompletion() {
        return completion;
    }

    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
