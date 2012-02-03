/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.graphics.Bitmap;

/**
 *
 * @author Nospherus
 */
public class LIBDetails {

    String name;
    String info;
    Bitmap icon;
    
    public LIBDetails(String name, String info){
        this.name = name;
        this.info = info;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
