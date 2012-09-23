/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;

public class Domain implements Serializable {
    Integer id;
    String name;
    String detail;
    String description;
    int icon;
    Class activity;

    public Domain(String name) {
        this.name = name;
    }
    
    public Domain(String name, String detail, String description, int icon) {
        this.name = name;
        this.detail = detail;
        this.description = description;
        this.icon = icon;
    }
    
    public Domain(String name, String detail, String description, int icon, Class activity) {
        this.name = name;
        this.detail = detail;
        this.description = description;
        this.icon = icon;
        this.activity = activity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
    
    
}
