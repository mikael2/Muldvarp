/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.entities;

import android.graphics.Bitmap;

/**
 * Class defining a general item.
 * 
 * @author johan
 */
public class ListItem {
    
    public String itemName;
    public String smallDetail;
    public String itemDescription;
    public String itemType;
    public Bitmap itemThumbnail;
    
    public ListItem(String itemName, String smallDetail, String itemDescription, String itemType, Bitmap itemThumbnail) {
        
        this.itemName = itemName;
        this.smallDetail = smallDetail;
        this.itemDescription = itemDescription;
        this.itemType = itemType;
        this.itemThumbnail = itemThumbnail;
        
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Bitmap getItemThumbnail() {
        return itemThumbnail;
    }

    public void setItemThumbnail(Bitmap itemThumbnail) {
        this.itemThumbnail = itemThumbnail;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
}
